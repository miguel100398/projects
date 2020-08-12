module desc_address(
input logic [4:0] i_address,
output logic [1:0] o_index,
output logic [2:0] o_tag
);

assign o_index = i_address [1:0];
assign o_tag = i_address [4:2];

endmodule 