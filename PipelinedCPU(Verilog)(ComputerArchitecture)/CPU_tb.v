///svn change test
module cpu_tb();
	reg clk, rst_n;
	wire hlt;
	wire [15:0] pc, RealPC;

	cpu cpuModel(.clk(clk), .rst_n(rst_n), .hlt(hlt), .pc(pc));
	assign RealPC = pc - 1;

	// generate clock
	// set reset and let cpu run
	initial begin
		rst_n = 0;
		@(posedge clk); begin
			rst_n = 1;
		end
		//#400;
		//$stop();

		@(posedge hlt) begin
	       #1;
	       $stop();
	    end

	end
	initial begin
	  clk = 1;
	end
	always clk = #10 ~clk;
endmodule