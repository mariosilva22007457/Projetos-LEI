// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "main.h"
#include "main-private.h"
#include "memory.h"
#include "memory-private.h"
#include "process.h"
#include <semaphore.h>
#include "semaphore.h"
#include "configuration.h"
#include "sotime.h"
#include "log.h"
#include <signal.h>

/* Função que lê os argumentos da aplicação, nomeadamente o número
* máximo de operações, o tamanho dos buffers de memória partilhada
* usados para comunicação, e o número de clientes, de proxies e de
* servidores. Guarda esta informação nos campos apropriados da
* estrutura main_data.
*/
void main_args(int argc, char* argv[], struct main_data* data){

	if (argc!=2) {
		printf("File not found. Please create a .txt file with the arguments to start the program\n\n");
		exit(0);
	}
	lerficheiro(data, argv[1]);
}

/* Função que reserva a memória dinâmica necessária para a execução
* do socps, nomeadamente para os arrays *_pids e *_stats da estrutura 
* main_data. Para tal, pode ser usada a função create_dynamic_memory.
*/
void create_dynamic_memory_buffers(struct main_data* data){
	//reserva memoria dinamica *_pids
	data->client_pids = create_dynamic_memory(data->n_clients);
	data->proxy_pids = create_dynamic_memory(data->n_proxies);
	data->server_pids = create_dynamic_memory(data->n_servers);

	//reserva memoria dinamica *_stats
	data->client_stats = create_dynamic_memory(data->max_ops);
	data->proxy_stats = create_dynamic_memory(data->max_ops);
	data->server_stats = create_dynamic_memory(data->max_ops);
}

/* Função que reserva a memória partilhada necessária para a execução do
* socps. É necessário reservar memória partilhada para todos os buffers da
* estrutura communication_buffers, incluindo os buffers em si e respetivos
* pointers, assim como para o array data->results e variável data->terminate.
* Para tal, pode ser usada a função create_shared_memory.
*/
void create_shared_memory_buffers(struct main_data *data, struct communication_buffers *buffers)
{
	buffers->main_cli->ptr = create_shared_memory(STR_SHM_MAIN_CLI_PTR, data->buffers_size);
	buffers->main_cli->buffer = create_shared_memory(STR_SHM_MAIN_CLI_BUFFER, data->buffers_size);
 
	buffers->cli_prx->in = create_shared_memory(STR_SHM_CLI_PRX_PTR, data->buffers_size);
	buffers->cli_prx->buffer = create_shared_memory(STR_SHM_CLI_PRX_BUFFER, data->buffers_size);

	buffers->prx_srv->ptr = create_shared_memory(STR_SHM_PRX_SRV_PTR, data->buffers_size);
	buffers->prx_srv->buffer = create_shared_memory(STR_SHM_PRX_SRV_BUFFER, data->buffers_size);
 
	buffers->srv_cli->in = create_shared_memory(STR_SHM_SRV_CLI_PTR, data->buffers_size);
	buffers->srv_cli->buffer = create_shared_memory(STR_SHM_SRV_CLI_BUFFER, data->buffers_size);

	data->results = create_shared_memory(STR_SHM_RESULTS, data->buffers_size);
	 
	data->terminate = create_shared_memory(STR_SHM_TERMINATE, data->buffers_size);
	*data->terminate = 0;
}

/* Função que inicializa os semáforos da estrutura semaphores. Semáforos
* *_full devem ser inicializados com valor 0, semáforos *_empty com valor
* igual ao tamanho dos buffers de memória partilhada, e os *_mutex com valor
* igual a 1. Para tal pode ser usada a função semaphore_create.
*/
void create_semaphores(struct main_data* data, struct semaphores* sems);

/* Função que inicia os processos dos clientes, proxies e
* servidores. Para tal, pode usar a função launch_process,
* guardando os pids resultantes nos arrays respetivos
* da estrutura data.
*/
void launch_processes(struct communication_buffers* buffers, struct main_data* data, struct semaphores* sems){
	int i, j, k;
	
	//loop para criar operacoes clients
	for(i = 0; i < data->n_clients; i++){
		data->client_pids[i] = launch_process(i, 0, buffers, data,sems);
	}

	//loop para criar operacoes proxies
	for(j = 0; j < data->n_proxies; j++){
		data->proxy_pids[j] = launch_process(j, 1, buffers, data,sems);
	}

	//loop para criar operacoes servers
	for(k = 0; k < data->n_servers; k++){
		data->server_pids[k] = launch_process(k, 2, buffers, data,sems);
	}
	return;
}

void apanha_interrupcao(){
	stop_execution(data_glb, buffers_glb, sems_glb);
	exit(0);
}

