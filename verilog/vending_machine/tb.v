module tb;

`timescale 1ns/10ps

reg clk, rst;
reg BTN2, BTN5, BTN10, BTNREFUND;
reg SWchild, SWmen, SWwomen;
wire LEDchild, LEDmen, LEDwomen;
wire [3:0] Anode_Activate;
wire [7:0] Segment;

time clktoggle = 5;

vending_machine dut(
.clk(clk),
.rst(rst),
.BTNL(BTN2),
.BTNR(BTN5),
.BTND(BTN10),
.BTNC(BTNREFUND),
.SW15(SWchild),
.SW14(SWmen),
.SW13(SWwomen),
.LED15(LEDchild),
.LED14(LEDmen),
.LED13(LEDwomen),
.Anode_Activate(Anode_Activate),
.Segment(Segment)
);

initial begin
	clk = 0;
end

always begin
	#clktoggle clk = ~clk;
end

initial begin
	rst = 1;
	BTN2 = 0;
	BTN5 = 0;
	BTN10 = 0;
	SWchild = 0;
	SWmen = 0;
	SWwomen = 0;
	#20;
	rst = 0;
	#30;
	//TEST REFUND
	BTN2 = 1;
	#30;
	BTN2 = 0;
	#30;
	BTN2 = 1;
	BTN5 = 1;
	BTN10 = 1;
	#30;
	BTN2 = 0;
	BTN5 = 0;
	BTN10 = 0;
	#30;
	BTNREFUND = 1;
	#20;
	BTNREFUND = 0;
	
	//#50;
	//rst = 1;
	//#20;
	//rst = 0;
	wait (dut.state == 2'b00);
	
	//TEST GET CHILD HAIRCUT
	#50;
	BTN10 = 1;
	#20;
	//try to get invalid hair cut
	BTN10 = 0;
	SWmen = 1;
	#20;
	SWmen = 0;
	#20;
	SWchild = 1;
	#20;
	SWchild = 0;
	wait (dut.state == 2'b00);
	//#50;
	//rst = 1;
	//#20;
	//rst = 0;
	
	//TEST GET MEN HAIRCUT
	#50;
	BTN10 = 1;
	#20;
	BTN10 = 0;
	#20;
	BTN2 = 1;
	#20;
	BTN2 = 0;
	#20;
	SWmen = 1;
	#20;
	SWmen = 0;
	wait (dut.state == 2'b00);
	//#50;
	//rst = 1;
	//#20;
	//rst = 0;
	
	//TEST GET WOMEN HAIRCUT
	#50;
	BTN10 = 1;
	#20;
	BTN10 = 0;
	#20;
	BTN5 = 1;
	#20;
	BTN5 = 0;
	#20;
	SWwomen = 1;
	#20;
	SWwomen = 0;
	wait (dut.state == 2'b00);
	//#50;
	//rst = 1;
	//#20;
	//rst = 0;
	
	//TEST GET CHILD HAIRCUT with extra money
	#50;
	BTN10 = 1;
	#20;
	BTN10 = 0;
	#20;
	BTN5 = 1;
	#20;
	BTN5 = 0;
	#20;
	SWchild = 1;
	#20;
	SWchild = 0;
	wait (dut.state == 2'b00);
	//#50;
	//rst = 1;
	//#20;
	//rst = 0;
	
	
	
	
	$finish;
	
end

endmodule 