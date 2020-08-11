library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;


use work.VMC_PKG.all;

entity accumulator is
	PORT(
		clock, reset, en, N, D, Q: IN STD_LOGIC;
		accum_out: OUT CASH_INTERNAL_T
	);
end accumulator;

architecture behave of accumulator is
SIGNAL accum: CASH_INTERNAL_T;
SIGNAL sum_N: STD_LOGIC;
SIGNAL sum_D: STD_LOGIC_VECTOR(1 downto 0);
SIGNAL sum_Q: STD_LOGIC_VECTOR(2 downto 0);
begin

	process (clock, reset) begin
		if (reset = '1') then
			accum <= std_logic_vector(to_unsigned(0,accum'length));
		elsif RISING_EDGE(clock) then
			if (en = '1') then
				accum <= accum + sum_N + sum_D + sum_Q;
			else 
				accum <= std_logic_vector(to_unsigned(0,accum'length));
			end if;
		end if;
	end process;
	
	process (N, D, Q) begin
		if (N='1') then
			sum_N <= '1';
		else
			sum_N <= '0';
		end if;
		if (D='1') then
			sum_D <= "10";
		else
			sum_D <= "00";
		end if;
		if (Q='1') then
			sum_Q <= "101";
		else
			sum_Q <= "000";
		end if;
	end process; 
	
	--assign accum_out to accum
	process (accum) begin
		accum_out <= accum;
	end process;

end behave;