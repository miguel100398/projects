library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;


use work.VMC_PKG.all;

entity tb is
end tb;

architecture test of tb is
SIGNAL clock: STD_LOGIC := '0';
SIGNAL reset, hard_reset: STD_LOGIC := '0';
SIGNAL start, set: STD_LOGIC := '0';
SIGNAL N, D, Q: STD_LOGIC := '0';
SIGNAL funct: VMC_MODE := PROGRAM;
SIGNAL prod:  PROD_T := "00";
SIGNAL change0, change1, change2: DISPLAY_CASH_T;
SIGNAL runTotal0, runTotal1, runTotal2: DISPLAY_CASH_T;
SIGNAL total0, total1, total2: DISPLAY_CASH_T;
SIGNAL finished: STD_LOGIC;

constant clk_period: time := 1 ns;
COMPONENT VMC PORT(
						clock, reset, hard_reset: IN STD_LOGIC;
						start, set: IN STD_LOGIC;
						N, D, Q: IN STD_LOGIC;
						funct: IN VMC_MODE;
						prod: IN PROD_T;
						change0, change1, change2: OUT DISPLAY_CASH_T;
						runTotal0, runTotal1, runTotal2: OUT DISPLAY_CASH_T;
						total0, total1, total2: OUT DISPLAY_CASH_T;
						finished: OUT STD_LOGIC
						); END COMPONENT;
begin

dut: VMC PORT MAP(
						clock => clock,
						reset => reset,
						hard_reset => hard_reset,
						start => start,
						set => set,
						N => N,
						D => D,
						Q => Q,
						funct => funct,
						prod => prod,
						change0 => change0,
						change1 => change1,
						change2 => change2,
						runTotal0 => runTotal0,
						runTotal1 => runTotal1,
						runTotal2 => runTotal2,
						total0 => total0,
						total1 => total1,
						total2 => total2,
						finished => finished
						);

clk_process: process begin
	clock <= '0';
	wait for clk_period/2;
	clock <= '1';
	wait for clk_period/2;
end process; 

