module tb_SAD (); /* this is automatically generated */

	logic clk;

	// clock
	initial begin
		clk = 0;
		forever #5 clk = ~clk;
	end


	// (*NOTE*) replace reset, clock, others

	logic                rst;
	logic                go;
	logic [7:0]          A [15:0];
	logic [7:0]          B [15:0];
	logic [7:0]          A_old;
	logic [7:0]          B_old;
	logic          [7:0] AB_addr;
   logic          [8:0] AB_addr_old;
	logic         [31:0] sad;
	logic         [31:0] sad_old;
	logic [7:0] mem [511:0];
	
	SAD inst_SAD (.clk(clk), .rst(rst), .go(go), .A(A), .B(B), .AB_addr(AB_addr), .sad(sad));
	SAD_old int_SAD_old(.clk(clk), .Mrst(~rst), .go(~go), .A_data(A_old), .B_data(B_old), .AB_addr(AB_addr_old), .sad(sad_old));

		initial begin
      rst = 0;
      go=1;
      #20;
      rst = 1;
  		go = 0;
      #20;
      go = 1;
      #20;
	end
initial begin
  integer q;
	for (q=0; q<513; q++) begin 
		mem [q] = $urandom();
	end
	
end

always_comb begin
	integer a;
	for (a=0; a<16; a++)begin
		A [ a ] = mem [a + (AB_addr*16)];
		B [ a ] = mem [a + 255 + (AB_addr*16)];
	end
end

always_comb begin
	A_old = mem [AB_addr_old];
	B_old = mem [AB_addr_old + 255];
end



endmodule
