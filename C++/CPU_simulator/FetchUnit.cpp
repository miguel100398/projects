#include "fetchunit.h"

FetchUnit::FetchUnit(){
    
}

FetchUnit::FetchUnit(string program){
    Memory memory(MEMORY_SIZE, program);
}

Instruction FetchUnit::FetchInstruction(int PC){
    return memory.getMemory(PC);
}

bool FetchUnit::HasInstruction(int PC){
	return memory.HasInstruction(PC);
}
