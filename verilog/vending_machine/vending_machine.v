module vending_machine(
input clk, rst,
input BTNL, BTNR, BTND, BTNC,
input SW15, SW14, SW13,
output wire LED15, LED14, LED13,
output wire [3:0] Anode_Activate,
output wire [7:0] Segment
);
//time generator
wire half_second, second, three_seconds, five_seconds;
wire rst_time_gen, en_time_gen, set_time_gen;
//blink generator
wire blink;
wire rst_blink;
//cash accumulator
wire[6:0] cash;
wire rst_cash_accum, en_cash_accum;
//ticket_enable
wire child_en, men_en, women_en;
//refund
wire rst_refund, block_cash, en_refund;
wire [6:0] refund_cash;
//FSM
wire [1:0] state;
wire refund;
wire coinButton, dispatch_ticket;


assign coinButton = BTNL||BTNR||BTND;
assign dispatch_ticket = (child_en&&SW15)||(men_en&&SW14)||(women_en&&SW13);

FSM FSM1(
.clk(clk),
.rst(rst),
.coinButton(coinButton),
.withdrawbutton(BTNC),
.dispatch_ticket(dispatch_ticket),
.five_seconds(five_seconds),
.three_seconds(three_seconds),
.rst_time_gen(rst_time_gen),
.en_time_gen(en_time_gen),
.set_time_gen(set_time_gen),
.rst_blink(rst_blink),
.rst_cash_accum(rst_cash_accum),
.en_cash_accum(en_cash_accum),
.rst_refund(rst_refund),
.en_refund(en_refund),
.block_cash(block_cash),
.state_output(state),
.refund(refund)
);

time_generator tm_gen(
.clk(clk),
.rst(rst_time_gen),
.en(en_time_gen),
.set(set_time_gen),
.half_second(half_second),
.second(second),
.three_seconds(three_seconds),
.five_seconds(five_seconds)
);

//blinks 1 seconds
blink_generator blink_gen(
.count_in(half_second),
.rst(rst_blink),
.blink(blink)
);

cash_acumulator cash_accum(
.clk(clk),
.rst(rst_cash_accum),
.en(en_cash_accum),
.note2(BTNL),
.note5(BTNR),
.note10(BTND),
.cash(cash)
);

ticket_enable ticket_en(
.cash(cash),
.child(child_en),
.men(men_en),
.women(women_en)
);

sevenSegmentDecoder sevenDecoder(
.clk(clk),
.rst(rst),
.cash(refund_cash),
.refund(refund),
.Segment(Segment),
.Anode_Activate(Anode_Activate)
);

refund refund1(
.clk(clk),
.rst(rst_refund),
.cash(cash),
.en(en_refund),
.block_cash(block_cash),
.child(SW15),
.men(SW14),
.women(SW13),
.refund_cash(refund_cash)
);

LEDcontroller LED(
.clk(clk),
.blink(blink),
.state(state),
.sw_child(SW15),
.sw_men(SW14),
.sw_women(SW13),
.en_child(child_en),
.en_men(men_en),
.en_women(women_en),
.ledchild(LED15),
.ledmen(LED14),
.ledwomen(LED13)
);


endmodule 