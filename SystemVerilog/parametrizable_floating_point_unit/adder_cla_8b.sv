module adder_cla_8b(
input logic i_c_in,
input logic [7:0] i_a, i_b,
output logic [7:0] o_s,
output logic o_c_out,
output logic o_p, o_g
);

logic [7:0] g, p;
logic [6:0] carry;

adder_cla_1b cla_1b_0(
.i_a(i_a[0]),
.i_b(i_b[0]),
.i_c_in(i_c_in),
.o_p(p[0]),
.o_g(g[0]),
.o_s(o_s[0]),
.o_c_out(carry[0])
);

adder_cla_1b cla_1b_1(
.i_a(i_a[1]),
.i_b(i_b[1]),
.i_c_in(carry[0]),
.o_p(p[1]),
.o_g(g[1]),
.o_s(o_s[1]),
.o_c_out(carry[1])
);

adder_cla_1b cla_1b_2(
.i_a(i_a[2]),
.i_b(i_b[2]),
.i_c_in(carry[1]),
.o_p(p[2]),
.o_g(g[2]),
.o_s(o_s[2]),
.o_c_out(carry[2])
);

adder_cla_1b cla_1b_3(
.i_a(i_a[3]),
.i_b(i_b[3]),
.i_c_in(carry[2]),
.o_p(p[3]),
.o_g(g[3]),
.o_s(o_s[3]),
.o_c_out(carry[3])
);

adder_cla_1b cla_1b_4(
.i_a(i_a[4]),
.i_b(i_b[4]),
.i_c_in(carry[3]),
.o_p(p[4]),
.o_g(g[4]),
.o_s(o_s[4]),
.o_c_out(carry[4])
);

adder_cla_1b cla_1b_5(
.i_a(i_a[5]),
.i_b(i_b[5]),
.i_c_in(carry[4]),
.o_p(p[5]),
.o_g(g[5]),
.o_s(o_s[5]),
.o_c_out(carry[5])
);

adder_cla_1b cla_1b_6(
.i_a(i_a[6]),
.i_b(i_b[6]),
.i_c_in(carry[5]),
.o_p(p[6]),
.o_g(g[6]),
.o_s(o_s[6]),
.o_c_out(carry[6])
);

adder_cla_1b cla_1b_7	(
.i_a(i_a[7]),
.i_b(i_b[7]),
.i_c_in(carry[6]),
.o_p(p[7]),
.o_g(g[7]),
.o_s(o_s[7]),
.o_c_out(o_c_out)
);

always_comb begin
	o_p = (p[0]&p[1]&p[2]&p[3]&p[4]&p[5]&p[6]&p[7]);
	o_g = ((g[0]&p[1]&p[2]&p[3]&p[4]&p[5]&p[6]&p[7]) | (g[1]&p[2]&p[3]&p[4]&p[5]&p[6]&p[7]) | (g[2]&p[3]&p[4]&p[5]&p[6]&p[7]) | (g[3]&p[4]&p[5]&p[6]&p[7]) | (g[4]&p[5]&p[6]&p[7]) | (g[5]&p[6]&p[7]) | (g[6]&p[7]) | (g[7]));
end


endmodule