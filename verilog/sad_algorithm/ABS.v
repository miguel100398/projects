`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    11:10:23 09/23/2017 
// Design Name: 
// Module Name:    ABS 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////
module ABS(
    input [15:0] substract,
	 output reg [15:0] abs
	 );
	 
	 wire [15:0] complement;
	 
	 always @* begin
		 if (substract[15]) abs = complement;
		 else abs = substract;
	 end
	 
	 Twos_Complement TC1 (
		.A(substract),
		.B(complement)
	 );


endmodule
