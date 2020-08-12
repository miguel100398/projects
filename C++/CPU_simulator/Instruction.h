#pragma once
#include "RegisterFile.h"
#include <iostream>

class Instruction 
{
private:
	bool bits[32];
	char type;
	int opcode;
	int address;
	int dest;
	int src1;
	int src2;
	unsigned int immediate;
	
	void RSetup();
	void ISetup();
	void JSetup();
	void PSetup();
public:
	Instruction();
	Instruction(unsigned int unsignedVal);
	bool exists;
	long RobId;
	char getType();
	int getOpcode();
	int getAddress();
	int getDest();
	int getSrc1();
	int getSrc2();
	bool getSrc1Valid(RegisterFile reg);
	bool getSrc2Valid(RegisterFile reg);
	unsigned int getImmediate();

	//friend ostream& operator<<(ostream& os, Instruction& inst);
};