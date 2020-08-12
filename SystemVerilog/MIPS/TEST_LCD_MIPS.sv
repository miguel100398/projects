`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   15:33:39 05/10/2017
// Design Name:   MIPS
// Module Name:   D:/Verilog/MIPS/Mips_testfinal.v
// Project Name:  MIPS
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: MIPS
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module TEST_LCD_MIPS;

	// Inputs
	logic clk;
	logic rst;
	logic start;

	// Outputs
	logic [7:0] DATA;
	logic RW;
	logic EN;
	logic RS;
	logic ON;
	// Instantiate the Unit Under Test (UUT)
	Top_MIPS uut (
		.clk(clk),
		.rst(rst),
		.start(start),
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
		start = 0;

		// Wait 100 ns for global reset to finish
		#150;
        
		// Add stimulus here
		#20;
		rst = 1;
		#20;
		rst = 0;
		#20;
		rst = 1;

	end
	
	initial begin
		forever #10 clk = ~clk;
		end
      
endmodule

