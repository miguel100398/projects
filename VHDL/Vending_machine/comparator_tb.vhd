library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;


entity comparator_tb is
end comparator_tb;

Architecture tb of comparator_tb is
SIGNAL price, accum: STD_LOGIC_VECTOR(5 downto 0);
SIGNAL change: STD_LOGIC_VECTOR(5 downto 0);
SIGNAL done: STD_LOGIC;

COMPONENT  comparator is
	PORT(
		price, accum: IN STD_LOGIC_VECTOR(5 downto 0);
		change: OUT STD_LOGIC_VECTOR(5 downto 0);
		done: OUT STD_LOGIC
	); END COMPONENT;
	

begin

dut: comparator PORT MAP(
price => price,
accum => accum,
change => change,
done => done
);


test_process: process begin
	price <= "000000";
	accum <= "000000";
	wait for 5 ns;
	--equals
	price <= "000000";
	accum <= "000000";
	wait for 5 ns;
	price <= "101010";
	accum <= "101010";
	wait for 5 ns;
	price <= "111111";
	accum <= "111111";
	wait for 5 ns;
	--price > accum
	price <= "100101";
	accum <= "011111";
	wait for 5 ns;
	price <= "010101";
	accum <= "010100";
	wait for 5 ns;
	price <= "111110";
	accum <= "111101";
	wait for 5 ns;
	-- price < accum
	price <= "100101";
	accum <= "110101";
	wait for 5 ns;
	price <= "001111";
	accum <= "010101";
	wait for 5 ns;
	price <= "000010";
	accum <= "000111";
	wait for 5 ns;
end process;
	

end tb;