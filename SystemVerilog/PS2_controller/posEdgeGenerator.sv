module posEdgeGenerator(input logic sig_a, rst,clk,output logic sig_a_risedge);

  logic sig_a_d1;

  always @(posedge clk or negedge rst) begin
    if(!rst)
      sig_a_d1<=1'b0;
    else
      sig_a_d1<=sig_a;
  end

  always_ff @ (posedge clk)  sig_a_risedge=sig_a & !sig_a_d1;

endmodule