library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;


entity vending_unit_tb is
end vending_unit_tb;

Architecture tb of vending_unit_tb is
constant clk_period: time := 1 ns;
SIGNAL clock, reset, enable, done: STD_LOGIC;
SIGNAL price_in: STD_LOGIC_VECTOR(5 downto 0);
SIGNAL N, D, Q: STD_LOGIC;
SIGNAL change, insert_out: STD_LOGIC_VECTOR(11 downto 0);

COMPONENT  vending_unit 
	PORT(
		clock, reset, enable: IN STD_LOGIC;
		price_in: IN STD_LOGIC_VECTOR(5 downto 0);
		N, D, Q: IN STD_LOGIC;
		change, insert_out: OUT STD_LOGIC_VECTOR(11 downto 0);
		done: OUT STD_LOGIC
	);
END COMPONENT;
	

begin

dut: vending_unit PORT MAP(
clock => clock,
reset => reset,
enable => enable,
price_in => price_in,
N => N,
D => D,
Q => Q,
change => change,
insert_out => insert_out,
done => done

);

clk_process: process begin
	clock <= '0';
	wait for clk_period/2;
	clock <= '1';
	wait for clk_period/2;
end process; 

test_process: process begin
	reset <= '1';
	enable <= '0';
	price_in <= "000100"; --0.20
	N <= '0';
	D <= '0';
	Q <= '0';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	enable <= '1';
	wait until RISING_EDGE(clock);
	--Pay with exact cash
	D <= '1';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	D <= '0';
	wait until (done = '1');
	wait until RISING_EDGE(clock);
	reset <= '1';
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	--Pay with extra cash
	Q <= '1';
	wait until RISING_EDGE(clock);
	Q<= '0';
	wait until (done = '1');
	wait until RISING_EDGE(clock);
	reset <= '1';
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	--Pay with extra cash and extra cycles
	Q <= '1';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	Q <= '0';
	wait until (done = '1');
	wait until RISING_EDGE(clock);
	reset <= '1';
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	--Interrupt payment
	Q <= '1';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	reset <= '1';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	
	
	
end process;
	

end tb;