library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity voting is
	PORT(
	clk, reset: IN STD_LOGIC;
	vote_inp: IN STD_LOGIC_VECTOR(3 downto 0);
	warn: OUT STD_LOGIC;
	num_1, num_2, num_3, num_abs: OUT STD_LOGIC_VECTOR(4 downto 0);
	decision: OUT STD_LOGIC_VECTOR (2 downto 0)
	);
end voting;

ARCHITECTURE behavior of voting is
signal count_1, count_2, count_3, count_abs: STD_LOGIC_VECTOR(4 downto 0);
signal invalid_vote: STD_LOGIC;
begin

process(vote_inp) begin
	if (((vote_inp(3) and vote_inp(2))or(vote_inp(3) and vote_inp(1))or(vote_inp(3) and vote_inp(0))or(vote_inp(2) and vote_inp(1))or(vote_inp(2) and vote_inp(0))or(vote_inp(1) and vote_inp(0)))='1' ) then
		invalid_vote <= '1';
	else
		invalid_vote <= '0';
	end if;
end process;
num_1 <= count_1;
num_2 <= count_2;
num_3 <= count_3;
num_abs <= count_abs;

process(clk, reset) begin
	if(reset='1') then
		count_1 <= "00000";
		count_2 <= "00000";
		count_3 <= "00000";
		count_abs <= "00000";
		warn <= '0';
	elsif RISING_EDGE(clk)then
		if (invalid_vote = '1') then
			warn<='1';
			count_1 <= count_1;
			count_2 <= count_2;
			count_3 <= count_3; 
			count_abs <= count_abs;
		else 
			warn <= '0';
			count_1 <= count_1+vote_inp(3);
			count_2 <= count_2+vote_inp(2);
			count_3 <= count_3+vote_inp(1);
			count_abs <= count_abs+vote_inp(0);
		end if;
	end if;
end process;

process(reset, count_1, count_2, count_3) begin
	if (reset='1') then
		decision <= "000";
	else 
		if ((count_1 = count_2) and (count_2 = count_3) and (count_1=count_3)) then
			decision <= "111";
		elsif(count_1 = count_2) then
			if (count_1>count_3) then
				decision <= "110";
			else 
				decision <= "001";
			end if;
		elsif (count_1 = count_3) then
			if (count_1>count_2) then
				decision <= "101";
			else 
				decision <= "010";
			end if;
		elsif (count_2 = count_3) then
			if (count_2>count_1) then
				decision <= "011";
			else 
				decision <= "100";
			end if;
		else 
			if (count_1>count_2) then
				if (count_1>count_3) then
					decision <= "100";
				else 
					decision <= "001";
				end if;
			else 
				if (count_2>count_3) then
					decision <= "010";
				else 
					decision <= "001";
				end if;
			end if;
		end if;	
	end if;
end process;




end behavior;