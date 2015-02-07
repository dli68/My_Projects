module cpu_tb();
	reg clk, rst_n;
	wire hlt;
	wire [15:0] pc;

	cpu cpuModel(.clk(clk), .rst_n(rst_n), .hlt(hlt), .pc(pc));

	initial begin
   $monitor("//////////////////////////////////time:%t;ID current instruction %h,IF current instruction %h////////////////HD mem_reOut_exmem: %h,HD stallIF: %h,HD lwStall\n"
,$time,cpuModel.idStage.instr,cpuModel.ifStage.instr,cpuModel.mem_reOut_idex,cpuModel.ifidBuffer.stallIF,cpuModel.lwStall_id);
	
//		$dumpfile("dump.vcd");
//		$dumpvars(0,cpuModel.memStage.dataM.data_mem);
		rst_n = 0;
		#5;
		rst_n = 1;
		#8000;
	//	#8000;
		$finish;
	    end	
	initial begin
	  clk = 0;  
	end	
	always begin
	  clk = #10 ~clk;
 	end
/*	always @(posedge clk) begin
   #5;
   $display("time: %d; MEM mem_weIn: %h\n",$time,cpuModel.memStage.mem_weIn);
   $display("MEM dst_dataIn: %h;  data at addr of mem BEEF: %h \n",cpuModel.memStage.dst_dataIn,cpuModel.memStage.dataM.data_mem[48879]);
   $display("MEM mem_dataIn: %h \n",cpuModel.memStage.mem_dataIn);
   $display("EX src1In: %h; EX src0In: %h\n",cpuModel.exStage.src1In,cpuModel.exStage.src0In);
   $display("EX aluResult: %h \n",cpuModel.exStage.aluResult);
   $display("ID sr1: %h; ID src0: %h\n",cpuModel.idStage.src1,cpuModel.idStage.src0);
   $display("ID memMux1: %h; ID memMux0: %h\n",cpuModel.idStage.bypassMux.memMux1,cpuModel.idStage.bypassMux.memMux0);
   $display("ID p1: %h; ID p0: %h\n",cpuModel.idStage.p1,cpuModel.idStage.p0);
   $display("ID R4 is %h,ID R3 is %h\n",cpuModel.idStage.rfile.mem[4],cpuModel.idStage.rfile.mem[3]);
   $display("ID current instruction %h\n",cpuModel.idStage.instr);
   $display("IF current instruction %h\n",cpuModel.ifStage.instr);
   $display("HD stallIF %h\n",cpuModel.ifidBuffer.stallIF);
   $display("HD lwStall: %h \n",cpuModel.lwStall_id);
   $display("HD mem_reOut_exmem: %h \n",cpuModel.mem_reOut_idex);
   $display("\n");
       end
*/
endmodule
