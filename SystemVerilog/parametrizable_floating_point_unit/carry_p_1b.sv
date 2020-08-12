module carry_p_1b (
input logic i_a, i_b,
output logic o_p
);

always_comb begin
	o_p = (i_a^i_b);
end

endmodule 