--VENDING MACHINE PACKAGE
library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use IEEE.math_real.all;
use ieee.numeric_std.all;



package VMC_PKG is
	
	constant num_products: integer :=4;
	constant max_cost: integer := 255;
	constant max_cost_internal: integer :=max_cost/5;
	constant wait_cycles_mem: integer := 3;
	
	type VMC_MODE is (PROGRAM, DISPLAY, VENDING, FREE);
	type VMC_STATE is (IDLE, HARDRESET, PROGRAM, DISPLAY, VEND, FREE);
	type PROGRAM_UNIT_STATE is (IDLE, ADDING, MEM_WRITING, HARDRESET);
	
	subtype CASH_T is STD_LOGIC_VECTOR(integer(ceil(log2(real(max_cost))))-1 downto 0);
	subtype CASH_INTERNAL_T is STD_LOGIC_VECTOR(integer(ceil(log2(real(max_cost_internal))))-1 downto 0);
	subtype PROD_T is STD_LOGIC_VECTOR(integer(ceil(log2(real(num_products))))-1 downto 0);
	subtype MEM_COUNTER_T is STD_LOGIC_VECTOR(integer(ceil(log2(real(wait_cycles_mem))))-1 downto 0);
	subtype MEM_ADDR_T is PROD_T;
	subtype BINARY_BCD_T IS STD_LOGIC_VECTOR(7 downto 0);
	subtype BCD_T is STD_LOGIC_VECTOR(11 downto 0);
	subtype ADD3_T is STD_LOGIC_VECTOR(3 downto 0);
	subtype DISPLAY_CASH_T is STD_LOGIC_VECTOR(3 downto 0);
	
end VMC_PKG;