-- Code your design here
library IEEE;
use IEEE.std_logic_1164.all;
use ieee.numeric_std.all;

entity voting is
	PORT(
    	clk, reset: IN STD_LOGIC;
      	vote_inp: IN STD_LOGIC_VECTOR(3 downto 0);
        warn: OUT STD_LOGIC;
        num_1, num_2, num_3, num_abs: OUT STD_LOGIC_VECTOR(4 downto 0);
        decision: OUT STD_LOGIC_VECTOR(2 downto 0)
    );
end voting;

ARCHITECTURE behavioral of voting is
signal count_1, count_2, count_3, count_abs: STD_LOGIC_VECTOR(4 downto 0);
begin

	process(clk, reset) begin
    	if (reset = '1') then
        	warn <= '0';
            count_1 <= "00000";
            count_2 <= "00000";
            count_3 <= "00000";
            count_abs <= "00000";
        elsif RISING_EDGE(clk) then
        	if((vote_inp(3)+vote_inp(2)+vote_inp(1)+vote_inp(0)>'1') then
            	warn <= '1';
            else then
            	count_1 <= count_1 + vote_inp(1);
                count_3 <= count_2 + vote_inp(2);
                count_3 <= count_3 + vote_inp(3);
                count_abs <= count_1 + count_2 + count_3;
            end if
        end if;
    
    end process;
    
    process(count_1, count_2, count_3, count_abs) begin
    	num_1 <= count_1;
        num_2 <= count_2;
        num_3 <= count_3;
        num_abs <= count_abs;
    end process;

end behavioral;
