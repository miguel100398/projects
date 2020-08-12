#pragma once
#include <iostream>
#include "Memory.h"
#include "Instruction.h"
#include <string>
using namespace std;

#define MEMORY_SIZE 200

class FetchUnit {
	private:
		Memory memory;
	public:
		FetchUnit();
		FetchUnit(string program);
		Instruction FetchInstruction(int PC);
		bool HasInstruction(int PC);
};