test_process: process begin
	reset <= '1';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	
	--LOAD MEMORY
	funct <= PROGRAM;
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait until RISING_EDGE(clock);
	--set prod 0 = 1.55
	prod <= "00";
	Q <= '1';
	D <= '1';
	N <= '1';	--0.40
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --0.80
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --1.20
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '0'; --1.55
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '0';
	N <= '0';
	set <= '1';
	wait until RISING_EDGE(clock);
	set <= '0';
	wait until (finished = '1');		--wait write mem
	wait until RISING_EDGE(clock);
	--set prod1 = 2.55;
	prod <= "01";
	Q <= '1';
	D <= '1';
	N <= '1'; --0.40
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --0.80
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --1.20
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --1.60
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --2.00
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --2.40
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '1';
	N <= '1'; --2.55
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '0';
	N <= '0';
	set <= '1';
	wait until RISING_EDGE(clock);
	set <= '0';
	wait until (finished = '1');		--wait write mem
	wait until RISING_EDGE(clock);
	--set prod2 = 0.40
	prod <= "10";
	Q <= '1';
	D <= '1';
	N <= '1';
	wait until RISING_EDGE(clock);
	wait until RISING_EDGE(clock);
	reset <= '1';		--assert reset to cancel program;
	wait until RISING_EDGE(clock);
	reset <= '0';		--deassert reset
	Q <= '0';
	D <= '0';
	N <= '0';
	wait until RISING_EDGE(clock);
	--Restart program
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait until RISING_EDGE(clock);
	--now set prod2=0.30
	Q <= '1';
	D <= '0';
	N <= '1';
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '0';
	N <= '0';
	set <= '1';
	wait until RISING_EDGE(clock);
	set <= '0';
	wait until (finished = '1');		--wait write mem
	wait until RISING_EDGE(clock);
	--set prod3 = 0.5
	prod <= "11";
	Q <= '0';
	D <= '0';
	N <= '1';
	wait until RISING_EDGE(clock);
	set <= '1';
	Q <= '0';
	D <= '0';
	N <= '0';
	wait until RISING_EDGE(clock);
	set <= '0';
	wait until (finished = '1');		--wait write mem
	wait until RISING_EDGE(clock);
	--reprogram 00 to 0.25
	prod <= "00";
	Q <= '1';
	D <= '0';
	N <= '0';
	wait until RISING_EDGE(clock);
	set <= '1';
	Q <= '0';
	D <= '0';
	N <= '0';
	wait until RISING_EDGE(clock);
	set <= '0';
	wait until (finished = '1');		--wait write mem
	wait until RISING_EDGE(clock);
	--Finished program, return to IDLE
	reset <= '1';
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	
	
	--Enter to display mode to verify prices
	funct <= DISPLAY;
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until (finished = '1');		--wait read mem
	wait until RISING_EDGE(clock);
	prod <= "01";
	wait until (finished = '1');		--wait read mem
	wait until RISING_EDGE(clock);
	prod <= "10";
	--change prod before read is ready
	wait for 2 ns;
	prod <= "11";
	wait until (finished = '1');		--wait read mem
	wait until RISING_EDGE(clock);
	prod <= "10";
	wait until (finished = '1');		--wait read mem
	wait until RISING_EDGE(clock);
	--go back to IDLE
	reset <= '1';
	wait until RISING_EDGE(clock);
	reset <= '0';
	
	
	--test FREE MODE
	funct <= FREE;
	prod <= "00";
	wait until RISING_EDGE(clock);
	--request free prod 00;
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait until (finished = '1');		--wait product delivered
	wait until RISING_EDGE(clock);
	--request free prod 01
	prod <= "01";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait until (finished = '1');		--wait product delivered
	wait until RISING_EDGE(clock);
		--request free prod 10
	prod <= "10";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait until (finished = '1');		--wait product delivered
	wait until RISING_EDGE(clock);
		--request free prod 11
	prod <= "11";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait until (finished = '1');		--wait product delivered
	wait until RISING_EDGE(clock);
	
	
	--TEST VEND MODE
	funct <= VENDING;
	prod <= "00";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait for 5 ns;--wait correct price is ready in vend module
	--buy prod "00" with extra cash 0.40 >0.25
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1';
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '0';
	N <= '0';
	wait until (finished = '1');
	wait until RISING_EDGE(clock);
	--buy prod 01 with exact cash 255>255
	funct <= VENDING;
	prod <= "01";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait for 5 ns;--wait correct price is ready in vend module
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1';  --0.40
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --0.80
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --1.20
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --1.60
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --2.00
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --2.40
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '1';
	N <= '1'; --2.80
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '0';
	N <= '0';
	wait until (finished = '1');
	wait until RISING_EDGE(clock);
	--buy prod 10 with extra cash for 2 cycles
	funct <= VENDING;
	prod <= "10";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait for 5 ns;--wait correct price is ready in vend module
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '0';
	N <= '1'; --0.30
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '0';
	N <= '0'; --0.55
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '0';
	N <= '1'; --0.60
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '0';
	N <= '0';
	wait until (finished = '1');
	wait until RISING_EDGE(clock);
	--buy prod 11 with reset in the middle restarting the transaction
	funct <= VENDING;
	prod <= "11";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait for 5 ns;--wait correct price is ready in vend module
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --0.40
	wait until RISING_EDGE(clock);
	reset <= '1';
	wait until RISING_EDGE(clock);
	reset <= '0';
	wait until RISING_EDGE(clock);
	funct <= VENDING;
	prod <= "11";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait for 5 ns;--wait correct price is ready in vend module
	wait until RISING_EDGE(clock);
	Q <= '1';
	D <= '1';
	N <= '1'; --0.40
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '1';
	N <= '0'; --0.50
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '0';
	N <= '0'; --0.40
	wait until (finished <= '1');
	wait until RISING_EDGE(clock);
	
	
	--Test HARDRESET
	wait for 7 ns;
	hard_reset <= '1';
	wait until RISING_EDGE(clock);
	hard_reset <= '0';
	wait until (finished = '1');
	--Display products 
	funct <= DISPLAY;
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until (finished = '1');		--wait read mem
	wait until RISING_EDGE(clock);
	prod <= "01";
	wait until (finished = '1');		--wait read mem
	wait until RISING_EDGE(clock);
	prod <= "10";
	wait until (finished = '1');		--wait read mem
	wait until RISING_EDGE(clock);
	prod <= "11";
	wait until (finished = '1');		--wait read mem
	wait until RISING_EDGE(clock);
	--go back to IDLE
	reset <= '1';
	wait until RISING_EDGE(clock);
	reset <= '0';
	--Reprogram product 01 = 0.15
	funct <= PROGRAM;
	prod <= "01";
	wait until RISING_EDGE(clock);
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait until RISING_EDGE(clock);
	Q <= '0';
	D <= '1';
	N <= '1';
	wait until RISING_EDGE(clock);
	set <= '1';
	Q <= '0';
	D <= '0';
	N <= '0';
	wait until RISING_EDGE(clock);
	set <= '0';
	wait until (finished <= '1');
	wait until RISING_EDGE(clock);
	--back to idle
	reset <= '1';
	wait until RISING_EDGE(clock);
	reset <= '0';
	funct <= DISPLAY;
	wait until RISING_EDGE(clock);
	--display product
	start <= '1';
	wait until RISING_EDGE(clock);
	start <= '0';
	wait until (finished = '1');
	wait until RISING_EDGE(clock);
	
	
end process;
end test;