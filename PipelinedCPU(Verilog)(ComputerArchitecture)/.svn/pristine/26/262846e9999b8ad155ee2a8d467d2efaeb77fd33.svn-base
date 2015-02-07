module MEM_WB(hltOut, weOut, dst_dataOut, dst_addrOut,
			  /*input*/
			  hltIn, weIn, dst_dataIn, dst_addrIn, stallMEM, flushMEM, clk, rst_n);
	output reg weOut, hltOut;
	output reg [3:0] dst_addrOut;
	output reg [15:0] dst_dataOut;

	input weIn, stallMEM, flushMEM, clk, rst_n, hltIn;
	input [3:0] dst_addrIn;
	input [15:0] dst_dataIn;

	always @(posedge clk, negedge rst_n)
		if(!rst_n)
		begin
			weOut<=0;
			dst_dataOut<=0;
			dst_addrOut<=0;
			hltOut <=0;
		end
		else if(stallMEM)
		begin
			weOut<=weOut;
			dst_dataOut<=dst_dataOut;
			dst_addrOut<=dst_addrOut;
			hltOut <=hltOut;
		end
		else if(flushMEM)
		begin
			weOut<=0;
			dst_dataOut<=0;
			dst_addrOut<=0;
			hltOut <=0;
		end
		else begin
			weOut<=weIn;
			dst_dataOut<=dst_dataIn;
			dst_addrOut<=dst_addrIn;
			hltOut <=hltIn;
		end
endmodule