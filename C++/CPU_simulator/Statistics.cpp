#include "Statistics.h"
#include <iostream>

Statistics::Statistics(){
}


void Statistics::fetch_instructions(int num_fetch){
    time_t fetch_time;
    time(&fetch_time);
    for (int i=0; i<num_fetch;i++){
        instructions_latency_start.push(fetch_time);
    }
}

void Statistics::commit_instructions(int num_commit){
    time_t commit_time;
    time(&commit_time);
    time_t fetch_time;
    for (int i=0; i<num_commit;i++){
        fetch_time = instructions_latency_start.front();
        instructions_latency_start.pop();
        instructions_latency.push(difftime(commit_time, fetch_time));
    }
}

void Statistics::execute_instructions(int num_execute){
    instructions_troghput.push(num_execute);
}

void Statistics::get_statistics(){
    int num_latencies;
    time_t accum_latencies;
    int num_troughput;
    int accum_trhoughput;
    float average_latency, average_trougput;
    
    num_latencies = instructions_latency.size();
    num_troughput = instructions_troghput.size();
    accum_latencies = 0;
    accum_trhoughput = 0;

    while (!instructions_latency.empty()){
        accum_latencies += instructions_latency.front();
        instructions_latency.pop();
    }
    while (!instructions_troghput.empty()){
        accum_trhoughput += instructions_troghput.front();
        instructions_troghput.pop();
    }

    average_latency = accum_latencies/num_latencies;
    average_trougput = accum_trhoughput/num_troughput;

    cout << "The average latency is %f\n", average_latency;
    cout << "The average troughput is %f\n", average_trougput;

}


