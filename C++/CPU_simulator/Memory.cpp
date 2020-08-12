#include "Memory.h"
#include <iostream>
using namespace std;

Memory::Memory() {
	instructionMemory = new Instruction[16];
};

Memory::Memory(int size, string filename) {
	instructionMemory = new Instruction[size];
	unsigned int* unsignedValues = new unsigned int[size];
	inputStream.open(filename, ifstream::in);
	int i = 0;
	while (!inputStream.eof()) {
		inputStream >> *unsignedValues;
		instructionMemory[i] = Instruction(unsignedValues[i]);
		i++;
	}
};

bool Memory::HasInstruction(int PC){
	if (instructionMemory[PC].exists){
		return true;
	}else{
		return false;
	}
}

Instruction Memory::getMemory(int PC){
	return instructionMemory[PC];
}

Memory::~Memory() {
	delete[] instructionMemory;
};