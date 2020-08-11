//Frec = 100 MHz
//Period = 10ns
//Counts for half second = 50Mclks
//Counts for one second= 100Mclks
//Counts for tree_seconds = 300Mclks
//Counts for five_seconds = 500Mclks

module time_generator(
input clk, rst, en, set,
output wire half_second, second, three_seconds, five_seconds
);

wire count_done1, count_done2, count_done3, count_done4, count_done5,
	  count_done6, count_done7, count_done8;

wire [3:0] count_8, count_9;
wire rst2;
reg rst_f;

assign rst2 = rst_f ||rst;

/*
assign half_second = (count_8==5);
assign second = count_done8;
assign three_seconds = (count_9==3);
assign five_seconds = (count_9==5);
*/
assign half_second = (count_done1);
assign second = count_done2;
assign three_seconds = count_done3;			//This signals are used in the tb to avoid
														//extreme waiting times in simulation
assign five_seconds = count_done4;


//Count_done = 10clks
BCD_counter counter1(
.in_count(clk),
.rst(rst),
.set(set),
.en(en),
.count_done(count_done1)
);

//Count_done = 100clks
BCD_counter counter2(
.in_count(count_done1),
.rst(rst2),
.set(set),
.en(en),
.count_done(count_done2)
);

//Count_done = 1000clks
BCD_counter counter3(
.in_count(count_done2),
.rst(rst2),
.set(set),
.en(en),
.count_done(count_done3)
);

//Count_done = 10000clks
BCD_counter counter4(
.in_count(count_done3),
.rst(rst2),
.set(set),
.en(en),
.count_done(count_done4)
);

//Count_done = 100000clks
BCD_counter counter5(
.in_count(count_done4),
.rst(rst2),
.set(set),
.en(en),
.count_done(count_done5)
);

//Count_done = 1000000clks
BCD_counter counter6(
.in_count(count_done5),
.rst(rst2),
.set(set),
.en(en),
.count_done(count_done6)
);

//Count_done = 10000000clks = 0.1s
BCD_counter counter7(
.in_count(count_done6),
.rst(rst2),
.set(set),
.en(en),
.count_done(count_done7)
);

//Count_done = 100000000clks == 1s
BCD_counter counter8(
.in_count(count_done7),
.rst(rst2),
.set(set),
.en(en),
.count_done(count_done8),
.count_out(count_8)
);

//Count_done = 1000000000clks == 10s
BCD_counter counter9(
.in_count(count_done8),
.rst(rst2),
.set(set),
.en(en),
.count_out(count_9)
);

always @(posedge clk or posedge rst) begin
	if (rst) begin
		rst_f <= 1;
	end else begin
		if (set) begin
			rst_f <= 1;
		end else begin
			rst_f <= 0;
		end
	end
end

endmodule 