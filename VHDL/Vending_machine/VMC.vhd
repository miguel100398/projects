--VENDING MACHINE CONTROLLER PRIMARY ENTITY

library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;


use work.VMC_PKG.all;

entity VMC is
	PORT(
		clock, reset, hard_reset: IN STD_LOGIC;
		start, set: IN STD_LOGIC;
		N, D, Q: IN STD_LOGIC;
		funct: IN VMC_MODE;
		prod: IN PROD_T;
		change0, change1, change2: OUT DISPLAY_CASH_T;
		runTotal0, runTotal1, runTotal2: OUT DISPLAY_CASH_T;
		total0, total1, total2: OUT DISPLAY_CASH_T;
		finished: OUT STD_LOGIC
	);
end VMC;

architecture behave of VMC is
SIGNAL current_state, next_state, possible_state: VMC_STATE;
SIGNAL en_program_unit, en_display_mode, en_vending_unit: STD_LOGIC;
SIGNAL write_data_mem, read_data_mem: CASH_INTERNAL_T;
SIGNAL write_mem_addr, read_mem_addr, mem_addr: MEM_ADDR_T;
SIGNAL program_unit_done, mem_wen, mem_ren: STD_LOGIC;
SIGNAL BCD_change, BCD_total, vending_BCD_runtotal, program_BCD_runtotal: BCD_T;
SIGNAL vending_unit_done: STD_LOGIC;
SIGNAL total_shifted, runtotal_shifted: CASH_T;
SIGNAL total2BCD, runtotal2BCD: BINARY_BCD_T;
SIGNAL display_done: STD_LOGIC;
SIGNAL free_done: STD_LOGIC;
SIGNAL deassert_display: STD_LOGIC;
COMPONENT program_unit PORT(
									clock, reset, hard_reset, set, enable: IN STD_LOGIC;
									product: IN PROD_T;
									N, D, Q: IN STD_LOGIC;
									data_mem: OUT CASH_INTERNAL_T;
									addr_out: OUT MEM_ADDR_T;
									done, wen: OUT STD_LOGIC
									); END COMPONENT;
									
COMPONENT SRAM PORT
						(
						address		: IN STD_LOGIC_VECTOR (1 DOWNTO 0);
						clock		: IN STD_LOGIC  := '1';
						data		: IN STD_LOGIC_VECTOR (5 DOWNTO 0);
						rden		: IN STD_LOGIC  := '1';
						wren		: IN STD_LOGIC ;
						q		: OUT STD_LOGIC_VECTOR (5 DOWNTO 0)
						); END COMPONENT;
						
COMPONENT display_mode PORT(
									reset, en: IN STD_LOGIC;
									clk: IN STD_LOGIC;
									hard_reset: IN STD_LOGIC;
									product: IN PROD_T; 
									addr_mem: OUT MEM_ADDR_T;
									mem_ren: OUT STD_LOGIC;
									display_done: OUT STD_LOGIC;
									deassert_display: OUT STD_LOGIC
									); END COMPONENT;


COMPONENT vending_unit PORT(
									clock, reset, enable: IN STD_LOGIC;
									price_in: IN CASH_INTERNAL_T;
									N, D, Q: IN STD_LOGIC;
									change, insert_out: OUT BCD_T;
									done: OUT STD_LOGIC
									); END COMPONENT;	

COMPONENT convert2bcd PORT(
									binary: IN BINARY_BCD_T;
									bcd: OUT BCD_T
									); END COMPONENT;

									
begin

program_unit1: program_unit PORT MAP(
												clock => clock,
												reset => reset,
												hard_reset => hard_reset,
												set => set,
												enable => en_program_unit,
												product => prod,
												N => N,
												D => D,
												Q => Q,
												data_mem => write_data_mem,
												addr_out => write_mem_addr,
												done => program_unit_done,
												wen => mem_wen
												);
						
SRAM1: SRAM PORT MAP(
							address => mem_addr,
							clock => clock,
							data => write_data_mem,
							rden => mem_ren,
							wren => mem_wen,
							q => read_data_mem
							);

