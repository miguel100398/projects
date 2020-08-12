module Adder_1bit(
input A, B,
input c_in,
output c_out,
output result,
output c_gen,
output c_prop
);

logic [1:0] suma;
logic generator;
logic propagator;

always_comb begin
	suma = A + B + c_in;
	generator = (A & B);
	propagator = ((A^B) & c_in);
end

assign result = suma [0];
assign c_out = suma [1];
assign c_gen =generator;
assign c_prop = propagator;


endmodule 