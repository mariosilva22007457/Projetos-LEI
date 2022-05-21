// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include <time.h>
#include "log.h"
#include "main.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void escreverFicheiroLog(FILE *fp, char* operacao, int op_nr)
{
    time_t clock; 
    char *tempo; 
    clock = time((time_t *)NULL); 
    tempo = ctime(&clock);
    char time[125] = "";
    for(int i = 0; tempo[i] != '\n'; i++){
        time[i] = tempo[i];
    }

    if(op_nr == -1){
        fprintf(fp,"%s %s\n",time,operacao);
    } else {
        fprintf(fp,"%s %s %d\n",time,operacao,op_nr);
    }
    fclose(fp); /*fechar o ficheiro*/
}





