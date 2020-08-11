
library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;

use work.VMC_PKG.all;

entity program_unit is 
	PORT(
		clock, reset, hard_reset, set, enable: IN STD_LOGIC;
		product: IN PROD_T;
		N, D, Q: IN STD_LOGIC;
		data_mem: OUT CASH_INTERNAL_T;
		addr_out: OUT MEM_ADDR_T;
		done, wen: OUT STD_LOGIC
	);
end program_unit;

architecture behave of program_unit is
SIGNAL en_accumulator: STD_LOGIC;
SIGNAL current_state, next_state: PROGRAM_UNIT_STATE;
SIGNAL mem_counter_done, mem_counter_enable, mem_counter_reset: STD_LOGIC;
SIGNAL mem_counter: MEM_COUNTER_T;
SIGNAL counter_errase_mem: PROD_T;
SIGNAL hard_reset_done, hard_reset_en, counter_errase_mem_rst:	STD_LOGIC;
SIGNAL mem_counter_set: STD_LOGIC;
COMPONENT accumulator PORT(
									clock, reset, en, N, D, Q: IN STD_LOGIC;
									accum_out: OUT CASH_INTERNAL_T); END COMPONENT;

begin

accum : accumulator PORT MAP(
										clock => clock,
										reset => reset,
										en => en_accumulator, 
										N => N,
										D => D,
										Q => Q,
										accum_out => data_mem
										);

--STATE MACHINE
process (clock, reset) begin
	if (reset = '1') then
		current_state <= IDLE;
	elsif RISING_EDGE(clock) then
		if (hard_reset = '1') then
			current_state <= HARDRESET;
		else
			current_state <= next_state;
		end if;
	end if;
end process;

process (reset, current_state, enable, set, mem_counter_done, hard_reset_done) begin
	if (reset='1') then
		next_state <= IDLE;
	else
		case current_state is
			when IDLE =>
				if (enable = '1') then
					next_state <= ADDING;
				else
					next_state <= IDLE;
				end if;
			when ADDING =>
				if (set = '1') then
					next_state <= MEM_WRITING;
				else
					next_state <= ADDING;
				end if;
			when MEM_WRITING =>
				if (mem_counter_done = '1') then
					next_state <= IDLE;
				else
					next_state <= MEM_WRITING;
				end if;
			when HARDRESET =>
				if (hard_reset_done = '1') then
					next_state <= IDLE;
				else
					next_state <= HARDRESET;
				end if;
			when others =>
				next_state <= IDLE;
		end case;
	end if;
end process;

process(reset, current_state, mem_counter_done, hard_reset_done) begin
	if(reset='1') then
		en_accumulator <= '0';
		mem_counter_reset <= '1';
		done	<= '0';
		wen	<= '0';
		mem_counter_enable <= '0';
		mem_counter_set <= '0';
		hard_reset_en <= '0';
		counter_errase_mem_rst <= '1';
	else 
		case current_state is
			when IDLE =>
				en_accumulator <= '0';
				mem_counter_reset <= '1';
				done	<= '0';
				wen	<= '0';
				mem_counter_enable <= '0';
				mem_counter_set <= '0';
				hard_reset_en <= '0';
				counter_errase_mem_rst <= '1';
			when ADDING =>
				en_accumulator <= '1';
				mem_counter_reset <= '1';
				done	<= '0';
				wen	<= '0';
				mem_counter_enable <= '0';
				mem_counter_set <= '0';
				hard_reset_en <= '0';
				counter_errase_mem_rst <= '1';
			when MEM_WRITING =>
				en_accumulator <= '1';
				mem_counter_reset <= '0';
				mem_counter_enable <= '1';
				mem_counter_set <= '0';
				hard_reset_en <= '0';
				counter_errase_mem_rst <= '1';
				if (mem_counter_done = '1') then
					done	<= '1';
					wen	<= '0';
				else
					done <= '0';
					wen <= '1';
				end if;
			when HARDRESET =>
				en_accumulator <= '0';
				mem_counter_reset <= '0';
				mem_counter_enable <= '1';
				hard_reset_en <= '1';
				counter_errase_mem_rst <= '0';
				if (mem_counter_done = '1') then
					mem_counter_set <= '1';
				else
					mem_counter_set <= '0';
				end if;
				if (hard_reset_done = '1') then
					wen <= '0';
					done <= '1';
				else
					wen <= '1';
					done <= '0';
				end if;
			when others =>
				mem_counter_set <= '0';
				en_accumulator <= '0';
				mem_counter_reset <= '1';
				done	<= '0';
				wen	<= '0';
				mem_counter_enable <= '0';
				hard_reset_en <= '0';
				counter_errase_mem_rst <= '1';
		end case;
	end if;
end process;

--Counter to store data in mem
process (clock, mem_counter_reset, mem_counter_set) begin
	if (mem_counter_reset = '1') then
		mem_counter <= std_logic_vector(to_unsigned(0,mem_counter'length));
	elsif RISING_EDGE(clock) then
		if (mem_counter_set = '1') then
			mem_counter <= std_logic_vector(to_unsigned(0,mem_counter'length));
		elsif (mem_counter_enable = '1') then
			mem_counter <= mem_counter+1; 
		end if;
	end if;
end process;

--counter to clear memory
process (reset, mem_counter_done, counter_errase_mem_rst, clock) begin
	if (counter_errase_mem_rst ='1') then
		counter_errase_mem <= std_logic_vector(to_unsigned(0,counter_errase_mem'length));
	elsif RISING_EDGE(clock) then
		if (hard_reset_en = '1') then
			if (mem_counter_done = '1') then
				counter_errase_mem <= counter_errase_mem+1;
			end if;
		end if;
	end if;
end process;

process (counter_errase_mem, mem_counter_done) begin
	if(counter_errase_mem = std_logic_vector(to_unsigned(num_products-1,mem_counter'length)) and (mem_counter_done='1')) then
		hard_reset_done <= '1';
	else
		hard_reset_done <= '0';
	end if;
end process;

process (mem_counter) begin
	if (mem_counter = std_logic_vector(to_unsigned(wait_cycles_mem,mem_counter'length))) then
		mem_counter_done <= '1';
	else
		mem_counter_done <= '0';
	end if;
end process;

--Map addr_out to prod or HARD RESET
process (product, current_state, counter_errase_mem) begin
	if (current_state = HARDRESET) then
		addr_out <= counter_errase_mem;
	else
		addr_out <= product;
	end if;
end process;

end behave;