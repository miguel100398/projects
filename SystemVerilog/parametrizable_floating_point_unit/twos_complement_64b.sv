module twos_complement_64b(
input  logic  [63:0]  i_number,
output logic  [63:0]  o_complement 
);


always_comb begin
	o_complement = ~i_number + 1;
end

endmodule //twos_complement_64b

