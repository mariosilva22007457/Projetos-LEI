// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include <stdio.h>
#include "client.h"
#include "memory.h"
#include "memory-private.h"
#include "main.h"
#include "sotime.h"

/* Função principal de um Cliente. Deve executar um ciclo infinito
* onde em cada iteração tem dois passos: primeiro, lê uma operação
* da main e se a mesma lida tiver id diferente de -1 e se data->terminate
* ainda for igual a 0, processa-a e escreve-a para os proxies; segundo,
* lê uma resposta dos servidores e se a mesma tiver id diferente de -1 e se
* data->terminate ainda for igual a 0, processa-a. Operações com id igual a
* -1 são ignoradas (op inválida) e se data->terminate for igual a 1 é porque
* foi dada ordem de terminação do programa, portanto deve-se fazer return do
* o número de operações processadas. Para efetuar estes passos, pode usar os
* outros métodos auxiliares definidos em client.h.
*/
int execute_client(int client_id, struct communication_buffers* buffers, struct main_data* data, struct semaphores* sems){
    int counter_cli = 0; 
    while (1) {
        //ler operacao da main
        struct operation op_main;
        client_get_operation(&op_main, buffers, data,sems);
        //checkar operacao
        if(*data->terminate == 0 && op_main.id != -1){
            //processar operacao
            client_process_operation(&op_main, client_id, &counter_cli);
            //escrever operacao para proxies
            client_send_operation(&op_main, buffers, data,sems);
        }   

        //ler operacao do servidor
        struct operation op_server;
        client_receive_answer(&op_server, buffers, data,sems);
        //checkar operacao
        
        if(*data->terminate == 0 && op_server.id != -1){
            //processar operacao
            client_process_answer(&op_server, data,sems);
        }

        //ordem de terminacao do programa
        if(*data->terminate == 1){
            return counter_cli;
        }
    }
}


/* Função que lê uma operação do buffer de memória partilhada entre a
* main e clientes. Antes de tentar ler a operação, deve verificar se
* data->terminate tem valor 1. Em caso afirmativo, retorna imediatamente
* da função.
*/
void client_get_operation(struct operation* op, struct communication_buffers* buffers, struct main_data* data, struct semaphores* sems){
    if(*data->terminate == 1){
        return;
    }
    read_rnd_access_buffer(buffers->main_cli, data->buffers_size, op);
}


/* Função que processa uma operação, alterando o seu campo cliente para o id
* passado como argumento, alterando o estado da mesma para 'C' (client), e
* incrementando o contador de operações.
*/
void client_process_operation(struct operation* op, int cient_id, int* counter){
    timerAcoes(op,1);
    op->client=cient_id;
    op->status='C';
    counter++;
};


/* Função que escreve uma operação no buffer de memória partilhada entre
* clientes e proxies.
*/
void client_send_operation(struct operation* op, struct communication_buffers* buffers, struct main_data* data, struct semaphores* sems){
    write_circular_buffer(buffers->cli_prx, data->buffers_size, op);
}


/* Função que lê uma operação do buffer de memória partilhada entre
* servidores e clientes. Antes de tentar ler a operação, deve verificar
* se data->terminate tem valor 1. Em caso afirmativo, retorna imediatamente
* da função.
*/
void client_receive_answer(struct operation* op, struct communication_buffers* buffers, struct main_data* data, struct semaphores* sems){
    if (*data->terminate==1)
    {
        return;
    }
    read_circular_buffer(buffers->srv_cli, data->buffers_size, op);
}


/* Função que guarda uma operação no array de operações finalizadas da
* estrutura data, usando o id da mesma para indexar o array.
*/
void client_process_answer(struct operation* op, struct main_data* data, struct semaphores* sems){
    timerAcoes(op, 4);
    data->results[op->id] = *op;
    printf("Operation %d concluded! It may now be read!\n", op->id);
}