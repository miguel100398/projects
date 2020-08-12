module twos_complement #(
parameter SIZE=64)(
input logic [SIZE-1:0] i_number,
output logic [SIZE-1:0] o_complement
);

always_comb begin
	o_complement = ~i_number + 1;
end

endmodule 