module Test_LCD;

	// Inputs
	logic clk;
	logic rst;

	// Outputs
	logic  RS;
	logic RW;
	logic E;
	logic [7:0] D;

	// Instantiate the Unit Under Test (UUT)
	LCD uut (
		.clk(clk), 
		.rst(rst),
		.RS(RS),
		.RW(RW),
		.E(E),
		.D(D)
	);

	initial begin
		// Initialize Inputs
		clk = 0;
		rst = 0;

		// Wait 100 ns for global reset to finish
		#100;
        
		// Add stimulus here
		
		rst=1;
		#20;
		rst = 0;

	end
	
	initial forever #10 clk = ~clk;
      
endmodule