/* Função que faz interação do utilizador, podendo receber 4 comandos:
* op - cria uma nova operação, através da função create_request
* read - verifica o estado de uma operação através da função read_answer
* stop - termina o execução do socps através da função stop_execution
* help - imprime informação sobre os comandos disponiveis
*/
void user_interaction(struct communication_buffers* buffers, struct main_data* data, struct semaphores* sems){
	char opcao[125];
	int operacao=-1;
	op_counter_glb = 0;
	FILE* fp;
	fp = fopen(data->logFileName, "w+");
	fclose(fp);

	data_glb = data;
	buffers_glb = buffers;
	sems_glb = sems;
	signal(SIGINT, apanha_interrupcao);

	do
	{
		fp = fopen(data->logFileName, "a");
    	if (fp == NULL)
    	{
    		printf("Error opening file\n");
    		exit(0);
    	}
		printf("Introduzir comando:\n");
		scanf(" %[^\n]", opcao);
		

		if (opcao[0]=='o' && opcao[1]=='p' && opcao[2]=='\0'){
			escreverFicheiroLog(fp,"op", -1);
			create_request(&op_counter_glb, buffers, data,sems);
		} else if (opcao[0]=='r' && opcao[1] == 'e' && opcao[2] == 'a' && opcao[3] == 'd'&& opcao[4]=='\0') {
			scanf(" %d", &op_read);
			escreverFicheiroLog(fp,"read", op_read);
			read_answer(data,sems);	
		} else if (opcao[0]=='s' && opcao[1] == 't' && opcao[2] == 'o' && opcao[3] == 'p' && opcao[4]=='\0'){
			escreverFicheiroLog(fp,"stop", -1);
			stop_execution(data, buffers,sems);	
			return;
		} else if (opcao[0]=='h' && opcao[1] == 'e' && opcao[2] == 'l' && opcao[3] == 'p' && opcao[4]=='\0'){
			printf("Available commands:\n	op - creates a new request\n	read x - attempts do read the response to request x\n	stop - stops the execution of this program.\n	help - prints these instructions.\n");
		} else {
			printf("Command not recognized, type 'help' for assistance.\n");
		}
	}
	while (1);
}

/* Se o limite de operações ainda não tiver sido atingido, cria uma nova
* operação identificada pelo valor atual de op_counter, escrevendo a mesma
* no buffer de memória partilhada entre main e clientes. Imprime o id da
* operação e incrementa o contador de operações op_counter.
*/
void create_request(int* op_counter, struct communication_buffers* buffers, struct main_data* data, struct semaphores* sems){
	struct operation op_request;
	timerAcoes(&op_request,0);
	if(op_counter_glb < data->max_ops){
		op_request.id = op_counter_glb;
		write_rnd_access_buffer(buffers->main_cli, data->buffers_size, &op_request);
		printf("operation %d created!\n", op_counter_glb);
		op_counter_glb++;
	} else {
		printf("Max ops has been reached!\n");
	}
}

/* Função que lê um id de operação do utilizador e verifica se a mesma
* é valida e se já foi respondida por um servidor. Em caso afirmativo,
* imprime informação da mesma, nomeadamente o seu estado, e os ids do 
* cliente, proxy e servidor que a processaram.
*/
void read_answer(struct main_data* data, struct semaphores* sems){
	if(op_read > data->max_ops || op_read < 0){
		printf("op id provided is invalid!\n");
	} else if(op_counter_glb < op_read){
		printf("op %d is not yet available!\n", op_read);
	} else {
		printf("op %d with status %c was received by client %d, forwarded by proxy %d, and served by server %d!\n"
		, op_read, data->results[op_read].status, data->results[op_read].client, data->results[op_read].proxy, data->results[op_read].server);
	}
}

/* Função que termina a execução do programa socps. Deve começar por 
* afetar a flag data->terminate com o valor 1. De seguida, e por esta
* ordem, deve acordar processos adormecidos, esperar que terminem a sua 
* execução, escrever as estatisticas finais do programa, e por fim libertar
* os semáforos e zonas de memória partilhada e dinâmica previamente 
*reservadas. Para tal, pode usar as outras funções auxiliares do main.h.
*/
void stop_execution(struct main_data* data, struct communication_buffers* buffers, struct semaphores* sems){
	int i, j, k;
	*data->terminate = 1;
	for(i = 0; i < data->n_clients; i++){
		data->client_stats[i] = launch_process(i, 0, buffers, data,sems);
	}

	for(j = 0; j < data->n_proxies; j++){
		data->proxy_stats[j] = launch_process(j, 1, buffers, data,sems);
	}

	for(k = 0; k < data->n_servers; k++){
		data->server_stats[k] = launch_process(k, 2, buffers, data,sems);
	}
	wait_processes(data);
	write_statistics(data);
	destroy_dynamic_memory_buffers(data);
	destroy_shared_memory_buffers(data, buffers);
}

/* Função que acorda todos os processos adormecidos em semáforos, para que
* estes percebam que foi dada ordem de terminação do programa. Para tal,
* pode ser usada a função produce_end sobre todos os conjuntos de semáforos
* onde possam estar processos adormecidos e um número de vezes igual ao 
* máximo de processos que possam lá estar.
*/

