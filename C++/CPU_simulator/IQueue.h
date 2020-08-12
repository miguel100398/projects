#pragma once
#include "Instruction.h"
#include <queue>
#include "RegisterFile.h"
using namespace std;
#define MAX_ENTRIES 10

typedef enum op_enum {ADD=0, SUB=1, MULTIPLY=2, DIVIDE=3, MODULO=4, BEQ=5, BNE=6, MOV=7, MOV_PRINT=8, JUMP=9} op_t;

typedef struct iqueue_list_s{
	op_t operation;
	bool valid1;
	bool src1;
	bool valid2;
	bool src2;
	long rob_id;
	Instruction instruction;
} iqueue_list_t;

class IQueue
{
	deque<iqueue_list_t> IQ;
	int num_entries;
	RegisterFile registerfile;
public:
	IQueue();

	void insertInstruction(Instruction newInst, long robId);
	void emptyQueue();
	bool checkNextValidity();
	Instruction popInstruction();
	void printIQ();
	void setRegisterFile(RegisterFile newregisterfile);
	bool HasEntries();
};