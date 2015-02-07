/*EX stage
  Output 

  Input 
  memHazardIn -passed from ID, used to indicate MEM_MEM bypass

  Modules
  MEM_MEM_MUX -used to MEM-MEM bypass, depending on signal memHazardIn 
*/



module EX(newAddr, we, zr_en, ov_neg_en, zr_flag, ov_flag, neg_flag,
		  aluResult, mem_data,
		  /*input*/
		  memHazardIn, src1In, src0In, imAddrIncreIn, zr_enIn, ov_neg_enIn, weIn,
		  shamtIn, funcIn, zrNext, memBypassDataIn, memDataIn, LabelSel_MEM);
	output [15:0] newAddr, aluResult, mem_data;
	output we, zr_en, ov_neg_en, zr_flag, ov_flag, neg_flag;

	input [15:0] src1In, src0In, imAddrIncreIn, memBypassDataIn, memDataIn;
	input [3:0] shamtIn;
	input [2:0] funcIn;
	input memHazardIn, zr_enIn, ov_neg_enIn, weIn, zrNext, LabelSel_MEM;

	wire [15:0] dst;

	ADDR_ALU addrAlu         (newAddr, imAddrIncreIn, src1In);
	ADDZ_Detection addzDet   (zr_en, ov_neg_en, we, zr_enIn, ov_neg_enIn, zrNext, funcIn, weIn, LabelSel_MEM);
	MEM_MEM_MUX memMux       (mem_data, memBypassDataIn, memDataIn, memHazardIn);
	ALU alunit               (neg_flag, zr_flag, ov_flag, dst, shamtIn, funcIn, src0In, src1In);

	assign aluResult = dst;

endmodule

module ADDR_ALU(newpc , pcIncre, signed_result);
    input [15:0] pcIncre, signed_result;
    output [15:0] newpc;

    assign newpc = pcIncre + signed_result;
endmodule

module ADDZ_Detection(zr_en, ov_neg_en, we, zr_enIn, ov_neg_enIn, zr_next, func, weIn, LabelSel_MEM);
	output zr_en, ov_neg_en, we;
	input weIn, zr_enIn, ov_neg_enIn, zr_next, LabelSel_MEM;
	input [2:0] func;

	//test
	/*
	assign zr_en = zr_enIn;
	assign ov_neg_en = ov_neg_enIn;
	assign we = weIn;
	*/
	
	wire zr_en_w = (func == 0 && ov_neg_enIn == 1 && zr_enIn == 0) ? ((zr_next == 1) ? 1 : 0) : zr_enIn;
	wire ov_neg_en_w = (func == 0 && ov_neg_enIn == 1 && zr_enIn == 0) ? ((zr_next == 1) ? 1 : 0) : ov_neg_enIn;
	assign we = (func == 0 && ov_neg_enIn == 1 && zr_enIn == 0) ? ((zr_next == 1) ? 1 : 0) : weIn;

	assign zr_en = zr_en_w & ~LabelSel_MEM;
	assign ov_neg_en = ov_neg_en_w & ~LabelSel_MEM;
	
endmodule


module MEM_MEM_MUX(memMemMuxOut, memIn, mem_wrt_data, memHazard);
	output [15:0] memMemMuxOut;
	input [15:0] memIn, mem_wrt_data;
	input memHazard;
	
	assign memMemMuxOut = memHazard ? memIn : mem_wrt_data;
	
	//test
	/*
	assign memMemMuxOut = aluResult;
	*/
endmodule

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
	
	/* implement arithmetic shifter */
	wire [15:0] firstLevel = (shamt[0]) ? {src1[15], src1[15:1]} : src1;
	wire [15:0] secondLevel = (shamt[1]) ? {{2{src1[15]}}, firstLevel[15:2]} : firstLevel;
	wire [15:0] thirdLevel = (shamt[2]) ? {{4{src1[15]}}, secondLevel[15:4]} : secondLevel;
	wire [15:0] fourthLevel = (shamt[3]) ? {{8{src1[15]}}, thirdLevel[15:8]} : thirdLevel;
	
	/* design alu operations */
	wire [15:0]tmpDst = (ctrl == add) ? src0+src1 :
	(ctrl == sub) ? src1+~src0+1'b1 :
	(ctrl == ando) ? src0&src1 :
	(ctrl == noro) ? ~(src0|src1) :
	(ctrl == sll) ? src1 << shamt :
	(ctrl == srl) ? src1 >> shamt :
	(ctrl == sra) ? fourthLevel :
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