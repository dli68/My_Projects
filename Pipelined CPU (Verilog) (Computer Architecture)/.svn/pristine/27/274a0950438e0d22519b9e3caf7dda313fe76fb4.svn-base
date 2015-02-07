module DM(clk,addr,re,we,wrt_data,rd_data/*,hlt*/);

	/////////////////////////////////////////////////////////
	// Data memory.  Single ported, can read or write but //
	// not both in a single cycle.  Precharge on clock   //
	// high, read/write on clock low.                   //
	/////////////////////////////////////////////////////
	input clk/*, hlt*/;
	input [15:0] addr;
	input re;				// asserted when instruction read desired
	input we;				// asserted when write desired
	input [15:0] wrt_data;	// data to be written

	output reg [15:0] rd_data;	//output of data memory

	reg [15:0]data_mem[0:65535];

	///////////////////////////////////////////////
	// Model read, data is latched on clock low //
	/////////////////////////////////////////////
	always @(addr,re,clk)
	  if (~clk && re && ~we)
	    rd_data <= data_mem[addr];
		
	////////////////////////////////////////////////
	// Model write, data is written on clock low //
	//////////////////////////////////////////////
	always @(posedge clk)
	  if (we && ~re)
	    data_mem[addr] <= wrt_data;
/*
integer indxm;
	always @(posedge hlt)
	  for(indxm=1; indxm<256; indxm = indxm+1)
		$display("MEM%1h = %h",indxm,data_mem[indxm]);
		
		integer indx;
	always @(*)
	  for(indx=1; indx<8; indx = indx+1)
	    $display("mem%1h = %h",indx,data_mem[indx]);
*/		
endmodule