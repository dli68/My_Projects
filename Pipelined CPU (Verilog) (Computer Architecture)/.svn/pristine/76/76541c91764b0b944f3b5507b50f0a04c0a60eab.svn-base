/////////////////copyright @ Di Li  Shunmiao Xu//////////
module mem_top(input clk,rst_n,
	       input[15:0]i_addr,d_addr,wrt_data,
	       input top_I_re,top_D_re,top_D_we,
	       output[15:0]instr,rd_data,
	       output freez);
//////varialbes///////////
	wire I_we,I_re,D_re,D_we,M_we,M_re;
	wire wdirty,rdy;
	wire [63:0] I_rd_data,D_rd_data,M_rd_data,I_wr_data,D_wr_data,M_wr_data;
	wire [7:0] D_wrt_data_tag,USELESS1;
	wire I_hit,D_hit,D_dirty;
	wire [13:0] addr_from_ctrl,D_addr;
//////modules initialization///////
/*MEM CONTROL*/
mem_ctrl control(.clk(clk),.rst_n(rst_n),.instr(instr),.rd_data(rd_data),.i_addr(i_addr),.d_addr(d_addr),.wdirty(wdirty),
.I_rd_data(I_rd_data),.D_rd_data(D_rd_data),.I_wr_data(I_wr_data),.D_wr_data(D_wr_data),.M_wr_data(M_wr_data),.M_rd_data(M_rd_data),
.I_we(I_we),.I_re(I_re),.D_re(D_re),.D_we(D_we),.M_we(M_we),.M_re(M_re),.I_hit_real(I_hit),.D_hit_real(D_hit),.D_dirty_real(D_dirty),
.M_rdy(rdy),.top_I_re(top_I_re),.top_D_re(top_D_re),.top_D_we(top_D_we),.freez(freez),.wrt_data(wrt_data),.D_addr(D_addr),
.addr_to_mem(addr_from_ctrl),.D_wrt_data_tag(D_wrt_data_tag));
/*I_CACHE*/
cache icache(.clk(clk),.rst_n(rst_n),.addr(i_addr[15:2]),.wr_data(I_wr_data),.wdirty(1'b0),.we(I_we),
.re(I_re),.rd_data(I_rd_data),.tag_out(USELESS1),.hit(I_hit),.dirty(USELESS0));
/*D_CACHE*/
cache dcache(.clk(clk),.rst_n(rst_n),.addr(D_addr),.wr_data(D_wr_data),.wdirty(wdirty),.we(D_we),
.re(D_re),.rd_data(D_rd_data),.tag_out(D_wrt_data_tag),.hit(D_hit),.dirty(D_dirty));
/*MAIN MEMORY*/
unified_mem mem(.clk(clk),.rst_n(rst_n),.addr(addr_from_ctrl),.re(M_re),.we(M_we),
.wdata(M_wr_data),.rd_data(M_rd_data),.rdy(rdy));	
endmodule
