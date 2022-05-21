// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include <stdio.h>
#include "server.h"
#include "memory.h"
#include "main.h"
#include "main-private.h"
#include "sotime.h"

/* Função principal de um Servidor. Deve executar um ciclo infinito onde em 
* cada iteração do ciclo lê uma operação dos proxies e se a mesma tiver id 
* diferente de -1 e se data->terminate ainda for igual a 0, processa-a e
* escreve a resposta para os clientes. Operações com id igual a -1 são 
* ignoradas (op inválida) e se data->terminate for igual a 1 é porque foi 
* dada ordem de terminação do programa, portanto deve-se fazer return do
* número de operações processadas. Para efetuar estes passos, pode usar os
* outros métodos auxiliares definidos em server.h.
*/
int execute_server(int server_id, struct communication_buffers *buffers, struct main_data *data, struct semaphores *sems)
{
    int counter_srv = 0;
    while(1){
        struct operation op_proxy;
        server_receive_operation(&op_proxy, buffers, data,sems);
        if(*data->terminate == 0 && op_proxy.id != -1){
            server_process_operation(&op_proxy, server_id, &counter_srv);
            server_send_answer(&op_proxy, buffers, data,sems);
        }
 
        if(*data->terminate == 1){
            return counter_srv;
        }
    }
}

/* Função que lê uma operação do buffer de memória partilhada entre
* proxies e servidores. Antes de tentar ler a operação, deve verificar 
* se data->terminate tem valor 1. Em caso afirmativo, retorna imediatamente 
* da função.
*/
void server_receive_operation(struct operation *op, struct communication_buffers *buffers, struct main_data *data, struct semaphores *sems)
{
    if(*data->terminate == 1){
        return;
    }
    read_rnd_access_buffer(buffers->prx_srv, data->buffers_size, op);
}

/* Função que processa uma operação, alterando o seu campo server para o id
* passado como argumento, alterando o estado da mesma para 'S' (served), e 
* incrementando o contador de operações.
*/
void server_process_operation(struct operation* op, int server_id, int* counter){
    timerAcoes(op,3);
    op->server=server_id;
    op->status='S';
    counter++;
}

/* Função que escreve uma operação no buffer de memória partilhada entre
* servidores e clientes.
*/
void server_send_answer(struct operation *op, struct communication_buffers *buffers, struct main_data *data, struct semaphores *sems)
{
    printf("server read!\n");
    write_circular_buffer(buffers->srv_cli, data->buffers_size, op);
}