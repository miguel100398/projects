library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;


entity add3_tb is
end add3_tb;

Architecture tb of add3_tb is

SIGNAL A: STD_LOGIC_VECTOR(3 downto 0);
SIGNAL S: STD_LOGIC_VECTOR(3 downto 0);

COMPONENT  add3 
	PORT(
		A: IN STD_LOGIC_VECTOR(3 downto 0);
		S: OUT STD_LOGIC_VECTOR(3 downto 0)
	); END COMPONENT;
	

begin

dut: add3 PORT MAP(
A => A,
S => S
);


test_process: process begin
	--Try all combinations
	--Legal inputs
	A <= "0000";
	wait for 5 ns;
	A <= "0001";
	wait for 5 ns;
	A <= "0010";
	wait for 5 ns;
	A <= "0011";
	wait for 5 ns;
	A <= "0100";
	wait for 5 ns;
	A <= "0101";
	wait for 5 ns;
	A <= "0110";
	wait for 5 ns;
	A <= "0111";
	wait for 5 ns;
	A <= "1000";
	wait for 5 ns;
	A <= "1001";
	--Illegal inputs
	wait for 5 ns;
	A <= "1010";
	wait for 5 ns;
	A <= "1011";
	wait for 5 ns;
	A <= "1100";
	wait for 5 ns;
	A <= "1101";
	wait for 5 ns;
	A <= "1110";
	wait for 5 ns;
	A <= "1111";
	wait for 5 ns;
end process;
	

end tb;