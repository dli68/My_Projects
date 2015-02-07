module ID_tb();  
	wire[15:0] mem_wrt_data, Jump_addr, signed_result, p0_data, p1_data;
	wire mem_re, src1sel, jump, mem_we, PCSrc, imm_4_sel, jal_sel, memToReg;
	wire[3:0] shamt;
	wire[2:0] func;
	reg [15:0] instr, dst_data;
	reg  zr_flag, ov_flag, neg_flag, clk;
	ID_EX tb(mem_wrt_data, mem_re, Jump_addr, signed_result, src1sel, shamt, func, 
	jump, mem_we, PCSrc, imm_4_sel, jal_sel, memToReg, p0_data, p1_data, instr, dst_data, zr_flag, ov_flag, neg_flag, clk);
	
	initial begin
		#20;
		//test 3 registers
		instr = 16'h0571;
		dst_data = 16'h4444;
		zr_flag = 0;
		ov_flag = 0;
		neg_flag = 0;
		#20;
		instr = 16'h2145;
		#20;
		$stop();		
	end
	initial begin
	  clk = 0;  
	end	
	always clk = #10 ~clk;
endmodule