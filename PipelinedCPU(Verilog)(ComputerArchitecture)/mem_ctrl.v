/////////////////copyright @ Di Li  Shunmiao Xu//////////
module mem_ctrl(output reg[15:0]instr,rd_data,
		output reg[13:0]addr_to_mem,D_addr,
		input [15:0]i_addr,d_addr,wrt_data,
		input clk,rst_n,
		input [63:0] I_rd_data,D_rd_data,M_rd_data,
		input I_hit_real,D_hit_real,D_dirty_real,M_rdy,
		input top_I_re,top_D_re,top_D_we,
		input [7:0] D_wrt_data_tag,
		output reg I_we,I_re,D_re,D_we,M_we,M_re,wdirty,freez,
		output reg [63:0] I_wr_data,D_wr_data,M_wr_data);
////////////////////variables////////
	reg M_rdy_saved;
	reg [1:0] currstate;
	reg [1:0] nextstate;
	wire [1:0] I_block_offset,D_block_offset,rdy_init;
	wire [63:0] I_select,D_select,M_select_for_I,M_select_for_D,
		    D_wr_data_select,D_wr_data_mask,D_wr_clear_mask;
	reg [1:0] rdy_count;
	reg [1:0] rdy_count_saved;
	wire I_hit,D_hit,D_dirty; 
	wire [13:0] dirty_write_back_addr;
