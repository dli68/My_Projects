/* MEM state
  Output
  dst_data -the data that is going to be written back to register file.
  LabelSelOut -asserted by LabelJumpLogic, and will be passed back to IF stage.

  Input
  weIn - the register file write enable passed down from ID stage; significant to WB_MUX & LabelJumplogic.
  mem_reIn/mem_weIn  - memory read & write enables passed down from ID stage
  labelSelIn  - passed down from ID stage; used to distinguish branch & jal instructions.
  brTypeIn  -signal passed down from ID stage; used to determine branch type.
  dst_dataIn   -address port of memory
  mem_dataIn   -data that is writing into memory

  Module
  WB_MUX -wire select of dst_data based on which instruction type it is among 1.lw 2.jal 3.others.
  LabelJumpLogic -based on signals weIn & labelSelIn & brTypeIn signals, the module distinguish between branch & jal instructions, and whether to branch or not; it asserts LabelSelOut signal.
*/


module MEM(dst_data,
		  /* input */
		dst_dataIn, mem_reIn, mem_dataIn, mem_rd_data);
	output [15:0] dst_data;
	input mem_reIn;
	input [15:0] dst_dataIn, mem_dataIn, mem_rd_data;

	wire [15:0] mem_data;
	WB_MUX wbMux(dst_data, mem_rd_data, dst_dataIn, mem_reIn);
endmodule

module WB_MUX(dst_data, mem_data, alu_data, mem_re);

	output [15:0] dst_data;
	input [15:0] alu_data, mem_data;
	input mem_re;

	assign dst_data = mem_re ? mem_data : alu_data;

endmodule