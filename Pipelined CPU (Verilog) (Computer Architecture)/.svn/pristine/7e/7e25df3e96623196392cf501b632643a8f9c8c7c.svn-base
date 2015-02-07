module EX_tb();    
	reg rst_n, clk, src1sel;
	reg [2:0] ctrl;
	reg [3:0] shamt;
	reg signed [15:0] signed_result, instr_addr_incre, p0_data, p1_data;
	wire [15:0] PCSrc_addr, ALU_result;
	wire neg_flag, zr_flag, ov_flag;
	EX_MEM exec(neg_flag, zr_flag, ov_flag, ALU_result, PCSrc_addr, p0_data, p1_data, src1sel, instr_addr_incre, signed_result, ctrl, shamt, clk, rst_n);
	localparam add = 3'b000;      // add src0 and src1
	localparam lhb = 3'b001;      // subtract src1 from src0
	localparam sub = 3'b010;
	localparam ando = 3'b011;     // bitwise and src0 and src1
	localparam noro = 3'b100;     // bitwise nor src0 and src1
	localparam sll = 3'b101;      // logical shift left src0
	localparam srl = 3'b110;      // logical shift right src0
	localparam sra = 3'b111;      // arithmetic shift right src0
	initial begin
		rst_n = 0;
		src1sel = 1; //p1_data
	  ctrl = add;
	  shamt = 4'b0000;
	  signed_result = 16'h7777;
	  instr_addr_incre = 16'h0005;
	  p0_data = 16'h1111;
	  p1_data = 16'h3333;
	  #20;
	  rst_n = 1;
	  #20;
	  ctrl = sub;
	  #20;
	  p0_data = 16'hffee;
	  ctrl = sra;
		shamt = 4'b0100;
		#20;
		ctrl = lhb;
		src1sel = 0;
		#20;
		src1sel = 1;
		#20;
		$stop();		
	end
	initial begin
	  clk = 0;  
	end	
	always clk = #10 ~clk;
endmodule
