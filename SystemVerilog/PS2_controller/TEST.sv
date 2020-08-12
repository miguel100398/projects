`timescale 1ns / 1ps
module TEST;

	// Inputs
	logic clk;
	logic rst;
	logic enter;
	logic ps2d;
	logic ps2c;
	

	// Outputs
	logic [7:0] DATA;
	logic RW;
	logic EN;
	logic RS;
	logic ON;

	// Instantiate the Unit Under Test (UUT)
	top uut (
		.clk(clk),
		.rst(rst),
		.enter(enter),
		.ps2d(ps2d),
		.ps2c(ps2c),
		.DATA(DATA),
		.RW(RW),
		.EN(EN),
		.RS(RS),
		.ON(ON)
	);

	initial begin
		// Initialize Inputs
		clk = 0;
		rst = 0;
		enter = 1;
		ps2d = 0;
		ps2c = 1;
		
		#20;
		rst = 1;

		// Wait 100 ns for global reset to finish
		#150;
       
		
		
		// Add stimulus here
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 1;
		#10000;
		ps2c = 1;
		ps2d = 1;
		#10000;
		
		ps2c = 0;
		ps2d = 0;
		#10000;
		ps2c = 1;
		ps2d = 0;
		#10000;

	end
	
	initial begin
		forever #10 clk = ~clk;
		end
		
      
endmodule

