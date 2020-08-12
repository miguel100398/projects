#pragma once
#include "IQueue.h"
#include "Memory.h"
#include "RegisterFile.h"
#include "Execute.h"
#include "fetchunit.h"
#include "ReorderBuffer.h"
#include "HelperFunctions.h"
#include "Statistics.h"

#define FETCH_WIDTH 1
#define ISSUE_WIDTH 1
#define COMMIT_WIDTH 1

class Pipeline{
    
    protected:
		IQueue iqueue;
		FetchUnit fetchunit;
		RegisterFile registerfile;
		ReorderBuffer ROB;
		HelperFunctions helperfunctions;
        Statistics statistics;
		Execute execute;
		int PC;
		bool stall_fetch;
		long ROB_ID;
		bool finish;
    public:
		Pipeline();
		Pipeline(string program);
        void Pipe_Fetch();
        void Pipe_Execute();
        void Pipe_Commit();
		bool getFinish();
		void initMem(string program);
		void getStats();
};