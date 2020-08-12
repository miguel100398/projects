module des_norm #(
parameter SIZE = 64,
parameter EXPONENT = (5 + ($clog2(SIZE)-4)*3),
parameter FRACTION = (SIZE - EXPONENT - 1),
parameter BIAS = (2**(EXPONENT-1)-1))(
input logic i_clk, i_rst_n, i_en,
input logic [SIZE-2:0] i_A, i_B,
output logic [EXPONENT-1:0] o_exp_A, o_exp_B,
output logic [FRACTION+1:0] o_frct_A, o_frct_B,
output logic o_des_norm
);

logic [EXPONENT-1:0] exp_A, exp_B;
logic [FRACTION:0] frct_A, frct_B;

assign o_exp_A = exp_A;
assign o_exp_B = exp_B;
assign o_frct_A [FRACTION:0] = frct_A;
assign o_frct_B [FRACTION:0] = frct_B;
assign o_frct_A [FRACTION+1] = 0;
assign o_frct_B [FRACTION+1] = 0;


always_ff @ (posedge i_clk or negedge i_rst_n) begin
	if (!i_rst_n) begin
		exp_A = i_A [SIZE-2:FRACTION];
		exp_B = i_B [SIZE-2:FRACTION];
		frct_A [FRACTION] = 1;
		frct_B [FRACTION] = 1;
		frct_A [FRACTION-1:0] = i_A [FRACTION-1:0];
		frct_B [FRACTION-1:0] = i_B [FRACTION-1:0];
		o_des_norm = 0;
	end else if (i_en) begin
		if (exp_A > exp_B) begin
			exp_A = exp_A;
			exp_B = exp_B + 1;
			frct_A = frct_A;
			frct_B = frct_B >> 1;
			o_des_norm = 0;
		end else if (exp_B > exp_A) begin
			exp_A = exp_A + 1;
			exp_B = exp_B;
			frct_A = frct_A >> 1;
			frct_B = frct_B;
			o_des_norm = 0;
		end
		else begin 
			exp_A = exp_A;
			exp_B = exp_B;
			frct_A = frct_A;
			frct_B = frct_B;
			o_des_norm = 1;
		end
	end
	else begin
		exp_A = exp_A;
		exp_B = exp_B;
		frct_A = frct_A;
		frct_B = frct_B;
		o_des_norm = o_des_norm;
	end
end

endmodule 