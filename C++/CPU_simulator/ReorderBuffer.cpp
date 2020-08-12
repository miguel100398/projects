#include "ReorderBuffer.h"

ReorderBuffer::ReorderBuffer(){
    num_entries = 0;
}

bool ReorderBuffer::isEmpty(){
    return (num_entries==0);
}

bool ReorderBuffer::OldestExecuted(){
    if (rob_list.front().valid_bit){
        rob_list.pop_front();
        return true;
    }else{
        return false;
    }
}

void ReorderBuffer::setCompleteInstruction(long robid){
    unsigned int i = 0;
    while (rob_list[i].valid_bit==1){
        i++;
    }
    if (rob_list[i].rob_id==robid){
        rob_list[i].valid_bit = true;
    }else{
        //throw exception
    }
}

bool ReorderBuffer::HasEntries(){
    return (num_entries<MAX_ENTRIES);
}

void ReorderBuffer::InstructionInsertion(Instruction instruction, long robid){
    if (num_entries>=MAX_ENTRIES){
        //trhow exception
    }else{
        rob_data_t tmp_data;
        tmp_data.instruction = instruction;
        tmp_data.rob_id = robid;
        tmp_data.valid_bit = false;
        rob_list.push_back(tmp_data);
    }
}