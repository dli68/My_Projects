/////////////////copyright @ Di Li  Shunmiao Xu//////////
/* cache top level */
module memory(instr, freez, rd_data,
			 /*input*/
			  i_addr, clk, rst_n, d_addr, i_re, d_re, d_we, wrt_data
			 );
	output [15:0] instr, rd_data;
	output freez;

	input [15:0] i_addr, d_addr, wrt_data;
	input clk, rst_n, i_re, d_we, d_re;

	wire [13:0] mem_addr, daddr, iaddr;
	wire [63:0] mem_wdata, mem_rd_data, dwr_data, iwr_data, ird_data, drd_data;
	wire [7:0] dtag_out, itag_out;
	wire mem_re, mem_we, mem_rdy, dwdirty, dwe, dre, dhit, ddirty, iwdirty, iwe,
	 ire, ihit, idirty;

/* initialize memory modules */
	cache icache(clk,rst_n,iaddr,iwr_data,iwdirty,iwe,ire,ird_data,itag_out,ihit,idirty);
	cache dcache(clk,rst_n,daddr,dwr_data,dwdirty,dwe,dre,drd_data,dtag_out,
		dhit,ddirty);
	unified_mem mem(clk,rst_n,mem_addr,mem_re,mem_we,mem_wdata,mem_rd_data,mem_rdy);
/* initialize control module */
	cache_ctrl ctrl(instr, rd_data, mem_wdata, mem_we, mem_re,mem_addr,
					iaddr, daddr, freez, iwe, ire, dwe, dre, iwdirty,
					dwdirty, iwr_data, dwr_data,
				 	/* input */
				  	i_addr, d_addr, dtag_out, mem_rd_data, wrt_data, drd_data,
				  	ird_data, mem_rdy, ihit, dhit, ddirty, i_re,
				  	d_re, d_we, clk, rst_n
					);
endmodule
