// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include <time.h>
#include "memory.h"
#include "sotime.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void timerAcoes(struct operation* op, int id){
    time_t clock; 
    struct timespec tempo; 
    int gettimeId = clock_gettime(CLOCK_REALTIME, &tempo);

    if(gettimeId == -1){
        perror("clock gettime");
        exit(EXIT_FAILURE);
    }

    switch (id)
    {
        case 0:
            op->start_time = &tempo;
            break;
        case 1:
            op->client_time = &tempo;
            break;
        case 2:
            op-> proxy_time = &tempo;
            break;
        case 3:
            op-> server_time = &tempo;
            break;
        case 4:
            op-> end_time = &tempo;
            break;
        default:
            break;
    }

}
