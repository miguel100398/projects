module LCD(
	input clk,
	input rst,
	output RS,
	output RW,
	output E,
	output [7:0] D
	
);

	logic [31:0] addr;
	logic [9:0] instruction;
	logic finish;
	logic sum;
	logic en_sum;
	logic en_temp;
	logic rst_temp;
	logic rst_sum;
	
	assign D [7:0] = instruction [7:0];
	assign RS = instruction [8];
	assign RW = instruction [9];

/*memory mem1(
	.clk(clk),
	.rst(rst),
	.wr_addr(),
	.wr_en(),
	.wr_data(),
	.rd_addr(),
	.rd_data()
);
*/

//Counter
always_ff @(posedge clk) begin
	if (rst_sum) 
		addr <= 0;
	else if (en_sum) 
		addr <= addr + 1;
end

always_comb begin
	if (addr == 14)
		sum = 1;
	else
		sum = 0;
end

//FSM
FSM fsm1(
	.clk(clk),
	.rst(rst),
	.finish(finish),
	.sum(sum),
	.en_temp (en_temp),
	.rst_temp(rst_temp),
	.en_sum(en_sum),
	.rst_sum(rst_sum)
);

//Temporizador
temporizador temp1(
	.en(en_temp),
	.clk(clk),
	.rst(rst_temp),
	.finish(finish)
);

always_comb begin
	case (addr) 
		0: instruction = 10'b 0000001110;
		1: instruction = 10'b 0000000110;
		2: instruction = 10'b 0000111000;   //Inicializa
	/*	3: D = 8'h;
		4: D = 8'h;
		6: D = 8'h;
		7: D = 8'h;
		8: D = 8'h
		9: D = 8'h
		10: D = 8'h
		11: D = 8'h
		12: D = 8'h
		13: D = 8'h
		14: D = 8'h
		*/
		default instruction = 0;
	endcase
end


endmodule 