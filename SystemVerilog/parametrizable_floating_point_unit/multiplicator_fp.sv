module multiplicator_fp #(
parameter SIZE = 32,
parameter EXPONENT = (5 + ($clog2(SIZE)-4)*3),
parameter FRACTION = (SIZE - EXPONENT - 1),
parameter BIAS = (2**(EXPONENT-1)-1))(
input logic [SIZE-1:0] i_A, i_B,
input logic [3:0] i_d_a, i_d_b,
input logic i_clk, i_en, i_rst_n, i_start,
output logic [SIZE-1:0] o_result,
output logic o_done
);

//logic [SIZE-1:0] i_A, i_B;

logic [($clog2(BIAS)):0] complement_bias;
logic [EXPONENT-1:0] exp_a, exp_b, result_exp, exp_bias, in_norm_exp;
logic [SIZE-1:0] frc_mul_a, frc_mul_b;
logic [FRACTION+4:0] result_mult, result_round, in_normalizer;
logic [2:0] GRS;
logic [SIZE-2:0] result_norm;
logic mult_done, normal_done, round;
logic en_booth, rst_booth_n, en_norm, rst_norm_n, mux_norm;
logic [FRACTION-1:0] in_round;
logic [(SIZE*2)-1:0] result_booth;
logic [SIZE-1:0] resulta;

assign exp_a = i_A [SIZE-2:FRACTION];
assign exp_b = i_B [SIZE-2: FRACTION];

assign result_exp = exp_a + complement_bias;
assign exp_bias = result_exp + exp_b;


assign result_mult = result_booth [(FRACTION*2)+1:(FRACTION*2)-(FRACTION+3)];

assign frc_mul_a [FRACTION] = 1;
assign frc_mul_b [FRACTION] = 1;
assign frc_mul_a [FRACTION-1:0] = i_A [FRACTION-1:0];
assign frc_mul_b [FRACTION-1:0] = i_B [FRACTION-1:0];
assign frc_mul_a [SIZE-1:FRACTION+1] = 0;
assign frc_mul_b [SIZE-1:FRACTION+1] = 0;

assign o_result [SIZE-1] = i_A [SIZE-1] ^ i_B [SIZE-1];
assign o_result [SIZE-2:FRACTION] = result_norm [SIZE-2:FRACTION];
assign o_result [FRACTION-1:0] = result_norm [FRACTION-1:0];

assign resulta [SIZE-1] = i_A [SIZE-1] ^ i_B [SIZE-1];
assign resulta [SIZE-2:FRACTION] = result_norm [SIZE-2:FRACTION];
assign resulta [FRACTION-1:0] = result_norm [FRACTION-1:0];

assign in_round = result_norm [FRACTION-1:0];

always_comb begin
	if(mux_norm) begin
		in_normalizer = result_mult;
		in_norm_exp = exp_bias;
	end
	else begin 
		in_normalizer = result_round;
		in_norm_exp = result_norm[SIZE-2:FRACTION];
	end
end

/*
//data
data data1(
.i_a(i_d_a),
.i_b(i_d_b),
.o_a(i_A),
.o_b(i_B)
);
*/


//Complement BIAS
twos_complement #(.SIZE  ($clog2(BIAS)+1)) tw_cmp_bias(
.i_number(BIAS),
.o_complement(complement_bias)
);


booth #(.SIZE (2*SIZE)) booth_1(
.i_A(frc_mul_a),
.i_B(frc_mul_b),
.i_clk(i_clk),
.i_en(en_booth),
.i_rst_n(rst_booth_n),
.o_result(result_booth),
.o_done(mult_done)
);

normalizer #(.SIZE (SIZE)) normalizer_1(
.i_exp(in_norm_exp),
.i_frct(in_normalizer),
.i_en(en_norm),
.i_clk(i_clk),
.i_rst_n(rst_norm_n),
.o_number(result_norm),
.o_normal(normal_done),
.o_GRS(GRS)
);

round #(.SIZE (SIZE)) round_1(
.i_GRS(GRS),
.i_number(in_round),
.o_round(round),
.o_number(result_round)
);

ctrl_mul_fp #(.SIZE (SIZE)) ctrl (
.i_clk(i_clk),
.i_rst_n(i_rst_n),
.i_mult_done(mult_done),
.i_normal_done(normal_done),
.i_round(round),
.i_start(i_start),
.o_en_mult(en_booth),
.o_rst_mult_n(rst_booth_n),
.o_en_norm(en_norm),
.o_rst_norm_n(rst_norm_n),
.o_mux_norm(mux_norm),
.o_done(o_done)
);

endmodule 