display_mode1: display_mode PORT MAP(
												reset => reset,
												en => en_display_mode,
												clk => clock,
												hard_reset => hard_reset,
												product => prod,
												addr_mem => read_mem_addr,
												mem_ren => mem_ren,
												display_done => display_done,
												deassert_display => deassert_display
												);
												
vending_unit1: vending_unit PORT MAP(
												clock => clock,
												reset => reset,
												enable => en_vending_unit,
												price_in => read_data_mem,
												N => N,
												D => D,
												Q => Q,
												change => BCD_change,
												insert_out => vending_BCD_runtotal,
												done => vending_unit_done
												);
												
convert_total: convert2bcd PORT MAP(
												binary => total2BCD,
												bcd => BCD_total
												);
												
convert_runTotal: convert2bcd PORT MAP(
												binary => runtotal2BCD,
												bcd => program_BCD_runtotal
													);
												
--FSM
process (reset, clock, hard_reset) BEGIN
	if (hard_reset = '1') then
		current_state <= HARDRESET;
	elsif (reset ='1') then
		current_state <= IDLE;
	elsif RISING_EDGE(clock) then
		current_state <= next_state;
	end if;
end process;

process (reset, start, set, current_state, free_done, possible_state, program_unit_done, vending_unit_done) begin
	if (reset = '1') then
		next_state <= IDLE;
	else
		case current_state is
			when IDLE =>
				if (start ='1') then
					next_state <= possible_state;
				else
					next_state <= IDLE;
				end if;
			when HARDRESET =>
				if (program_unit_done = '1') then
					next_state <= IDLE;
				else
					next_state <= HARDRESET;
				end if;
			when PROGRAM =>
			--	if (program_unit_done = '1') then  --Descomment this lines if you want to start to
					next_state <= IDLE;				  --IDLE when a product is programed
			--	else										  --otherwise will return only on reset
					next_state <= PROGRAM;
			--	end if;
			when DISPLAY => next_state <= DISPLAY;
			when VEND =>
				if (vending_unit_done = '1') then
					next_state <= IDLE;
				else
					next_state <= VEND;
				end if;
			when FREE => 
				if (free_done <= '1') then
					next_state <= IDLE;
				else
					next_state <= FREE;
				end if;
			when others =>
				next_state <= IDLE;
		end case;
	end if;
end process;

process (funct) begin
	case funct is
		WHEN PROGRAM =>	possible_state <=PROGRAM;
		WHEN DISPLAY =>	possible_state <=DISPLAY;
		WHEN VENDING =>	possible_state <=VEND;
		WHEN FREE =>	possible_state <=FREE;
		WHEN others => possible_state <= IDLE;
	end case;
end process;

