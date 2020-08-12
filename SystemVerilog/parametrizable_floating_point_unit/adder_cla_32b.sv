module adder_cla_32b (
input logic [31:0] i_a, i_b,
input logic i_c_in,
output logic [31:0] o_s,
output logic o_c_out,
output logic o_g, o_p
);

logic [3:0] g, p;
logic [2:0] carry;

adder_cla_8b cla_8b_0(
.i_a(i_a[7:0]),
.i_b(i_b[7:0]),
.i_c_in(i_c_in),
.o_s(o_s[7:0]),
.o_c_out(carry[0]),
.o_g(g[0]),
.o_p(p[0])
);

adder_cla_8b cla_8b_1(
.i_a(i_a[15:8]),
.i_b(i_b[15:8]),
.i_c_in(carry[0]),
.o_s(o_s[15:8]),
.o_c_out(carry[1]),
.o_g(g[1]),
.o_p(p[1])
);

adder_cla_8b cla_8b_2(
.i_a(i_a[23:16]),
.i_b(i_b[23:16]),
.i_c_in(carry[1]),
.o_s(o_s[23:16]),
.o_c_out(carry[2]),
.o_g(g[2]),
.o_p(p[2])
);

adder_cla_8b cla_8b_3(
.i_a(i_a[31:24]),
.i_b(i_b[31:24]),
.i_c_in(carry[2]),
.o_s(o_s[31:24]),
.o_c_out(o_c_out),
.o_g(g[3]),
.o_p(p[3])
);

always_comb begin
	o_p = (p[0] & p[1] & p[2] & p[3]);
	o_g = ((g[0] & p[1] & p[2] & p[3]) | (g[1] & p[2] & p[3]) | (g[2] & p[3]) | (g[3]));
end

endmodule 