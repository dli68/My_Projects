module WB_IF(rf_wrt_data, ALU_result, mem_data, memToReg, jal_sel, instr_addr_incre);
	// this is a comment
	output [15:0] rf_wrt_data;
	input [15:0] ALU_result, mem_data, instr_addr_incre;
	input memToReg, jal_sel;

	wire [15:0] memToReg_mux = memToReg ? mem_data : ALU_result;
	assign rf_wrt_data = jal_sel ? instr_addr_incre : memToReg_mux;
endmodule