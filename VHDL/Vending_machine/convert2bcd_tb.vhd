library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;


entity convert2bcd_tb is
end convert2bcd_tb;

Architecture tb of convert2bcd_tb is

SIGNAL binary: STD_LOGIC_VECTOR(7 downto 0);
SIGNAL bcd: STD_LOGIC_VECTOR(11 downto 0);

COMPONENT  convert2bcd IS
	PORT(
			binary: IN STD_LOGIC_VECTOR(7 downto 0);
			bcd: OUT STD_LOGIC_VECTOR(11 downto 0)
	
	); END COMPONENT;
	

begin

dut: convert2bcd PORT MAP(
binary => binary,
bcd => bcd
);


test_process: process begin
	--COnvert different numbers
	binary <= "00000000";
	wait for 5 ns;
	binary <= "10010010";
	wait for 5 ns;
	binary <= "01010010";
	wait for 5 ns;
	binary <= "11001100";
	wait for 5 ns;
	binary <= "11000101";
	wait for 5 ns;
	binary <= "00101011";
	wait for 5 ns;
	binary <= "00000001";
	wait for 5 ns;
	binary <= "10100101";
	wait for 5 ns;
	binary <= "10000010";
	wait for 5 ns;
	binary <= "11111111";
	wait for 5 ns;
end process;
	

end tb;