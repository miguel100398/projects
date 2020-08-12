#pragma once
#include <string>
#include <time.h>
#include <queue> 
using namespace std;

class Statistics{

    protected:
        queue<int> instructions_troghput;   //queue to store the throuput of the instructions executed
        queue<int> instructions_latency;    //queue to store the latency of the instructions
        queue<int> instructions_latency_start;
    public:                                                                                          
        Statistics();
        void fetch_instructions(int num_fetch);
        void commit_instructions(int num_commit);
        void execute_instructions(int num_execute);
        void get_statistics();

};