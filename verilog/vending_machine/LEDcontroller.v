module LEDcontroller(
input clk,
input blink,
input [1:0] state, 
input sw_child, sw_men, sw_women,
input en_child, en_men, en_women,
output reg ledchild, ledmen, ledwomen
);

reg select_child, select_men, select_women;

always @(*) begin
	case (state)
		2'b00: begin
			ledchild = blink;
			ledmen = blink;
			ledwomen = blink;
		end
		2'b01: begin
			ledchild = en_child;
			ledmen = en_men;
			ledwomen = en_women;
		end
		2'b10: begin
			if (select_child) begin
				ledchild = blink;
				ledmen = 0;
				ledwomen = 0;
			end else if (select_men) begin
				ledchild = 0;
				ledmen = blink;
				ledwomen = 0;
			end else if (select_women)begin
				ledchild = 0;
				ledmen = 0;
				ledwomen = blink;
			end
			else begin
				ledchild = 0;
				ledmen = 0;
				ledwomen = 0;
			end
		end
		2'b11: begin
			if (select_child) begin
				ledchild = blink;
				ledmen = 0;
				ledwomen = 0;
			end else if (select_men) begin
				ledchild = 0;
				ledmen = blink;
				ledwomen = 0;
			end else if (select_women)begin
				ledchild = 0;
				ledmen = 0;
				ledwomen = blink;
			end
			else begin
				ledchild = 0;
				ledmen = 0;
				ledwomen = 0;
			end
		end
		default: begin
			ledchild = 0;
			ledmen = 0;
			ledwomen = 0;
		end
	endcase
end

always @(posedge clk) begin
	if (state == 2'b01) begin
		if (sw_child) select_child = 1;
		else select_child = 0;
		if (sw_men) select_men  = 1;
		else select_men = 0;
		if (sw_women) select_women = 1;
		else select_women = 0;
	end
end


endmodule 