module sevenSegmentDecoder(
input clk, rst,
input [6:0] cash,
input refund,
output reg [3:0] Anode_Activate,
output reg [7:0] Segment
);

wire [7:0] Segmentsign, segment0, segment1, segment2;
reg[19:0] refresh;
wire[1:0] activating_counter;

wire [3:0] units, tens;
assign units = cash%10;
assign tens = cash/10;

assign segment0[7] = 1;
assign segment1[7] = 0;
assign segment2[7] = 1;
assign activating_counter = refresh[19:18];

always @(posedge clk or posedge rst) begin
	if (rst) begin
		refresh <= 0;
	end else begin
		refresh<= refresh+1;
	end
end

always @(*) begin
	case (activating_counter)
		2'b00: begin
			Anode_Activate = 4'b0111;
			Segment = Segmentsign;
			
		end
		2'b01: begin
			Anode_Activate = 4'b1011;
			Segment = segment0;
		end
		2'b10: begin
			Anode_Activate = 4'b1101;
			Segment = segment1;
		end
		2'b11: begin
			Anode_Activate = 4'b1110;
			Segment = segment2;
		end
	endcase
end


internalSevenSegmentDecoder tens_decoder(
.decode(tens),
.decoded(segment0[6:0])
);


internalSevenSegmentDecoder units_decoder(
.decode(units),
.decoded(segment1[6:0])
);

internalSevenSegmentDecoder decimals_decoder(
.decode(0),
.decoded(segment2[6:0])
);

assign Segmentsign[7:1] = 7'b1111111;
assign Segmentsign[0] = ~refund;


endmodule 