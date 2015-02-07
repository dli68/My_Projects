all:compile instr run
compile:
	iverilog -o pipelined_design IF.v ID.v EX.v MEM.v IF_ID.v ID_EX.v EX_MEM.v MEM_WB.v HD.v cpu.v Di_CPU_tb.v
instr:
	perl asmbl.pl Di_inst.txt > instr.hex
run:
	vvp pipelined_design
