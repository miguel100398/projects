module tb;

/*
logic clk, reset;
logic [3:0] vote_inp;
logic warn;
logic [4:0] num_1, num_2, num_3, num_abs;
logic [2:0] decision; 
*/
logic clk, X, reset, Z, S;

/*
ej_1 dut(
.clk(clk),
.reset(reset),
.vote_inp(vote_inp),
.warn(warn),
.num_1(num_1),
.num_2(num_2), 
.num_3(num_3),
.num_abs(num_abs),
.decision(decision)
);
*/
ej_1 dut(
.clk(clk),
.X(X),
.RESET(reset),
.Z(Z),
.S(S)
);

initial begin 
clk = 0;
end

always #5 clk = ~clk;
initial begin
	reset = 1;
	#20;
	reset = 0;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 0;
	#10;
	X = 0;
	#10;
	X = 0;
	#10;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 1;
	#10;
	X = 1;
end

/*
initial begin
	reset = 1;
	vote_inp = 0;
	#20;
	reset = 0;
	vote_inp = 4'b0001;
	#10;
	vote_inp = 4'b0011;
	#10;
	vote_inp = 4'b0010;
	#10;
	vote_inp = 4'b0100;
	#10;
	vote_inp = 4'b1000;
	#10;
	vote_inp = 4'b1000;
	#10;
	vote_inp = 4'b0100;
	#10;
	vote_inp = 4'b0010;
	#10;
	vote_inp = 4'b0100;
	#10;
	vote_inp = 4'b1000;
	#10;
	vote_inp = 4'b0010;
	#10;
	vote_inp = 4'b0100;
	#10;
	vote_inp = 4'b0010;
	#10;
	reset = 1;
end
*/
endmodule 