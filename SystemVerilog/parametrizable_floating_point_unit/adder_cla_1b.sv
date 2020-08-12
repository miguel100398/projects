module adder_cla_1b(
input logic i_a, i_b, i_c_in,
output logic o_p, o_g, o_s, o_c_out
);

logic p, g;

carry_g_1b carry_g_1(
.i_a(i_a),
.i_b(i_b),
.o_g(g)
);

carry_p_1b carry_p_1(
.i_a(i_a),
.i_b(i_b),
.o_p(p)
);

always_comb begin
	o_p = p;
	o_g = g;
	o_s = (p ^ i_c_in);
	o_c_out = ((g) | (p&i_c_in));
end


endmodule 