process (current_state, reset, read_mem_addr, write_mem_addr, display_done) begin
	if (reset='1') then
	en_program_unit <= '0';
	en_display_mode <= '0';
	en_vending_unit <= '0';
	mem_addr <= std_logic_vector(to_unsigned(0,mem_addr'length));
	else
		case current_state is
			when IDLE =>
				en_program_unit <= '0';
				en_display_mode <= '0';
				en_vending_unit <= '0';
				mem_addr <= std_logic_vector(to_unsigned(0,mem_addr'length));
			when HARDRESET =>
				en_program_unit <= '1';
				en_display_mode <= '0';
				en_vending_unit <= '0';
				mem_addr <= write_mem_addr;
			when PROGRAM =>
				en_program_unit <= '1';
				en_display_mode <= '0';
				en_vending_unit <= '0';
				mem_addr <= write_mem_addr;
			when DISPLAY =>
				en_program_unit <= '0';
				en_display_mode <= '1';
				en_vending_unit <= '0';
				mem_addr <= read_mem_addr;
			when VEND =>
				en_program_unit <= '0';
				en_display_mode <= '1';
				if (display_done = '1') then
					en_vending_unit <= '1';
				else
					en_vending_unit <= '0';
				end if;
				mem_addr <= read_mem_addr;
			when FREE =>
				en_program_unit <= '0';
				en_display_mode <= '0';
				en_vending_unit <= '0';
				mem_addr <= read_mem_addr;
		end case;
	end if;
end process;

process (current_state, BCD_change, BCD_total, vending_BCD_runtotal, program_BCD_runtotal) begin
	case current_state is
		when PROGRAM =>
			change2 <= "0000";
			change1 <= "0000";
			change0 <= "0000";
			
			total2 <= "0000";
			total1 <= "0000";
			total0 <= "0000";
			
			runTotal2 <= program_BCD_runtotal(11 downto 8);
			runTotal1 <= program_BCD_runtotal(7 downto 4);
			runTotal0 <= program_BCD_runtotal(3 downto 0);
		when DISPLAY =>
			change2 <= "0000";
			change1 <= "0000";
			change0 <= "0000";
			
			total2 <= BCD_total(11 downto 8);
			total1 <= BCD_total(7 downto 4);
			total0 <= BCD_total(3 downto 0);
			
			runTotal2 <= "0000";
			runTotal1 <= "0000";
			runTotal0 <= "0000";
		when VEND =>
			change2 <= BCD_change(11 downto 8);
			change1 <= BCD_change(7 downto 4);
			change0 <= BCD_change(3 downto 0);
			
			total2 <= BCD_total(11 downto 8);
			total1 <= BCD_total(7 downto 4);
			total0 <= BCD_total(3 downto 0);
			
			runTotal2 <= vending_BCD_runtotal(11 downto 8);
			runTotal1 <= vending_BCD_runtotal(7 downto 4);
			runTotal0 <= vending_BCD_runtotal(3 downto 0);
		when FREE =>
			change0 <= "ZZZZ";
			change1 <= "ZZZZ";
			change2 <= "ZZZZ";
			runTotal0 <= "ZZZZ";
			runTotal1 <= "ZZZZ";
			runTotal2 <= "ZZZZ";
			total0 <= "ZZZZ";
			total1 <= "ZZZZ";
			total2 <= "ZZZZ";
		when others =>
			change0 <= "0000";
			change1 <= "0000";
			change2 <= "0000";
			runTotal0 <= "0000";
			runTotal1 <= "0000";
			runTotal2 <= "0000";
			total0 <= "0000";
			total1 <= "0000";
			total2 <= "0000";
	end case;
	

end process;

process (reset, current_state, program_unit_done, display_done, vending_unit_done, clock, deassert_display) begin
	if (reset = '1') then
		finished <='0';
	elsif (deassert_display = '1' and ((current_state = DISPLAY) or (current_state = VEND))) then
		finished <= '0';
	elsif RISING_EDGE (clock) then
		case current_state is
			when IDLE => finished <= '0';
			when HARDRESET => finished <=  program_unit_done;
			when PROGRAM => finished <= program_unit_done;
			when DISPLAY =>	finished <= display_done;
			when VEND =>	finished <= vending_unit_done;
			when FREE =>	finished <= '1';
				--if (set = '1') then
				--	finished <= '1';
				--else
				--	finished <= '0';
				--end if;
			when others => finished <= '0';
		end case;
	end if;
end process; 

process (clock, reset) begin
	if (reset = '1') then
		free_done <= '0';
	elsif RISING_EDGE(clock) then
		if (current_state = FREE) then
			free_done <='1';
		else
			free_done <= '0';
		end if;
	end if;
end process;

process (read_data_mem,write_data_mem) begin
	total_shifted(7 downto 2) <= read_data_mem;
	total_shifted(1 downto 0) <= "00";
	runtotal_shifted(7 downto 2) <= write_data_mem;
	runtotal_shifted(1 downto 0) <= "00";
end process; 

process (total_shifted, read_data_mem, runtotal_shifted, write_data_mem) begin
	total2BCD <= read_data_mem + total_shifted;
	runtotal2BCD <= write_data_mem + runtotal_shifted;
end process;
		

end behave;