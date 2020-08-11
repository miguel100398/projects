library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;
use work.VMC_PKG.all;

entity comparator is
	PORT(
		price, accum: IN CASH_INTERNAL_T;
		change: OUT CASH_INTERNAL_T;
		done: OUT STD_LOGIC
	);
end comparator;

architecture behave of comparator is

begin

	process (price, accum) begin
		if (accum>=price) then
			done <= '1';
			change <= accum-price;
		else
			done <= '0';
			change <= std_logic_vector(to_unsigned(0,change'length));
		end if;
	end process;

end behave;