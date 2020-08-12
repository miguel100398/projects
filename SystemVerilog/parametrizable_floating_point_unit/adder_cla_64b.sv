module adder_cla_64b (
input logic [63:0] i_a, i_b,
input logic i_c_in,
output logic [63:0] o_s,
output logic o_c_out
//output logic o_g, o_p
);

logic [1:0] g, p;
logic  carry;

adder_cla_32b cla_32b_0(
.i_a(i_a[31:0]),
.i_b(i_b[31:0]),
.i_c_in(i_c_in),
.o_s(o_s[31:0]),
.o_c_out(carry),
.o_g(g[0]),
.o_p(p[0])
);

adder_cla_32b cla_32b_1(
.i_a(i_a[63:32]),
.i_b(i_b[63:32]),
.i_c_in(carry),
.o_s(o_s[63:32]),
.o_c_out(o_c_out),
.o_g(g[1]),
.o_p(p[1])
);

/*
always_comb begin
	o_p = (p[0] & p[1]);
	o_g = ((g[0] & p[1]) | (g[1]));
end
*/


endmodule