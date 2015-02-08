module IF_ID(instrOut, imAddrIncreOut, instrIn, imAddrIncreIn, flushIF, stallIF, clk, rst_n);
	output reg [15:0] instrOut, imAddrIncreOut;
	input [15:0] instrIn, imAddrIncreIn;
	input flushIF, clk, rst_n, stallIF;
	always @(posedge clk, negedge rst_n)
		if(!rst_n)
		begin
			instrOut <= 16'hA000;
			imAddrIncreOut <= 16'h0000;
		end
		else if(flushIF)
		begin
			instrOut <= 16'hA000;
		end
		else if(stallIF)
		begin
			instrOut <= instrOut;
			imAddrIncreOut <= imAddrIncreOut;
		end
		else begin
			instrOut <= instrIn;
			imAddrIncreOut <= imAddrIncreIn;
		end
endmodule