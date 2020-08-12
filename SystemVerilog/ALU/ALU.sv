module ALU(
	input [31:0] A, B
	);

	carry_look_ahead_4bit cryla1(
	.A (A[3:0]),
	.B (B[3:0]),
	.c_in(),
	.result(),
	.c_out()
	);

endmodule 