module booth #(
parameter SIZE = 64)(
input logic [(SIZE/2)-1:0] i_A, i_B,
input logic i_en, i_clk, i_rst_n,
output logic [SIZE-1:0] o_result,
output logic o_done
);
logic en, unf;
logic [(SIZE/2)-1:0] d_A, q_A;
logic [(SIZE/2)-1:0] d_M, q_M;
logic [SIZE/2:0] d_Q, q_Q;

assign o_result[SIZE-1:SIZE/2] = q_A;
assign o_result[(SIZE/2)-1:0] = q_Q[(SIZE/2):1];


//Registros

register #(.SIZE ((SIZE/2)) )A(  //Result Register
.i_clk(i_clk),
.i_rst_n(1'b1),
.i_en(en),
.i_d(d_A),
.o_q(q_A)
);

register #(.SIZE (SIZE/2)) M(  //Multiplicand  
.i_clk(i_clk),
.i_rst_n(1'b1),
.i_en(en),
.i_d(d_M),
.o_q(q_M)
);

register #(.SIZE ((SIZE/2)+1) )Q(  //Multiplier 
.i_clk(i_clk),
.i_rst_n(1'b1),
.i_en(en),
.i_d(d_Q),
.o_q(q_Q)
);

counter #(.SIZE ($clog2(SIZE/2))) counter1(
.i_en(en),
.i_clk(i_clk),
.i_rst_n(i_rst_n),
.i_down(1'b1),
.o_unf(unf)
);

//Control
control_booth #(.SIZE (SIZE)) ctrl(
.i_rst_n(i_rst_n),
.i_en(i_en),
.i_unf(unf),
.i_Q(q_Q),
.i_M(q_M),
.i_result(q_A),
.i_A(i_A),
.i_B(i_B),
.o_result(d_A),
.o_M(d_M),
.o_Q(d_Q),
.o_en(en),
.o_done(o_done)
);


endmodule