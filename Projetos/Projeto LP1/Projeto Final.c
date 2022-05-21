#include <stdio.h>
#include <string.h> /* bibliotecas importadas */
#include <ctype.h>

#define DIM 25   /* dimensão do mapa */
#define size 512 /*tamanho do numero de caracteres a ler, um pouco grande demais, mas para garantir que não existem quaisquer problemas */

void map(char tray[DIM][DIM])
/* esta função cria o tabuleiro(mapa 25 por 25) com posições vazias*/
{

    int i, j;
    for (i = 0; i < 25; i++)
    {
        for (j = 0; j < 25; j++)
        {

            tray[i][j] = '_';
        }
    }
}

void printMenu() /* faz print do menu do jogo*/
{

    printf("+-----------------------------------------------------\nshow                - show the mine map\npropagate <x> <y>   - explode bomb at <x> <y>\nlog <x> <y>         - explode bomb at <x> <y>\nplant <x> <y>       - place armed mine at <x> <y>\nexport <filename>   - save file with current map\nquit                - exit program\nsos                 - show menu\n+-----------------------------------------------------\n");
}

void printCorrupted()
/* esta função da feedback e alerta o utilizador ao facto de o ficheiro que foi lido se encontrar inválido */
{
    printf("File is corrupted\n");
}

void printErrorOpen()
/* esta função da feedback e alerta o utilizador ao facto de que não foi possivel abrir o ficheiro pedido */
{

    printf("Error opening file\n");
}

void printMissingName(){

    printf("Error: missing file name");
}

void displayMap(char tray[DIM][DIM])
{
    /* esta função faz display do mapa (25 por 25)  do jogo*/
    int i, j;
    for (i = 0; i < DIM; i++)
    {
        for (j = 0; j < DIM; j++)
        {

            printf("%c", tray[i][j]);
        }
        printf("\n");
    }
}

int fileExport(char tray[DIM][DIM], char *fileName) /* função responsável por exportar o tabuleiro mapa providenciado para um fichiero */

{
    int i;
    int j;
    FILE *fp = fopen(fileName, "w");
    if (fp == NULL) /* verifica se o fichiero é null, se sim,alerta para o caso */
    {
        printErrorOpen();
        return 0;
    }

    fprintf(fp, "25 25\n"); /* faz a escrita para o ficheiro */

    for (i = 0; i < DIM; i++)
    {
        for (j = 0; j < DIM; j++)
        {
            /*verifica se o tabuleiro não se encontra vazio e procede a realizar a escita para o ficheiro*/
            if (tray[i][j] != '_')
            {
                fprintf(fp, " %c %d %d\n", tray[i][j], i, j);
            }
        }
    }
    fclose(fp); /*para a escrita(modificação) para o ficheiro,fechando o ficheiro em si */
    return 0;
}

int fileReader(char tray[DIM][DIM], char *fileName) /* realiza a leitura de um ficheiro que contém um mapa do jogo */
{
    char word[size];
    int xCoord, yCoord;
    int firstRow = 1;
    char option;

    FILE *fp = fopen(fileName, "r");

    if (fp == NULL) /* verifica se o fichiero é null, se sim,alerta para o caso */
    {
        printErrorOpen();
        return 0;
    }

    while (!feof(fp)) /*continua o cliclo até encontrar o final do ficheiro sobre o qual está a realizar a leitura*/
    {

        fgets(word, size, fp);

        sscanf(word, " %c %d %d", &option, &xCoord, &yCoord);
        if (word[0] == '\r' || word[0] == '#' || word[0] == '\n') /* continua se existe espaço vazio */
        {

            continue;
        }

        if (firstRow) /* if true*/
        {
            firstRow = 0;
            sscanf(word, "%d %d", &xCoord, &yCoord);
            if (sscanf(word, " %d %d", &xCoord, &yCoord) != 2) /* se são providenciados mais que dois parametros,alerta o utilizador para esse facto*/

            {
                map(tray); /* faz reset("limpa")  o mapa do jogo */
                printCorrupted();
                return 0;
            }

            else if (xCoord < 0 || yCoord < 0) /* caso as coordenadas de jogo estejam incorretas, alerta para esse facto*/
            {
                map(tray);
                printCorrupted();
                return 0;
            }

            continue;
        }

        else if ((option != '.' && option != '*') || xCoord < 0 || xCoord > 24 || yCoord < 0 || yCoord > 24 || sscanf(word, " %c %d %d", &option, &xCoord, &yCoord) != 3)
        /* se são providenciados mais que três parametros ou as coordenadas estejam incorretas,alerta o utilizador para esse facto*/

        {
            map(tray); /* faz reset("limpa")  o mapa do jogo */
            printCorrupted();
            return 0;
        }

        tray[xCoord][yCoord] = option;
    }

    fclose(fp);
    return 0;
}

