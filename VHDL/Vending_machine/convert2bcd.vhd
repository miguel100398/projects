library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;
use work.VMC_PKG.all;

entity convert2bcd IS
	PORT(
			binary: IN BINARY_BCD_T;
			bcd: OUT BCD_T
	
	);
end convert2bcd;

architecture behave of convert2bcd is
SIGNAL i_add1, i_add2, i_add3, i_add4, i_add5, i_add6, i_add7: ADD3_T;
SIGNAL o_add1, o_add2, o_add3, o_add4, o_add5, o_add6, o_add7: ADD3_T;
COMPONENT add3 
					PORT(
						A: IN ADD3_T;
						S: OUT ADD3_T
					); END COMPONENT;


begin

add3_1: add3 PORT MAP(
							A => i_add1,
							S => o_add1
							);
add3_2: add3 PORT MAP(
							A => i_add2,
							S => o_add2
							);
add3_3: add3 PORT MAP(
							A => i_add3,
							S => o_add3
							);
add3_4: add3 PORT MAP(
							A => i_add4,
							S => o_add4
							);
add3_5: add3 PORT MAP(
							A => i_add5,
							S => o_add5
							);
add3_6: add3 PORT MAP(
							A => i_add6,
							S => o_add6
							); 
add3_7: add3 PORT MAP(
							A => i_add7,
							S => o_add7
							);

process (binary, o_add1, o_add2, o_add3, o_add4, o_add5, o_add6, o_add7) begin
	--add1
	i_add1(3) <= '0';
	i_add1(2 downto 0) <= binary(7 downto 5);
	--add2
	i_add2(3 downto 1) <= o_add1(2 downto 0);
	i_add2(0) <= binary(4);
	--add3
	i_add3(3 downto 1) <= o_add2(2 downto 0);
	i_add3(0) <= BINARY(3);
	--add4
	i_add4(3) <= '0';
	i_add4(2) <= o_add1(3);
	i_add4(1) <= o_add2(3);
	i_add4(0) <= o_add3(3);
	--add5
	i_add5(3 downto 1) <= o_add3(2 downto 0);
	i_add5(0) <= binary(2);
	--add6
	i_add6(3 downto 1) <= o_add4(2 downto 0);
	i_add6(0) <= o_add5(3);
	--add7
	i_add7(3 downto 1) <= o_add5(2 downto 0);
	i_add7(0) <= binary(1);
	--bcd
	bcd(11 downto 10) <= "00";
	bcd(9) <= o_add4(3);
	bcd(8 downto 5) <= o_add6(3 downto 0);
	bcd(4 downto 1) <= o_add7(3 downto 0);
	bcd(0) <= binary(0);

end process;

end behave;