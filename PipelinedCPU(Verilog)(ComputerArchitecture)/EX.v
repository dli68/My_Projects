/*EX stage
  Output
  AddrOut -the jump target address produced by the branch module
  we -the real we signal after addz detection
  zr_en, ov_neg_en -enable signals for three flags
  zr_flag, ov_flag, neg_flag -flag values produced by alu
  aluResult -final merged arithmetic result produced by the ex stage
  mem_data -memory write data
  LabelSelOut -the (correction) branch signal produced by the branch module

  Input
  memHazardIn -passed from ID, used to indicate MEM_MEM bypass
  src1In, src0In -two operands for alu
  imAddrIncreIn -the incremented address
  zr_enIn -zero-flag write-enable
  ov_neg_enIn -overflow/negative flag write-enable
  weIn -register write enable
  shamtIn -shift immediate value
  funcIn -Alu opcode
  zrNext -the zr-flag status from EX-MEM ff
  memBypassDataIn -the bypassed data from memory stage
  memDataIn -the memory data that will be written to the mem
  zr_flagIn -the zr-flag value from EX-MEM ff
  ov_flagIn -the ov-flag value from EX-MEM ff
  neg_flagIn -the neg-flag value from EX-MEM ff
  labelSelIn -the jump enabler signal
  brTypeIn -the branch type 3-bit code
  prediction -the decision made by the branch predictor
  predictedAddr -the jump address calculated by the previous stage

  Modules
  MEM_MEM_MUX -used to MEM-MEM bypass, depending on signal memHazardIn
*/

module EX(AddrOut, we, zr_en, ov_neg_en, zr_flag, ov_flag, neg_flag,
		  aluResult, mem_data, LabelSelOut,
		  /*input*/
		  memHazardIn, src1In, src0In, imAddrIncreIn, zr_enIn, ov_neg_enIn, weIn,
		  shamtIn, funcIn, zrNext, memBypassDataIn, memDataIn,
		  zr_flagIn, ov_flagIn, neg_flagIn, labelSelIn, brTypeIn, prediction,
		  predictedAddr);
	output [15:0] aluResult, mem_data, AddrOut;
	output we, zr_en, ov_neg_en, zr_flag, ov_flag, neg_flag, LabelSelOut;

	input [15:0] src1In, src0In, imAddrIncreIn, memBypassDataIn, memDataIn, predictedAddr;
	input [3:0] shamtIn;
	input [2:0] funcIn, brTypeIn;
	input memHazardIn, zr_enIn, ov_neg_enIn, weIn, zrNext, zr_flagIn, ov_flagIn, neg_flagIn, labelSelIn
	, prediction;

	wire [15:0] dst;

	ADDZ_Detection addzDet   (zr_en, ov_neg_en, we, zr_enIn, ov_neg_enIn, zrNext, funcIn, weIn);
	MEM_MEM_MUX memMux       (mem_data, memBypassDataIn, memDataIn, memHazardIn);
	ALU alunit               (neg_flag, zr_flag, ov_flag, dst, shamtIn, funcIn, src0In, src1In);

	LabelJumpLogic jumpLogic (AddrOut, LabelSelOut, weIn, labelSelIn, brTypeIn, zr_flagIn, neg_flagIn,
							  ov_flagIn, prediction,
							  imAddrIncreIn, predictedAddr);
	JAL_MUX	jalmux           (aluResult, weIn, labelSelIn, dst, imAddrIncreIn);
endmodule

/* addz detection module detects the existence of addz instruction and determine the
 * we signal according to zero flag
 */
module ADDZ_Detection(zr_en, ov_neg_en, we, zr_enIn, ov_neg_enIn, zr_next, func, weIn);
	output zr_en, ov_neg_en, we;
	input weIn, zr_enIn, ov_neg_enIn, zr_next;
	input [2:0] func;

	assign zr_en = (func == 0 && ov_neg_enIn == 1 && zr_enIn == 0) ? 1 : zr_enIn;
	assign ov_neg_en = ov_neg_enIn;
	assign we = (func == 0 && ov_neg_enIn == 1 && zr_enIn == 0) ? ((zr_next == 1) ? 1 : 0) : weIn;
endmodule

