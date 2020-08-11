`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   07:48:48 10/03/2017
// Design Name:   SAD
// Module Name:   D:/Verilog/SAD/Test_data_path.v
// Project Name:  SAD
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: SAD
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module Test_data_path;

	// Inputs
	reg clk;
	reg go;
	reg Mrst;
	reg Mren;
	reg Mren_reg;

	// Outputs
	wire [31:0] sad;

	// Instantiate the Unit Under Test (UUT)
	SAD uut (
		.clk(clk), 
		.go(go), 
		.Mrst(Mrst), 
		.sad(sad), 
		.Mren(Mren), 
		.Mren_reg(Mren_reg)
	);

	initial begin
		// Initialize Inputs
		clk = 0;
		go = 0;
		Mrst = 0;
		Mren = 0;
		Mren_reg = 0;

		// Wait 100 ns for global reset to finish
		#100;
        
		// Add stimulus here
		Mrst = 1;
		#20;
		Mrst = 0;
		#20;
		Mren = 1;
		#100;
		Mren = 0;
		#50;
		Mren = 1;
		#100;
		Mren = 0;
		#20;
		Mren_reg = 1;

	end
	
	initial forever #10 clk = ~clk;
      
endmodule

