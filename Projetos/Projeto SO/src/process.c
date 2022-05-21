// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 
#include <sys/types.h>
#include <sys/wait.h>
#include "process.h"
#include "memory.h"
#include "client.h"
#include "proxy.h"
#include "server.h"

/* Função que inicia um processo através da função fork do SO. O novo
* processo pode ser um cliente, proxy, ou servidor, consoante a variável
* process_code tem valor 0, 1, ou 2 respectivamente, e irá executar a função
* execute_* respetiva, fazendo exit do retorno. O processo pai devolve o pid
* do processo criado.
*/
int launch_process(int process_id, int process_code, struct communication_buffers* buffers, struct main_data* data, struct semaphores* sems){
    int pid = fork();
    int exe_cli, exe_prx, exe_srv;

    if(pid == -1){
        exit(0);
    } else if(pid == 0) {
        switch (process_code)
        {
        case 0:
            exe_cli = execute_client(process_id, buffers, data,sems);
            exit(exe_cli);
        case 1:
            exe_prx = execute_proxy(process_id, buffers, data,sems);
            exit(exe_prx);
        case 2:
            exe_srv = execute_server(process_id, buffers, data,sems);
            exit(exe_srv);
        default:
            break;
        }
    } else {
        return pid;
    }
}

/* Função que espera que um processo termine através da função waitpid. 
* Devolve o retorno do processo, se este tiver terminado normalmente.
*/
int wait_process(int process_id){
    int status;
    waitpid(process_id, &status, 0);
    return process_id;
}