void wakeup_processes(struct main_data* data, struct semaphores* sems){

}


/* Função que espera que todos os processos previamente iniciados terminem,
* incluindo clientes, proxies e servidores. Para tal, pode usar a função 
* wait_process do process.h.
*/
void wait_processes(struct main_data* data){
	int i, j, k;
	//loop para criar operacoes clients
	for(i = 0; i < data->n_clients; i++){
		wait_process(data->client_pids[i]);
	}

	//loop para criar operacoes proxies
	for(j = 0; j < data->n_proxies; j++){
		wait_process(data->proxy_pids[j]);
	}

	//loop para criar operacoes servers
	for(k = 0; k < data->n_servers; k++){
		wait_process(data->server_pids[k]);
	}
	return;
}


/* Função que imprime as estatisticas finais do socps, nomeadamente quantas
* operações foram processadas por cada cliente, proxy e servidor.
*/
void write_statistics(struct main_data* data){
	int i, j, k;

	printf("Terminating scops! Printing Statistics:\n");
	for(i = 0; i < data->n_clients; i++){
		printf("Client %d received %d requests!\n", i, data->client_stats[i]);
	}

	for(j = 0; j < data->n_proxies; j++){
		printf("Proxy %d forwarded %d requests!\n", j, data->proxy_stats[j]);
	}

	for(k = 0; k < data->n_servers; k++){
		printf("Server %d answered %d requests!\n", k, data->server_stats[k]);
	}
}

/* Função que liberta todos os buffers de memória dinâmica previamente
* reservados na estrutura data.
*/
void destroy_dynamic_memory_buffers(struct main_data* data){
	destroy_dynamic_memory(data->client_pids);
	destroy_dynamic_memory(data->proxy_pids);
	destroy_dynamic_memory(data->server_pids);

	destroy_dynamic_memory(data->client_stats);
	destroy_dynamic_memory(data->proxy_stats);
	destroy_dynamic_memory(data->server_stats);
}


/* Função que liberta todos os buffers de memória partilhada previamente
* reservados nas estruturas data e buffers.
*/
void destroy_shared_memory_buffers(struct main_data* data, struct communication_buffers* buffers){
	destroy_shared_memory(STR_SHM_MAIN_CLI_PTR, buffers->main_cli->ptr, data->buffers_size);
	destroy_shared_memory(STR_SHM_MAIN_CLI_BUFFER, buffers->main_cli->buffer, data->buffers_size);

	destroy_shared_memory(STR_SHM_CLI_PRX_PTR, buffers->cli_prx->in, data->buffers_size);
	destroy_shared_memory(STR_SHM_CLI_PRX_BUFFER, buffers->cli_prx->buffer, data->buffers_size);

	destroy_shared_memory(STR_SHM_PRX_SRV_PTR, buffers->prx_srv->ptr, data->buffers_size);
	destroy_shared_memory(STR_SHM_PRX_SRV_BUFFER, buffers->prx_srv->buffer, data->buffers_size);

	destroy_shared_memory(STR_SHM_SRV_CLI_PTR, buffers->srv_cli->in, data->buffers_size);
	destroy_shared_memory(STR_SHM_SRV_CLI_BUFFER, buffers->srv_cli->buffer, data->buffers_size);

	destroy_shared_memory(STR_SHM_RESULTS, data->results, data->buffers_size);

	destroy_shared_memory(STR_SHM_TERMINATE, data->terminate, data->buffers_size);
}

int main(int argc, char *argv[]) {
	//init data structures 
	struct main_data* data = create_dynamic_memory(sizeof(struct main_data)); 
	struct communication_buffers* buffers = create_dynamic_memory(sizeof(struct communication_buffers));
	struct semaphores sems;
	
	buffers->main_cli = create_dynamic_memory(sizeof(struct rnd_access_buffer)); 
	buffers->cli_prx = create_dynamic_memory(sizeof(struct circular_buffer)); 
	buffers->prx_srv = create_dynamic_memory(sizeof(struct rnd_access_buffer)); 
	buffers->srv_cli = create_dynamic_memory(sizeof(struct circular_buffer)); 
 
	//execute main code 
	main_args(argc, argv, data);  
	create_dynamic_memory_buffers(data); 
	create_shared_memory_buffers(data, buffers); 
	launch_processes(buffers, data, &sems); 
	user_interaction(buffers, data, &sems); 
  
	//release final memory 
	destroy_dynamic_memory(data); 
	destroy_dynamic_memory(buffers->main_cli); 
	destroy_dynamic_memory(buffers->cli_prx); 
	destroy_dynamic_memory(buffers->prx_srv); 
	destroy_dynamic_memory(buffers->srv_cli); 
	destroy_dynamic_memory(buffers);
}

void destroy_semaphores(struct semaphores* sems){

}