int main()
{
    char option[size] = "";
    char fileName[size] = "";
    char awaiting = '>'; /* variavel para o menu,qunado aguarda input do utilizador */
    char off = '*';      /* variavel para a bomba em estado "off" */
    int xCoord, yCoord;  /* coordenadas */

    char tray[DIM][DIM]; /* tabuleiro(mapa do jogo) */

    printMenu(); /* faz print do menu,chamando a função printMenu */

    map(tray); /* faz reset("limpa")  o mapa do jogo */

    do
    {

        printf("%c", awaiting); /* espera por input */

        scanf(" %s", option); /* recebe a opção providenciada pelo utilizador*/

        if (strcmp(option, "sos") == 0) /* opção SOS,mostra novamente o menu */
        {
            printMenu();
        }

        else if (strcmp(option, "log") == 0) /* opção plant,"planta" as bombas */
        {
            scanf(" %d %d", &xCoord, &yCoord);
            if ((xCoord > 24 || yCoord > 24) || (xCoord < 0 || yCoord < 0))
            {
                printf("Error: invalid coordinate"); /* verifica se as coordenadas estão incorretas,se sim alerta para o facto */
                continue;
            }

            tray[xCoord][yCoord] = '.'; /* "planta a bomba no tabuleiro de jogo" */
            continue;
        }

        else if (strcmp(option, "read") == 0) /* opção read, usa a função fileReader para ler um ficheiro */
        {
            map(tray); /*resets the map */
            scanf(" %s", fileName);
            fileReader(tray, fileName);
            continue;
        }

        else if (strcmp(option, "show") == 0) /*opção show , faz print do mapa usando a função displayMap() */
        {
            displayMap(tray);
        }

        else if (strcmp(option, "plant") == 0) /* opção trigger, realiza o "trigger" da bomba no mapa*/
        {

            scanf(" %d %d", &xCoord, &yCoord);

            if ((xCoord > 24 || yCoord > 24) || (xCoord < 0 || yCoord < 0)) /* verifica se as coordenadas estão incorretas,se sim alerta para o facto */
            {
                printf("Error: invalid coordinate");
                continue;
            }

            if (tray[xCoord][yCoord] == off) /* se a bomba foi plantada, alerta para o facto  */
            {
                tray[xCoord][yCoord] = '.';
                continue;
            }

            if (tray[xCoord][yCoord] == '.') /* se a bomba armed continua */
            {

                continue;
            }

            if (tray[xCoord][yCoord] != off && tray[xCoord][yCoord] != '.')
            {

                printf("Error: no bomb at specified coordinate\n"); /* se não existir mina nas coordenadas pedidas,alerta para esse facto */
                continue;
            }
        }

        else if (strcmp(option, "propagate") == 0) /* opção trigger, realiza o "trigger" da bomba no mapa*/
        {

            scanf(" %d %d", &xCoord, &yCoord);

            if ((xCoord > 24 || yCoord > 24) || (xCoord < 0 || yCoord < 0)) /* verifica se as coordenadas estão incorretas,se sim alerta para o facto */
            {
                printf("Error: invalid coordinate\n");
                continue;
            }

            if (tray[xCoord][yCoord] == '.') /* se a bomba foi plantada, alerta para o facto  */
            {
                tray[xCoord][yCoord] = off;
                continue;
            }

            if (tray[xCoord][yCoord] == off) /* se a bomba explodiu continua */
            {

                continue;
            }

            if (tray[xCoord][yCoord] != off && tray[xCoord][yCoord] != '.')
            {

                printf("No mine at specified coordinate\n"); /* se não existir mina nas coordenadas pedidas,alerta para esse facto */
                continue;
            }
        }

        else if (strcmp(option, "export") == 0) /* exporta o mapa para um ficheiro   usando a função fileExport */
        {
            scanf("%s", fileName);
            fileExport(tray, fileName);
        }

        else if (strcmp(option, "quit") == 0) /* acaba o programa*/
        {
            return 0;
        }

        else
        {
            printf("Invalid command!\n"); /* em caso de o utilizador escolher outra coisa do que era esperado, faz um aviso e recebe input novamente */
            scanf(" %s", option);
            continue;
        }

    } while (!(strcmp(option, "quit")) == 0); /* só termina quando a opção for quit */

    return 0;
}
