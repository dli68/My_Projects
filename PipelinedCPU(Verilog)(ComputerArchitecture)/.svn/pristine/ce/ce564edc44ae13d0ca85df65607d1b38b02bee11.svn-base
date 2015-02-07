module ID_EX(memHazardOut, src1Out, src0Out, imAddrIncreOut, brTypeOut,
			 zr_enOut, ov_neg_enOut, dst_addrOut, weOut, mem_weOut, mem_reOut, hltOut,
			 shamtOut, funcOut, labelSelOut, memWrtOut, newAddrOut, predictionOut,
			 /* input */
			 memHazardIn, src1In, src0In, imAddrIncreIn, brTypeIn,
			 zr_enIn, ov_neg_enIn, dst_addrIn, weIn, mem_weIn, mem_reIn, hltIn,
			 shamtIn, funcIn, labelSelIn, stallID, flushID, memWrtIn, newAddrIn,
			 predictionIn, clk, rst_n);

	output reg memHazardOut, zr_enOut, ov_neg_enOut, weOut, mem_weOut, mem_reOut, hltOut, labelSelOut, predictionOut;
	output reg [3:0] dst_addrOut, shamtOut;
	output reg [2:0] funcOut, brTypeOut;
	output reg [15:0] src1Out, src0Out, imAddrIncreOut, memWrtOut, newAddrOut;

	input memHazardIn, zr_enIn, ov_neg_enIn, weIn, mem_weIn, mem_reIn, hltIn, labelSelIn, stallID, flushID, clk, rst_n,
	predictionIn;
	input [3:0] dst_addrIn, shamtIn;
	input [2:0] funcIn, brTypeIn;
	input [15:0] src1In, src0In, imAddrIncreIn, memWrtIn, newAddrIn;

	always @(posedge clk, negedge rst_n)
		if(!rst_n)
		begin
			memHazardOut<=0;
			src1Out<=0;
			imAddrIncreOut<=0;
			brTypeOut<=0;
			zr_enOut<=0;
			ov_neg_enOut<=0;
			dst_addrOut<=0;
			weOut<=0;
			mem_weOut<=0;
			mem_reOut<=0;
			hltOut<=0;
			shamtOut<=0;
			funcOut<=0;
			labelSelOut<=0;
			memWrtOut<=0;
			newAddrOut<=0;
			predictionOut<=0;
			src0Out<=0;
		end
		else if(stallID)
		begin
			memHazardOut<=memHazardOut;
			src1Out<=src1Out;
			imAddrIncreOut<=imAddrIncreOut;
			brTypeOut<=brTypeOut;
			zr_enOut<=zr_enOut;
			ov_neg_enOut<=ov_neg_enOut;
			dst_addrOut<=dst_addrOut;
			weOut<=weOut;
			mem_weOut<=mem_weOut;
			mem_reOut<=mem_reOut;
			hltOut<=hltOut;
			shamtOut<=shamtOut;
			funcOut<=funcOut;
			labelSelOut<=labelSelOut;
			memWrtOut<=memWrtOut;
			newAddrOut<=newAddrOut;
			predictionOut<=predictionOut;
			src0Out<=src0Out;
		end
		else if(flushID)
		begin
			memHazardOut<=0;
			//src1Out<=0;
			//imAddrIncreOut<=0;
			//brTypeOut<=0;
			zr_enOut<=0;
			ov_neg_enOut<=0;
			//dst_addrOut<=0;
			weOut<=0;
			mem_weOut<=0;
			mem_reOut<=0;
			hltOut<=0;
			//shamtOut<=0;
			funcOut<=0;
			labelSelOut<=0;
			//memWrtOut<=0;
			//newAddrOut<=0;
			predictionOut<=0;
			//src0Out<=0;
		end
		else begin
			memHazardOut<=memHazardIn;
			src1Out<=src1In;
			imAddrIncreOut<=imAddrIncreIn;
			brTypeOut<=brTypeIn;
			zr_enOut<=zr_enIn;
			ov_neg_enOut<=ov_neg_enIn;
			dst_addrOut<=dst_addrIn;
			weOut<=weIn;
			mem_weOut<=mem_weIn;
			mem_reOut<=mem_reIn;
			hltOut<=hltIn;
			shamtOut<=shamtIn;
			funcOut<=funcIn;
			labelSelOut<=labelSelIn;
			memWrtOut<=memWrtIn;
			newAddrOut<=newAddrIn;
			predictionOut<=predictionIn;
			src0Out<=src0In;
		end
endmodule