/* At the end of the ex stage, the addr+1 signal is merged with the alu result */
module JAL_MUX(aluResult, we, labelSel, dst, pcIncre);
	output [15:0] aluResult;
	input we, labelSel;
	input [15:0] dst, pcIncre;
	assign aluResult = (labelSel == 1 && we == 1) ? pcIncre : dst;
endmodule

/* jump logic determines the real decision the processor ought to make, if there is prediction error,
 * it will correct the previous decision
 */
module LabelJumpLogic(AddrOut, LabelSelOut, we, labelSel, brType, zr_flag, neg_flag, ov_flag,
	prediction, imAddrIncre, predictedAddr);
	output LabelSelOut;
	input [2:0] brType;
	input labelSel, we, zr_flag, neg_flag, ov_flag, prediction;

	output[15:0] AddrOut;
	input[15:0] imAddrIncre, predictedAddr;

	localparam neq = 3'b000,
			   eq = 3'b001,
			   gt = 3'b010,
			   lt = 3'b011,
			   gte = 3'b100,
			   lte = 3'b101,
			   ovfl = 3'b110,
			   uncond = 3'b111;

	wire brSel = (brType == neq) ? ~zr_flag :
                 (brType == eq)  ? zr_flag  :
                 (brType == gt)  ? ~zr_flag & ~neg_flag :
                 (brType == lt)  ? neg_flag :
                 (brType == gte) ? ~neg_flag :
                 (brType == lte) ? neg_flag | zr_flag :
                 (brType == ovfl)? ov_flag :
                 1'b1;

    wire LabelSelOutPrev = (labelSel == 1 && we == 1) ? 1'b1 : (labelSel ? brSel : 1'b0);
    assign LabelSelOut = (prediction == LabelSelOutPrev) ? 0 : 1;
    assign AddrOut = (prediction == 1 && LabelSelOutPrev == 0) ? imAddrIncre : predictedAddr;
endmodule

/* mem-to-mem bypass mux */
module MEM_MEM_MUX(memMemMuxOut, memIn, mem_wrt_data, memHazard);
	output [15:0] memMemMuxOut;
	input [15:0] memIn, mem_wrt_data;
	input memHazard;

	assign memMemMuxOut = memHazard ? memIn : mem_wrt_data;
endmodule

/* arithmetic unit module */
module ALU (neg, zr, ov, dst, shamt, ctrl, src0, src1);
	input [15:0] src0, src1;
	input[3:0] shamt;
	input[2:0] ctrl;
	output [15:0] dst;
	output ov, zr, neg;
	// opcode from WISCSP14 ISA
	localparam add = 3'b000;      // add src0 and src1
	localparam lhb = 3'b001;      // subtract src1 from src0
	localparam sub = 3'b010;
	localparam ando = 3'b011;     // bitwise and src0 and src1
	localparam noro = 3'b100;     // bitwise nor src0 and src1
	localparam sll = 3'b101;      // logical shift left src0
	localparam srl = 3'b110;      // logical shift right src0
	localparam sra = 3'b111;      // arithmetic shift right src0

	/* design alu operations */
	wire [15:0] tmpDst = (ctrl == add) ? src0+src1 :
	(ctrl == sub) ? src1+~src0+1'b1 :
	(ctrl == ando) ? src0&src1 :
	(ctrl == noro) ? ~(src0|src1) :
	(ctrl == sll) ? src1 << shamt :
	(ctrl == srl) ? src1 >> shamt :
	(ctrl == sra) ? {$signed(src1)>>>shamt} :
	(ctrl == lhb) ? {src1[7:0],src0[7:0]}:
				  16'h0000; // if ctrl signal is undefined, output 0

	/* design overflow logic */
	wire ov = ((ctrl == add) & ((src0[15]&src1[15]&~tmpDst[15]) | (~src0[15]&~src1[15]&tmpDst[15]))) |
	((ctrl == sub) & ((src0[15]&~src1[15]&tmpDst[15]) | (~src0[15]&src1[15]&~tmpDst[15])));

	/* design zero flag logic */
	assign zr = ~(|dst);

	assign neg = dst[15];
	/* design saturation adder */
	assign dst = (((ctrl == add) & (ov&src1[15])) || ((ctrl == sub) & (ov&~src0[15]))) ?  16'h8000 :
	(((ctrl == add) & (ov&~src1[15])) || ((ctrl == sub) & (ov&src0[15]))) ? 16'h7fff :
	tmpDst;
endmodule