library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;
use work.VMC_PKG.all;

entity add3 is
	PORT(
		A: IN ADD3_T;
		S: OUT ADD3_T
	);
end add3;

architecture behave of add3 is
begin

	process (A) begin
		case A is
			when "0000" => S <= "0000";
			when "0001" => S <= "0001";
			when "0010" => S <= "0010";
			when "0011" => S <= "0011";
			when "0100" => S <= "0100";
			when "0101" => S <= "1000";
			when "0110" => S <= "1001";
			when "0111" => S <= "1010";
			when "1000" => S <= "1011";
			when "1001" => S <= "1100";
			when others => S <= "XXXX";
		end case;
	end process;

end behave;