///////////////////general wire assignments///////
	assign I_block_offset=i_addr[1:0];
	assign D_block_offset=d_addr[1:0];
	assign I_hit= (top_I_re) ? I_hit_real : 1; 
	assign D_hit= (top_D_re || top_D_we) ? D_hit_real : 1; 
	assign D_dirty= (top_D_re || top_D_we) ? D_dirty_real : 0; 
	assign I_select= I_rd_data>>(I_block_offset*16);
	assign D_select= D_rd_data>>(D_block_offset*16);
	assign M_select_for_I= M_rd_data>>(I_block_offset*16);
	assign M_select_for_D= M_rd_data>>(D_block_offset*16);
	assign D_wr_clear_mask= D_rd_data & ~(64'hFFFF << (16*D_block_offset));
	assign D_wr_data_mask= wrt_data << (D_block_offset*16);
	assign D_wr_data_select= D_wr_data_mask | D_wr_clear_mask;
	assign dirty_write_back_addr= {D_wrt_data_tag,d_addr[7:2]};
	assign rdy_init=   /*rdy_count will be not quite invalid if either or neither of caches is not being used at all, but this is how it's designed for overall functionality*/ 
	  (I_hit && D_hit) ? 0 :
	  ((~I_hit && D_hit) || (I_hit && ~D_hit && ~D_dirty)) ? 1 :
	  ((I_hit && ~D_hit && D_dirty) || (~I_hit && ~D_hit && ~D_dirty)) ? 2 :
  	  (~I_hit && ~D_hit && D_dirty) ? 3 : 2;  
///////////////////local parameters/////
/*FSM state definition*/
  localparam      
 	S0=2'h0,  //starts
	MH=2'h1,  //Miss Hit wait
	HM=2'h2,  //Hit Miss wait
	MM=2'h3;  //Miss Miss wait
///////////////////sequencial logic (flip-flop)////
	always @(posedge clk,negedge rst_n) begin
	  if(!rst_n) begin
	    currstate<=S0;
	    rdy_count_saved<=0;
	    M_rdy_saved<=0;
	  end
	  else begin
	    currstate<=nextstate;
	    rdy_count_saved<=rdy_count;
	    M_rdy_saved<=M_rdy;
	  end
	end 
////////////////combinational logic/////
	always @(*)
	begin
	  I_we=0;
	  I_re=0;
	  D_re=0;
	  D_we=0;
	  M_we=0;
	  M_re=0;
	  wdirty=0;
	  rdy_count=0;
	  nextstate=S0;
	  freez=0;
	  instr=0;
	  rd_data=0;
	  D_wr_data=0;
	  I_wr_data=0;
	  M_wr_data=0;
	  addr_to_mem=0;
	  D_addr=d_addr[15:2];
	  case(currstate)
	     S0:begin
		  if(top_I_re || top_D_re || top_D_we) begin //at leaset one of I_cache & D_cache is being used
 		     /*open "swithces" to set rdy_init */
		     if(top_I_re) I_re=1;
		     if(top_D_re || top_D_we) D_re=1;
		   //  if(top_D_we) begin D_re=1; D_we=1; end
        	     /*based on rdy_init, singlas are assigned*/
		     if(rdy_init==0) begin 
		       if(top_I_re) instr=I_select[15:0];
		       if(top_D_re) rd_data=D_select[15:0];
		       if(top_D_we) begin 
			 D_we=1;
			 D_wr_data=D_wr_data_select;
			 wdirty=1;
		       end
		     end
		     else if (~I_hit && D_hit) begin
			 rdy_count=rdy_init;
			 addr_to_mem=i_addr[15:2];
			 M_re=1;
			 freez=1;
		         nextstate=MH;
		     end
		     else begin   ///cases when MM & HM 
			 rdy_count=rdy_init;
			 if(!D_dirty) begin
			   D_we=1;
			   addr_to_mem=d_addr[15:2];
			   M_re=1; 
			 end
			 else begin
			   addr_to_mem=dirty_write_back_addr;
			   M_we=1;
			 end
			 freez=1;
			 if(I_hit && ~D_hit) nextstate=HM;
			 else nextstate=MM;
		     end
		  end
		end
	     MH:begin
		  if(M_rdy) begin 
		     /*finishes I_cache miss handler*/
		     I_we=1;
		     I_wr_data=M_rd_data;
		     instr=M_select_for_I[15:0];
		     /*can be either a D_cache hit or didn't use D_cache at all*/
		     if(top_D_re) begin
			D_re=1;
			rd_data=D_select[15:0];
		     end
		     if(top_D_we) begin 
			D_we=1;
			D_wr_data=D_wr_data_select;
			wdirty=1;
		     end
		  end
		  else begin
		     nextstate=MH;
		     rdy_count=rdy_count;
		     freez=1;
		  end
	     	end
	     HM:begin
		  if(rdy_count_saved==2 && M_rdy_saved) begin
		      M_re=1;
		      addr_to_mem=d_addr[15:2];
		      freez=1;
		      rdy_count=rdy_count_saved-1; 
		      nextstate=HM;
		  end
		  else if(M_rdy) begin
		     if(rdy_count_saved==2) begin
		       D_re=1;
		       D_addr=dirty_write_back_addr;
		       M_wr_data=D_rd_data;
		     end
		     /*prepare for reading out data from mem if it is D_cache dirty miss*/
		     if(rdy_count_saved==1) begin
		       /*finishes D_cache miss handler*/	
		       if(top_D_re) begin 
		     	 D_we=1;
		         D_wr_data=M_rd_data;
			 rd_data=M_select_for_D[15:0];
		       end
		       if(top_D_we) begin 
			 D_we=1;
			 D_wr_data=D_wr_data_select;
			 wdirty=1;
		       end
		       /*can be either a I_cache hit or didn't use I_cache at all*/
		       if(top_I_re) begin
			 I_re=1;
			 instr=I_select[15:0];
		       end
		     end
		     else begin
		         rdy_count=rdy_count_saved; 
			 freez=1;
			 nextstate=HM;
		     end
		  end
		  else begin
		     nextstate=HM;
		     rdy_count=rdy_count_saved;
	             freez=1;
		  end
		end
	     default:begin  // this state is MM 
		       /*prepare for reading out data from mem if it is D_cache dirty miss*/
		       if(rdy_count_saved==3 && M_rdy_saved) begin
		           M_re=1;
		           addr_to_mem=d_addr[15:2];
		           freez=1;
		           rdy_count=rdy_count_saved-1; 
		           nextstate=MM;
		       end
		       /*prepare for reading out data from mem for I_cache miss*/
		       else if(rdy_count_saved==2 && M_rdy_saved) begin
		          addr_to_mem=i_addr[15:2];
		          M_re=1;
		          freez=1;
		          rdy_count=rdy_count_saved-1; 
		          nextstate=MM;
		       end
		       else if(M_rdy) begin
		           if(rdy_count_saved==3) begin
		              D_re=1;
		              D_addr=dirty_write_back_addr;
		              M_wr_data=D_rd_data;
		           end
			   if(rdy_count_saved==2) begin
			     /*finishes D_cache write miss*/
		             if(top_D_re) begin 
		     	        D_we=1;
		                D_wr_data=M_rd_data;
		             end
		             if(top_D_we) begin 
			        D_we=1;
			        D_wr_data=D_wr_data_select;
			        wdirty=1;
		            end
			  end
		          if(rdy_count_saved==1) begin
		            /*finishes D_cache read miss handler*/	
		       	    if(top_D_re) begin
			 	D_re=1;
				rd_data=D_select[15:0];
			    end
		     	    /*finishes I_cache miss handler*/
		     	    I_we=1;
		     	    I_wr_data=M_rd_data;
		    	    instr=M_select_for_I[15:0];
			  end
			  else begin
			     rdy_count=rdy_count_saved;
			     nextstate=MM;
			     freez=1;
			  end
		       end
	   	       else begin
			  nextstate=MM;
			  rdy_count=rdy_count_saved;
			  freez=1;
		       end		
		     end
	  endcase
	end
endmodule
