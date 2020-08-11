library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;


use work.VMC_PKG.all;

entity display_mode is
	PORT(
		reset, en: IN STD_LOGIC;
		clk: IN STD_LOGIC;
		hard_reset: IN STD_LOGIC;
		product: IN PROD_T; 
		addr_mem: OUT MEM_ADDR_T;
		mem_ren: OUT STD_LOGIC;
		display_done: OUT STD_LOGIC;
		deassert_display: OUT STD_LOGIC
	);
end display_mode;

architecture behave of display_mode is
SIGNAL mem_counter: MEM_COUNTER_T;
SIGNAL mem_counter_set: STD_LOGIC;
SIGNAL last_prod: PROD_T;
begin

	--MAP PRODUCT TO ADDR_MEM
	process (product) begin
		addr_mem <= product;
	end process;
	
	process (reset, en) begin
		if (reset = '1') then
			mem_ren <= '0';
		elsif(en = '1') then
			mem_ren <= '1';
		else
			mem_ren <= '0';
		end if;
	end process;
	
	process (clk, reset, mem_counter_set, hard_reset) begin
		if ((reset = '1') or (hard_reset = '1')) then
			mem_counter <= std_logic_vector(to_unsigned(0,mem_counter'length));
		elsif (mem_counter_set = '1') then
			mem_counter <= std_logic_vector(to_unsigned(0,mem_counter'length));
		elsif RISING_EDGE(clk) then	
			if (en ='1') then
				if (mem_counter < std_logic_vector(to_unsigned(wait_cycles_mem,mem_counter'length))) then
					mem_counter <= mem_counter + 1;
				end if;
			end if;
		end if;
	end process;
	
	process(mem_counter, mem_counter_set) begin
		if (mem_counter_set ='1') then
			display_done <= '0';
		else
			if (mem_counter = std_logic_vector(to_unsigned(wait_cycles_mem,mem_counter'length))) then
				display_done <= '1';
			else
				display_done <= '0';
			end if;
		end if;
	end process;
	
	process(reset, clk) begin
		if (reset ='1') then
			last_prod <= "00";
		elsif RISING_EDGE(clk) then
			last_prod <= product;
		end if;
	end process;
	
	process(product, last_prod) begin
		if (last_prod = product) then
			mem_counter_set <= '0';
		else
			mem_counter_set <= '1';
		end if;
	end process;
	
	process (mem_counter_set) begin
		deassert_display <= mem_counter_set;
	end process;
	

end behave;