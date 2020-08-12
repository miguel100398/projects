#pragma once
#include "Instruction.h"
#include <fstream>

//Instruction Memory
class Memory
{
private:
	ifstream inputStream;
	Instruction* instructionMemory;
public:
	Memory();
	Memory(int size, string filename);
	~Memory();
	Instruction getMemory(int PC);
	bool HasInstruction(int PC);

	//program memory
};