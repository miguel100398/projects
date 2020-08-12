module temporizador(
	input en,
	input clk,
	input rst,
	output logic finish
);

	logic [5:0] counter;

	always_ff @(posedge clk) begin
		if (rst ) begin
			counter <= 0;
			finish <= 0;
		end	
		else if (counter == 10) begin
			counter <= 0;
			finish <=1;
		end
		else if (en) begin
			counter <= counter +1;
			finish <= 0;
		end
	end

endmodule 