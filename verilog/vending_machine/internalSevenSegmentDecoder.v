module internalSevenSegmentDecoder(
input [3:0] decode,
output reg [6:0] decoded
);

always @(*) begin
	case(decode) 
		0: decoded[6:0] = 7'b0000001;
		1: decoded[6:0] = 7'b1001111;
		2: decoded[6:0] = 7'b0010010;
		3: decoded[6:0] = 7'b0000110;
		4: decoded[6:0] = 7'b1001100;
		5: decoded[6:0] = 7'b0100100;
		6: decoded[6:0] = 7'b0100000;
		7: decoded[6:0] = 7'b0001111;
		8: decoded[6:0] = 7'b0000000;
		9: decoded[6:0] = 7'b0000100;
		default: decoded[6:0] = 7'b111111;
	endcase
end

endmodule 