module arithmetic_shift #(
parameter SIZE = 64)(
input logic [(SIZE-1):0] i_a,
input logic right,
input logic [($clog2(SIZE)+1):0] shift,
output logic [(SIZE-1):0] o_b
);

logic [(SIZE-1):0] shift_right, shift_left;

assign shift_right = i_a >> shift;
assign shift_left = i_a << shift;

always_comb begin
	if (right) begin 
		o_b[(SIZE-2):0] = shift_right[(SIZE-2):0];
		o_b[(SIZE-1)] = i_a[(SIZE-1)];
	end
	else o_b  = shift_left;
	
end

endmodule