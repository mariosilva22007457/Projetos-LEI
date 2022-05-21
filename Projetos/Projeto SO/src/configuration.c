// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <configuration.h>
#include "main-private.h"

void lerficheiro(struct main_data* data,char* filename){

    FILE *fp;
    char linha[125] = "";
    int count=0;
    char logFile[125] = "";
    fp = fopen(filename, "r");

    if (fp == NULL)
    {
        printf("Error: could not open file");
        exit(0);
    }

    while (!feof(fp)) /*até o ficheiro estar fechado ou seja chegar ao fim do ficheiro*/
    {
        fgets(linha, 125, fp);
        if (count==0)
        {
            sscanf(linha, "%d", &data->max_ops); /*max opc*/
        }else if (count==1)
        {
            sscanf(linha, "%d", &data->buffers_size); /*buffers_size*/
        }else if(count==2)
        {
            sscanf(linha, "%d", &data->n_clients); /*n_clientes*/
        }else if(count==3)
        {
            sscanf(linha, "%d", &data->n_proxies); /*n_proxies*/
        }else if(count==4)
        {
            sscanf(linha, "%d", &data->n_servers); /*max opcn_servers*/
        }else if(count==5)
        {
            sscanf(linha, "%s", data->logFileName); /*log file name*/
        }
        count++;
    }
}