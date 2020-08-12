module adder_fp #(
parameter SIZE = 64,
parameter EXPONENT = (5 + ($clog2(SIZE)-4)*3),
parameter FRACTION = (SIZE - EXPONENT - 1),
parameter BIAS = (2**(EXPONENT-1)-1))(
input logic i_clk, i_rst_n, i_en,
input logic [SIZE-1:0] i_A, i_B,
output logic [SIZE-1:0] result
);

logic des_norm;
logic [SIZE-2:0] in_A_desnorm, in_B_desnorm;
logic [EXPONENT-1:0] out_exp_A_desnorm, out_exp_B_desnorm;
logic [FRACTION+1:0] out_frct_A_desnorm, out_frct_B_desnorm, add_A, add_B, cmp_add_A, cmp_add_B, result_add, result_add_cmp, result_add_final;
logic en_des_norm, en_norm, normal;
logic rst_n_des_norm, rst_n_norm;
logic [2:0] GRS;
logic [FRACTION+4:0] in_norm;
logic [SIZE-2:0] out_norm;
logic [FRACTION+4:0] out_round;
logic [EXPONENT-1:0] in_exp_norm;

assign in_A_desnorm = i_A [SIZE-2:0];
assign in_B_desnorm = i_B [SIZE-2:0];
assign result_add = add_A + add_B;


always_comb begin
	if (i_A [SIZE-1]) add_A = cmp_add_A;
	else add_A = out_frct_A_desnorm;
end

always_comb begin
	if (i_B [SIZE-1]) add_B = cmp_add_B;
	else add_B = out_frct_B_desnorm;
end

always_comb begin
	if (result_add[FRACTION+1]) begin
		result [SIZE-1] = 1;
		result_add_final = result_add_cmp;
	end
	else begin
		result [SIZE-1] = 0;
		result_add_final = result_add;
	end
end

always_comb begin
	if (mux_norm) begin
		in_exp_norm = out_exp_A_desnorm;
		in_norm [FRACTION+4:FRACTION+1] = 0;
		in_norm = [FRACTION:0] = result_add_final [FRACTION:0];
	end
	
end


des_norm #(.SIZE(SIZE)) des_norm1(
.i_clk(i_clk)
.i_rst_n(rst_n_des_norm),
.i_en(en_des_norm),
.i_A(in_A_desnorm),
.i_B(in_B_desnorm),
.o_exp_A(out_exp_A_desnorm),
.o_exp_B(out_exp_B_desnorm),
.o_frct_A(out_frct_A_desnorm),
.o_frct_B(out_frct_B_desnorm),
.o_norm(des_norm)
);

normalizer #(.SIZE (SIZE)) normalizer1(
.i_clk(i_clk),
.i_rst_n(rst_n_norm),
.i_en(en_norm),
.i_exp(in_exp_norm),
.i_frct(in_norm),
.o_number(out_norm),
.o_normal(normal),
.o_GRS(GRS)
);

round #(.SIZE (SIZE)) round1(
.i_GRS(GRS),
.i_number(out_norm[FRACTION-1:0]),
.o_round(round),
.o_number(out_round)
);

twos_complement #(.SIZE  (FRACTION+1)) tw_cmp_A(
.i_number(out_frct_A_desnorm),
.o_complement(cmp_add_A)
);

twos_complement #(.SIZE  (FRACTION+1)) tw_cmp_B(
.i_number(out_frct_B_desnorm),
.o_complement(cmp_add_B)
);

twos_complement #(.SIZE  (FRACTION+1)) tw_cmp_add(
.i_number(result_add),
.o_complement(result_add_cmp)
);



endmodule 