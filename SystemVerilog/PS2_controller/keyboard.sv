module keyboard
	(
		input clk, reset, 
		input  ps2d, ps2c,   	// ps2 data and clock inputs, receive enable input
		output logic done,       	 	// Flag de done
		output logic [9:0] data      	// Los datos recibidos 
	);
	
	
	// FSMD state declaration and registers
	enum 	{idle, read, DLY} state, next_state;

	// Cables internos
	logic [3:0] count;
	logic ps2cLow;
	logic en_esperados;
	logic rst_espera;
	logic [31:0]espera;
	logic rst_count;
	logic en_count;
	logic en_data;
	logic rst_data;
	//cambio de estado
	always_ff @(posedge clk) begin
		if(reset) begin
			state <= idle;
		end else begin
			state <=  next_state;
		end
	end 
	posEdgeGenerator inst_posEdgeGenerator (.sig_a(~ps2c), .rst(~reset), .clk(clk), .sig_a_risedge(ps2cLow));
	
	//Asignación next_state
	always_comb begin
		if (reset) begin
			next_state <= idle;
		end
		else begin
			case (state)
				idle: begin
					if (ps2cLow)
						next_state <= read;
					else
						next_state <= idle;
				end
				read: begin
					if (count == 10)
						next_state <= DLY;
					else
						next_state <= read;
				end
				DLY: begin
					if (espera >50000000)
						next_state <= idle;
					else
						next_state <= DLY;
				end
				default:
					next_state <= idle;
			endcase
		end
	end
	
	//Asignación outputs
	
	always_comb begin
		if (reset) begin
			rst_data <= 1;
			en_data <= 0;
			en_esperados <= 0;
			rst_espera <= 1;
			en_count <= 0;
			rst_count <= 1;
		end
		else begin
			case (state)
				idle: begin
					rst_data <= 1;
					en_data <= 0;
					en_esperados <= 0;
					rst_espera <= 1;
					en_count <= 0;
					rst_count <= 1;
				end
				read: begin
					en_esperados <= 0;
					rst_espera <= 1;
					rst_count <= 0;
					rst_data <= 0;
					en_data <= 1;
					if (ps2cLow) begin
						en_count <= 1;
					end
					else begin
						en_count <= 0;
					end
				end
				DLY: begin
					en_count <= 0;
					rst_count <= 1;
					rst_data <= 0;
					en_data <= 0;
					en_esperados <= 1;
					rst_espera <= 0;
				end
				default: begin
					en_count <= 0;
					rst_count <= 1;
					rst_data <= 1;
					en_data <= 0;
					en_esperados <= 0;
					rst_espera <= 1;
				end
			endcase
		end
	end
	
	
	always_ff @ (posedge clk)  begin
		if (count == 10 ) 
			done = 1;
		else 
			done = 0;
	end
	////////////////////////////////espera///////////////////////
	always_ff @ (posedge clk) begin
		if (rst_espera | reset)
			espera <= 0;
		else if (en_esperados)
			espera <= espera +1;
		else 
			espera <= espera;
	end
	////////////////////Counter/////////////////////////////
	always_ff @ (posedge clk) begin
		if (rst_count) begin
			count <= 0;
		end
		else if (count == 10) begin
			count <= 0;
		end
		else if (en_count) begin
			count <= count + 1;
		end
		else 
			count <= count;
	end
		
	//////////////////////data///////////////////////////////////
	always_ff @ (posedge clk) begin
		if (rst_data)
			data <= 0;
		else if (en_data)
			data[count]<= ps2d;
		else
			data <= data;
	end
	
endmodule // ps2_rx