/* hazard detection and logic control */
module HAZARD_HANDLER(realHlt, flushIF_ID, flushID_EX, flushEX_MEM, flushMEM_WB, stallIF_ID, stallID_EX,
	                  stallEX_MEM, stallMEM_WB,
	                  /*input*/
	                  jrSel, lwStall, LabelSel, hltMEM_WB, hltIF_ID, prediction, freeze/*, rst_n*/);
	output flushIF_ID, flushID_EX, flushEX_MEM, stallIF_ID, stallID_EX, stallEX_MEM, realHlt, flushMEM_WB, stallMEM_WB;
	input jrSel, lwStall, LabelSel, hltMEM_WB, hltIF_ID, prediction, freeze/*, rst_n*/;

	assign flushIF_ID = freeze ? 0 : ((jrSel | LabelSel | prediction) ? 1 : 0);
	assign flushID_EX = (LabelSel| lwStall) ? 1 : 0;
	assign flushEX_MEM = 0;
	assign flushMEM_WB = 0;

	// reg rst_fi;
	// always @(rst_n)
 	// 		rst_fi <= rst_n;

	//wire rst_filt = rst_n;//rst_fi & rst_n;

	assign stallIF_ID = (lwStall | freeze) ? 1 : ((hltIF_ID & ~LabelSel & ~jrSel) ? 1 : 0);
	assign stallID_EX = freeze;
	assign stallEX_MEM = freeze;
	assign stallMEM_WB = freeze;

	assign realHlt = hltMEM_WB;

endmodule