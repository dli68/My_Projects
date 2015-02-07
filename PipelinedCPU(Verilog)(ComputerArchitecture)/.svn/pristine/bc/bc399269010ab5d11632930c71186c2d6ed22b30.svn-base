module IF_tb();  
  
	wire [15:0] instr, pc_incre;
	reg label_select, jump_select, clk, rst_n, hlt, stall_IF, flush_IF;
	reg [15:0] Jump_addr, label_addr;

        IF myif(pc_incre, instr, Jump_addr, label_addr, 
                hlt, stall_IF, label_select, jump_select, flush_IF, clk, rst_n);

	always @(posedge clk) begin
		#1;		
$display("%h; 1. instr: %h; pc+1: %h; systick: %t,",myif.instr,instr,pc_incre,$time);
	end

	// set reset and let cpu run
	initial begin
	//Part 1
		rst_n = 0;
		#1;
	 	rst_n=1;
		hlt = 0;
		label_select = 0;
		jump_select = 0;
		flush_IF = 0;
		stall_IF=0;
		#260;
		$display("part1 finishes,part2 starts");
	//Part 2
	        Jump_addr = 16'h0003;
		jump_select=1;
		#20;
		jump_select=0;
		label_addr = 16'h0004;
		label_select =1;
		#20;
		label_select=0;
		hlt=1;
		#20;
		hlt=0;
		stall_IF=1;
		#20;
		stall_IF=0;
		flush_IF=1;
		#20;
		$display("part2 finishes");
		$finish();		
	end
	initial begin
	  clk = 0;  
	end	
	// generate clock
	always clk = #10 ~clk;
endmodule
