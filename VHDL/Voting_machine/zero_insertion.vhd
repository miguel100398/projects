library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity zero_insertion is
	Port(
		clk, X, RESET: IN STD_LOGIC;
		Z, S: OUT STD_LOGIC
	);
end zero_insertion;

ARCHITECTURE behave of zero_insertion is
type state is (ZERO, ONE, TWO, THREE, FOUR, FIVE);
signal current_state, next_state: state;
begin

process (clk, RESET) begin
	if (RESET = '1') then
		current_state <= ZERO;
	elsif RISING_EDGE(clk) then 
		current_state <= next_state;
	end if;
end process;

process (RESET, X, current_state) begin
	if (RESET = '1') then
		next_state <= ZERO;
	else
		case current_state is
			when ZERO =>
				if (X='1') then
					next_state <= ONE;
				else
					next_state <= ZERO;
				end if;
			when ONE =>
				if (X='1') then
					next_state <= TWO;
				else
					next_state <= ZERO;
				end if;
			when TWO =>
				if (X='1') then
					next_state <= THREE;
				else
					next_state <= ZERO;
				end if;
			when THREE =>
				if (X='1') then
					next_state <= FOUR;
				else
					next_state <= ZERO;
				end if;
			when FOUR =>
				if (X='1') then
					next_state <= FIVE;
				else
					next_state <= ZERO;
				end if;
			when FIVE =>
				next_state <= ZERO;
		end case;
	end if;
end process;

process(RESET, current_state, X) begin
	if (RESET = '1') then
		Z <= '0';
		S <= '0';
	else
		case current_state is
			when FIVE => Z <= '0'; S<='1';
			when others => Z<= X; S<='0';
		end case;
	end if;
end process;


end behave;