module data (
input logic [3:0] i_a, i_b,
output logic [31:0] o_a, o_b
);



always_comb begin
	case (i_a) 
		1:	o_a = 32'b 01000001110000000000000000000000; //24
		2:	o_a = 32'b 01000010000000000000000000000000; //32
		4:	o_a = 32'b 01000000010010010000111111011010; //pi 
		8:	o_a = 32'b 01000100100110001100011100001010; //1222.22
		default: o_a = 32'b 0;
	endcase
end

always_comb begin
	case (i_b) 
		1:	o_b = 32'b 00111111100000000000000000000000; //1
		2:	o_b = 32'b 01000000000000000000000000000000; //2
		4:	o_b = 32'b 01000001101101000000000000000000; //22.5
		8:	o_b = 32'b 01000000101000000000000000000000; //5
		default: o_b = 32'b 0;
	endcase
end


endmodule 