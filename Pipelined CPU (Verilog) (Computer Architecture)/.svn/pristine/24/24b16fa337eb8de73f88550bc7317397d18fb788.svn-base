module MEM_tb();    
	wire [15:0] mem_data;
	reg [15:0] mem_addr, mem_wrt_data;
	reg clk, mem_we, mem_re;
	MEM_WB memtb(mem_data, mem_addr, mem_we, mem_re, mem_wrt_data, clk);
	
	initial begin
		mem_we = 1;
		mem_re = 0;
		mem_wrt_data = 16'h1234;
		mem_addr = 16'h1111;
		#40;
		mem_we = 0;
		mem_re = 1;
		#40;
		mem_wrt_data = 16'h1233;
		#40;
		mem_we = 1;
		mem_re = 0;
		#40;
		mem_we = 0;
		$stop();		
	end
	initial begin
	  clk = 0;  
	end	
	always clk = #10 ~clk;
endmodule


