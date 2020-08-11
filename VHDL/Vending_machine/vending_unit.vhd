library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;
use work.VMC_PKG.all;

entity vending_unit is
	PORT(
		clock, reset, enable: IN STD_LOGIC;
		price_in: IN CASH_INTERNAL_T;
		N, D, Q: IN STD_LOGIC;
		change, insert_out: OUT BCD_T;
		done: OUT STD_LOGIC
	);
end vending_unit;

architecture behave of vending_unit is
SIGNAL accum_out: CASH_INTERNAL_T;
SIGNAL compare_done: STD_LOGIC;
SIGNAL compare_change: CASH_INTERNAL_T;
SIGNAL change_BCD, insert_out_BCD: CASH_T;
SIGNAL compare_change_shifted, accum_out_shifted: CASH_T;
COMPONENT accumulator 
							PORT(
									clock, reset, en, N, D, Q: IN STD_LOGIC;
									accum_out: OUT CASH_INTERNAL_T
								); END COMPONENT;
COMPONENT convert2bcd PORT(
									binary: IN BINARY_BCD_T;
									bcd: OUT BCD_T
									); END COMPONENT;
COMPONENT comparator PORT(
									price, accum: IN CASH_INTERNAL_T;
									change: OUT CASH_INTERNAL_T;
									done: OUT STD_LOGIC
									);  END COMPONENT;


begin

accumulator1: accumulator PORT MAP(
												clock => clock,
												reset => reset, 
												en => enable, 
												N => N,
												D => D,
												Q => Q,
												accum_out => accum_out
											);

comparator1: comparator PORT MAP(
											price => price_in,
											accum => accum_out,
											change => compare_change,
											done => compare_done
											);

BCD_change: convert2bcd PORT MAP(
											binary => change_BCD,
											bcd => change
											);
											
BCD_insert_out: convert2bcd PORT MAP(
											binary => insert_out_BCD,
											bcd => insert_out
											);

											
process (compare_change, accum_out, reset, enable, compare_change_shifted, accum_out_shifted) begin
	if (reset ='1' or enable = '0') then
		change_BCD <= std_logic_vector(to_unsigned(0,change_BCD'length));
		insert_out_BCD <= std_logic_vector(to_unsigned(0,insert_out_BCD'length));
	else 
		change_BCD <= compare_change + compare_change_shifted;					--Multiply by 5
		insert_out_BCD <= accum_out + accum_out_shifted;
	end if;
end process;

process (clock, reset) begin
	if (reset ='1') then
		done <= '0';
	elsif RISING_EDGE(clock) then
		if (enable = '1') then
			if ((compare_done='1') and (N='0') and (D='0') and (Q='0')) then
				done <= '1';
			else
				done <= '0';
			end if;
		else
			done <= '0';
		end if;
	end if;
end process;

--Shift manually
process (compare_change, accum_out) begin
	compare_change_shifted(7 downto 2) <= compare_change(5 downto 0);
	compare_change_shifted(1 downto 0) <= "00";
	accum_out_shifted(7 downto 2) <= accum_out(5 downto 0);
	accum_out_shifted(1 downto 0) <= "00";
end process; 

  
end behave;