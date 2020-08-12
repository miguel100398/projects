module carry_look_ahead_4bit(
input [3:0] A,
input [3:0] B,
input c_in,
output [3:0] result,
output c_out
);

logic [3:0] s;
logic gen0, gen1, gen2, gen3;
logic prop0, prop1, prop2, prop3;
logic c0, c1, c2, c3;

Adder_1bit flla1b_0(
.A(A[0]),
.B(B[0]),
.c_in(c0),
.result(s[0]),
.c_gen(gen0),
.c_prop(prop0)
); 

Adder_1bit flla1b_1(
.A(A[1]),
.B(B[1]),
.c_in(c1),
.result(s[1]),
.c_gen(gen1),
.c_prop(prop1)
); 

Adder_1bit flla1b_2(
.A(A[2]),
.B(B[2]),
.c_in(c2),
.result(s[2]),
.c_gen(gen2),
.c_prop(prop2)
); 

Adder_1bit flla1b_3(
.A(A[3]),
.B(B[3]),
.c_in(c3),
.result(s[3]),
.c_gen(gen3),
.c_prop(prop3)
); 	
	
	
assign result = s;
assign c0 = c_in;

endmodule 