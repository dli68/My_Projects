/*IF stage Top level of the Instruction Fetch stage, includes ALL input and output ports
  Output 
  imAddrIncre -current pc+1
  instr -current instruction fetched from instruction mem
  
  Input
  jumpAddr -the address fetched from register file; used by jr instruction.
  labelAddr -the address fetched from WB stage; used by jal & branch instructions.
 */
module IF(imAddr, imAddrIncre, instr, jumpAddr, labelAddr, 
          hlt, stallIF, labelSelect, jumpSelect, clk, rst_n);
    /////input/output declaration/////
	output [15:0] imAddrIncre, instr, imAddr;
	input [15:0] jumpAddr, labelAddr;
	input clk, rst_n, hlt, stallIF, labelSelect, jumpSelect;

	/*testing !!!!!!!*/
	assign rd_en = (~hlt) & (~stallIF);///Read Enabler for Instruciton Memory
	PC counter(imAddr, imAddrIncre, jumpAddr, labelAddr, clk, rst_n, hlt, stallIF, labelSelect, jumpSelect);
	IM instr_mem(clk, imAddr, rd_en, instr);
endmodule

module PC(imAddr, imAddrIncre, jumpAddr, labelAddr, clk, rst_n, hlt, stallIF, labelSelect, jumpSelect);
	output [15:0] imAddrIncre;
	output reg [15:0] imAddr;
	input [15:0] jumpAddr, labelAddr;
	input clk, rst_n, hlt, stallIF, labelSelect, jumpSelect;

	assign imAddrIncre = imAddr + 1;
	wire [15:0] hltMux = hlt ? imAddr : imAddrIncre;
	wire [15:0] stallMux = stallIF ? imAddr : hltMux;
	wire [15:0] jumpMux = jumpSelect ? jumpAddr : stallMux;
	wire [15:0] labelMux = labelSelect ? labelAddr : jumpMux;

	always @(posedge clk, negedge rst_n)
		if(!rst_n)
		begin
			imAddr <= 16'h0000;
		end
		else
		begin
			imAddr <= labelMux;
		end
endmodule

module IM(clk,addr,rd_en,instr);
	input clk;
	input [15:0] addr;
	input rd_en;			// asserted when instruction read desired

	output reg [15:0] instr;	//output of insturction memory

	reg [15:0]instr_mem[0:65535];

	/////////////////////////////////////
	// Memory is latched on clock low //
	///////////////////////////////////
	always @(addr,rd_en,clk)
	  if (~clk & rd_en)
	    instr <= instr_mem[addr];

	initial begin
	  $readmemh("instr.hex",instr_mem);
	end
endmodule
