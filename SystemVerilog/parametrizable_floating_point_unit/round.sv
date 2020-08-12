module round #(
parameter SIZE =64,
parameter EXPONENT = (5 + ($clog2(SIZE)-4)*3),
parameter FRACTION = (SIZE - EXPONENT - 1),
parameter BIAS = (2**(EXPONENT-1)-1))(
input logic [2:0] i_GRS,
input logic [FRACTION-1:0] i_number,
output logic o_round,
output logic [FRACTION+4:0] o_number
);

logic [FRACTION+1:0] number;

assign number [FRACTION+1] = 0;
assign number [FRACTION] = 1;
assign number [FRACTION-1:0] = i_number;

assign o_number [2:0] = 0;

always_comb begin
	if (! i_GRS[2]) begin
		o_number [FRACTION+4:3] = number;
		o_round = 0;
	end 
	else begin
		if ( i_GRS[1]) begin
			o_number [FRACTION+4:3] = number + 1;
			o_round = 1;
		end
		else if ( i_GRS[0]) begin
			o_number [FRACTION+4:3] = number + 1;
			o_round = 1;
		end
		else begin
			if ( number[0] ) begin
				o_number [FRACTION+4:3] = number +1;
				o_round = 1;
			end
			else begin
				o_number [FRACTION+4:3] = number;
				o_round = 0;
			end
		end
	end
end

endmodule 