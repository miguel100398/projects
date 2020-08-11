library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;


entity display_mode_tb is
end display_mode_tb;

Architecture tb of display_mode_tb is
constant clk_period: time := 1 ns;
SIGNAL clk, reset, en: STD_LOGIC;
SIGNAL hard_reset: STD_LOGIC;
SIGNAL product: STD_LOGIC_VECTOR(1 downto 0); 
SIGNAL addr_mem: STD_LOGIC_VECTOR(1 downto 0);
SIGNAL mem_ren: STD_LOGIC;
SIGNAL display_done: STD_LOGIC;
SIGNAL deassert_display: STD_LOGIC;

COMPONENT  display_mode 
	PORT(
		reset, en: IN STD_LOGIC;
		clk: IN STD_LOGIC;
		hard_reset: IN STD_LOGIC;
		product: IN STD_LOGIC_VECTOR(1 downto 0); 
		addr_mem: OUT STD_LOGIC_VECTOR(1 downto 0);
		mem_ren: OUT STD_LOGIC;
		display_done: OUT STD_LOGIC;
		deassert_display: OUT STD_LOGIC
	);
END COMPONENT;
	

begin

dut: display_mode PORT MAP(
clk => clk,
reset => reset,
en => en,
hard_reset => hard_reset,
product => product,
addr_mem => addr_mem,
mem_ren => mem_ren,
display_done => display_done,
deassert_display => deassert_display

);

clk_process: process begin
	clk <= '0';
	wait for clk_period/2;
	clk <= '1';
	wait for clk_period/2;
end process; 

test_process: process begin
	reset <= '1';
	en <= '0';
	hard_reset <= '0';
	product <= "00";
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	reset <= '0';
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	
	--product without enable, dont assert dispay_done
	product <= "01";
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	product <= "10";
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	--assert enable
	en <= '1';
	wait until (display_done = '1');
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	--change product without de assert enable, deassert display should be asserted
	product <= "00";
	wait until (display_done = '1');
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	--rst to disable module
	reset <= '1';
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	reset <= '0';
	wait until (display_done = '1');
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	
	--hard reset
	hard_reset <= '1';
	wait until RISING_EDGE(clk);
	wait until RISING_EDGE(clk);
	hard_reset <= '0';
	
	
end process;
	

end tb;