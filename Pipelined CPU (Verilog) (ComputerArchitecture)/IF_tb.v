module IF_tb();  
  
	wire [15:0] pc, instr, instr_addr_incre;
	reg PCSrc, jump, clk, rst_n, hlt, rd_en;
	reg [15:0] Jump_addr, PCSrc_Addr;
	IF_ID ifid(pc, instr, instr_addr_incre, PCSrc, jump, Jump_addr, PCSrc_Addr, clk, rst_n, hlt, rd_en);
	// generate clock
	// set reset and let cpu run
	initial begin
		rst_n = 0;
		rd_en = 1;
		hlt = 0;
	  PCSrc = 0;
	  jump = 0;
	  Jump_addr = 0'h0003;
	  PCSrc_Addr = 0'h0004;
		#40;
		rst_n = 1;
		#80;
		hlt = 1;
		#80;
		hlt = 0;
		jump = 1;
		#20;
		jump = 0;
		#80;
		PCSrc = 1;
		#20;
		PCSrc = 0;
		#80;
		$stop();		
	end
	initial begin
	  clk = 0;  
	end	
	always clk = #10 ~clk;
endmodule
