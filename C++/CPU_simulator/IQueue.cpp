#include "IQueue.h"
#include <iostream>

IQueue::IQueue(){
	num_entries = 0;
}

void IQueue::insertInstruction(Instruction newInst, long robId) 
{
	iqueue_list_t tmp_list;
	
	if (newInst.getType() == 'J'){
		tmp_list.operation = JUMP;
		tmp_list.src1 = -1;
		tmp_list.valid1 = true;
		tmp_list.src2 = -1;
		tmp_list.valid2 = true;
	}else if(newInst.getType() == 'P'){
		tmp_list.operation = static_cast<op_t>(newInst.getOpcode());
		tmp_list.src1 = -1;
		tmp_list.valid1 = true;
		tmp_list.src2 = -1;
		tmp_list.valid2 = true;
		registerfile.valid[newInst.getDest()] = false;
	}else{
		tmp_list.operation = static_cast<op_t>(newInst.getOpcode());
		tmp_list.src1 = newInst.getSrc1();
		tmp_list.valid1 = registerfile.valid[tmp_list.src1];
		if (newInst.getType() == 'I'){
			tmp_list.src2 = -1;
			tmp_list.valid2 = true;
		}else if (newInst.getType() == 'R'){
			tmp_list.src2 = newInst.getSrc2();
			tmp_list.valid2 = registerfile.valid[tmp_list.src2];
		}
		registerfile.valid[newInst.getDest()] = false;
	}

	tmp_list.instruction = newInst;
	tmp_list.instruction.RobId = robId;
	num_entries++;
	IQ.push_back(tmp_list);
}

void IQueue::setRegisterFile(RegisterFile newregisterfile){
	registerfile = newregisterfile;
}

void IQueue::emptyQueue()
{
	num_entries = 0;
	IQ.clear();
}

bool IQueue::checkNextValidity() 
{
	return IQ.front().valid1 && IQ.front().valid2;
}

Instruction IQueue::popInstruction()
{
	Instruction temp = IQ.front().instruction;
	IQ.pop_front();
	num_entries--;
	return temp;
}

void IQueue::printIQ() {
	for (int i = 0; i < IQ.size(); i++)
	{
		cout << "IQueue[" << i << "]" << endl << "IQ[i]" << endl;
	}
}

bool IQueue::HasEntries(){
	return (num_entries<MAX_ENTRIES);
}