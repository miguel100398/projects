module memory 
#(
  parameter NUM_REGS = 16, 
  parameter SIZE = 32
)(
  input clk,
  input rst,
  input  [3:0]  wr_addr,
  input         wr_en,
  input en_2,
  input  [31:0] wr_data,
  input  [5:0]  rd_addr,
  output [31:0] rd_data

);
  
    logic [SIZE-1:0] rf [NUM_REGS-1:0];
  
    integer i;
	 always_ff @ (posedge clk) begin
      if (rst)
			for (i = 0; i < NUM_REGS; i = i + 1)
				rf[i] <= 9'h 120;
		else  if (wr_en & en_2)
        rf[wr_addr] <= wr_data;
		
	end
	
    assign rd_data = rf[rd_addr];

endmodule 