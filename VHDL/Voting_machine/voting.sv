module ej_1(
input logic clk, reset,
input logic [3:0] vote_inp,
output logic warn,
output logic [4:0] num_1, num_2, num_3, num_abs,
output logic [2:0] decision
);

logic [4:0] count_1, count_2, count_3, count_abs;
logic invalid_vote;

assign invalid_vote = (vote_inp[3]+vote_inp[2]+vote_inp[1]+vote_inp[0]!=1);
assign num_1 = count_1;
assign num_2 = count_2;
assign num_3 = count_3;
assign num_abs = count_abs;

always_ff @(posedge clk or posedge reset) begin
	if (reset) begin
		count_1 <= 0;
		count_2 <= 0;
		count_3 <= 0;
		count_abs <= 0;
		warn <= 0;
	end else begin
		if (invalid_vote) begin
			warn<=1;
			count_1 <= count_1;
			count_2 <= count_2;
			count_3 <= count_3; 
			count_abs <= count_abs;
		end else begin
			warn <= 0;
			count_1 <= count_1+vote_inp[3];
			count_2 <= count_2+vote_inp[2];
			count_3 <= count_3+vote_inp[1];
			count_abs <= count_abs+vote_inp[0];
		end
	end
end

always_comb begin
	if (reset) begin
		decision = 0;
	end else begin
		if ((count_1 == count_2) &&(count_2 == count_3) &&(count_1==count_3)) begin
			decision = 3'b111;
		end else if(count_1 == count_2) begin
			if (count_1>count_3) begin
				decision = 3'b110;
			end else begin
				decision = 3'b001;
			end
		end else if (count_1 == count_3) begin
			if (count_1>count_2) begin
				decision = 3'b101;
			end else begin
				decision = 3'b010;
			end
		end else if (count_2 == count_3) begin
			if (count_2>count_1) begin
				decision = 3'b011;
			end else begin
				decision = 3'b100;
			end
		end else begin
			if (count_1>count_2) begin
				if (count_1>count_3) begin
					decision = 3'b100;
				end else begin
					decision = 3'b001;
				end
			end else begin
				if (count_2>count_3) begin
					decision = 3'b010;
				end else begin
					decision = 3'b001;
				end
			end
		end	
	end
end

endmodule 