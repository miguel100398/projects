library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;


entity program_unit_tb is
end program_unit_tb;

Architecture tb of program_unit_tb is
constant clk_period: time := 1 ns;
SIGNAL clock, reset, hard_reset, set, enable: STD_LOGIC;
SIGNAL product: STD_LOGIC_VECTOR(1 downto 0);
SIGNAL N, D, Q: STD_LOGIC;
SIGNAL data_mem: STD_LOGIC_VECTOR(5 downto 0);
SIGNAL addr_out: STD_LOGIC_VECTOR(1 downto 0);
SIGNAL done, wen: STD_LOGIC;

COMPONENT  program_unit 
	PORT(
		clock, reset, hard_reset, set, enable: IN STD_LOGIC;
		product: IN STD_LOGIC_VECTOR(1 downto 0);
		N, D, Q: IN STD_LOGIC;
		data_mem: OUT STD_LOGIC_VECTOR(5 downto 0);
		addr_out: OUT STD_LOGIC_VECTOR(1 downto 0);
		done, wen: OUT STD_LOGIC
	); END COMPONENT;
	

begin

dut: program_unit PORT MAP(
clock => clock,
reset => reset,
hard_reset => hard_reset,
set => set,
enable => enable,
product => product,
N => N,
D => D,
Q => Q,
data_mem => data_mem,
addr_out => addr_out,
done => done,
wen => wen
);

clk_process: process begin
	clock <= '0';
	wait for clk_period/2;
	clock <= '1';
	wait for clk_period/2;
end process; 

test_process: process begin
	reset <= '1';
	hard_reset <= '0';
	product <= "00";
	N <= '0';
	D <= '0';
	Q <= '0';
	enable <= '0';
	set <= '0';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	
	--program prod 00
	product <= "00";
	wait until RISING_EDGE(clock);
	enable <= '1';
	wait until RISING_EDGE(clock);
	N <= '1';
	Q <= '1';
	wait until RISING_EDGE(clock);
	N <= '0';
	Q <= '0';
	wait until RISING_EDGE(clock);
	set <= '1';
	wait until (done = '1');
	wait until RISING_EDGE(clock);
	enable <= '0';
	set <= '0';
	wait until RISING_EDGE(clock);
	
	--program prod 10
	product <= "10";
	wait until RISING_EDGE(clock);
	enable <= '1';
	wait until RISING_EDGE(clock);
	N <= '1';
	Q <= '1';
	wait until RISING_EDGE(clock);
	N <= '0';
	Q <= '0';
	wait until RISING_EDGE(clock);
	set <= '1';
	wait until (done = '1');
	wait until RISING_EDGE(clock);
	enable <= '0';
	set <= '0';
	wait until RISING_EDGE(clock);
	
	--HARD_RESET
	enable <= '1';
	hard_reset <= '1';
	wait until (done = '1');
	wait until RISING_EDGE(clock);
	
	--program prod 10
	product <= "10";
	wait until RISING_EDGE(clock);
	enable <= '1';
	wait until RISING_EDGE(clock);
	N <= '1';
	Q <= '1';
	wait until RISING_EDGE(clock);
	N <= '0';
	Q <= '0';
	wait until RISING_EDGE(clock);
	set <= '1';
	wait until (done = '1');
	wait until RISING_EDGE(clock);
	enable <= '0';
	set <= '0';
	wait until RISING_EDGE(clock);
end process;
	

end tb;