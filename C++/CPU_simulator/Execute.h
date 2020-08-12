#pragma once
#include <iostream>
#include "Instruction.h"
#include "RegisterFile.h"
#include "IQueue.h"
using namespace std;

//typedef enum op_enum {ADD=0, SUB=1, MULTIPLY=2, DIVIDE=3, MODULO=4, BEQ=5, BNE=6, MOV=7, MOV_PRINT=8, JUMP=9} op_t;


class Execute
{
private:
	op_t opcode;
	unsigned int operand1;
	unsigned int operand2;
	unsigned int result;
	unsigned int dest;
	char type;
	RegisterFile registerfile;

public:
	Execute();
	unsigned int getResult(Instruction instruction, int PC);
	void setRegisterfile(RegisterFile newregisterfile);
};