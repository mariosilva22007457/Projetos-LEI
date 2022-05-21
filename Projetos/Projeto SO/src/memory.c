// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include <stdlib.h>
#include <stdio.h>
#include <sys/mman.h>
#include <sys/stat.h> 
#include <fcntl.h>
#include <unistd.h>
#include "memory-private.h"
#include "memory.h"

//???
// Nomes usados na criação de zonas de memoria partilhada
#define STR_SHM_MAIN_CLI_PTR 			"SHM_MAIN_CLI_PTR"
#define STR_SHM_MAIN_CLI_BUFFER 		"SHM_MAIN_CLI_BUFFER"
#define STR_SHM_CLI_PRX_PTR 			"SHM_CLI_PRX_PTR"
#define STR_SHM_CLI_PRX_BUFFER 			"SHM_CLI_PRX_BUFFER"
#define STR_SHM_PRX_SRV_PTR 			"SHM_PRX_SRV_PTR"
#define STR_SHM_PRX_SRV_BUFFER 			"SHM_PRX_SRV_BUFFER"
#define STR_SHM_SRV_CLI_PTR 			"SHM_SRV_CLI_PTR"
#define STR_SHM_SRV_CLI_BUFFER			"SHM_SRV_CLI_BUFFER"
#define STR_SHM_RESULTS					"SHM_RESULTS"
#define STR_SHM_TERMINATE				"SHM_TERMINATE"

/* Função que reserva uma zona de memória partilhada com tamanho indicado
* por size e nome name, preenche essa zona de memória com o valor 0, e 
* retorna um apontador para a mesma. Pode concatenar o resultado da função
* getuid() a name, para tornar o nome único para o processo.
*/
void* create_shared_memory(char* name, int size){
	int *ptr;  
	int i;
	int ret;  
	int fd = shm_open(name, O_RDWR | O_CREAT, S_IRUSR | S_IWUSR);  

	if (fd == -1){      
   		perror("shm");      
   		exit(1);  
	} 
 
	ret = ftruncate(fd, size);  
	if (ret == -1){      
   		perror("shm");      
   		exit(2);  
	} 

	ptr = mmap(0, size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);  
	if (ptr == MAP_FAILED){      
   		perror("shm-mmap");      
   		exit(3);  
	} 
	for (i = 0; i < size; i++){
		ptr[i] = 0;
	}
	
	return ptr;
}


/* Função que reserva uma zona de memória dinâmica com tamanho indicado
* por size, preenche essa zona de memória com o valor 0, e retorna um 
* apontador para a mesma.
*/
void* create_dynamic_memory(int size){
	int* memory;
	int i;
	memory = malloc(size);
	*memory = 0;

	return (void*) memory;
}


/* Função que liberta uma zona de memória dinâmica previamente reservada.
*/
void destroy_shared_memory(char* name, void* ptr, int size){
	shm_unlink(name);
	munmap(ptr,size);
}


/* Função que liberta uma zona de memória partilhada previamente reservada.
*/
void destroy_dynamic_memory(void* ptr){
	free(ptr);
}


/* Função que escreve uma operação num buffer de acesso aleatório. A
* operação deve ser escrita numa posição livre do buffer, segundo as
* regras de escrita em buffers de acesso aleatório. Se não houver nenhuma
* posição livre, não escreve nada.
*/
void write_rnd_access_buffer(struct rnd_access_buffer* buffer, int buffer_size, struct operation* op){
	int i;
	for (i = 0; i < buffer_size; i++){
		if (buffer->ptr[i] == 0){
			buffer->buffer[i] = *op;
			buffer->ptr[i] = 1;
			break;
		}		
	}
}


/* Função que escreve uma operação num buffer circular. A operação deve 
* ser escrita numa posição livre do buffer, segundo as regras de escrita
* em buffers circulares. Se não houver nenhuma posição livre, não escreve
* nada.
*/
void write_circular_buffer(struct circular_buffer* buffer, int buffer_size, struct operation* op)
{
	if (((*buffer->in + 1) % buffer_size) == *buffer->in){
		return;
	}
	buffer->buffer[*buffer->in] = *op;
	*buffer->in = (*buffer->in + 1) % buffer_size;
}


/* Função que lê uma operação de um buffer de acesso aleatório, se houver
* alguma disponível para ler. A leitura deve ser feita segundo as regras
* de leitura em buffers acesso aleatório. Se não houver nenhuma operação
* disponível, afeta op com o valor -1.
*/
void read_rnd_access_buffer(struct rnd_access_buffer* buffer, int buffer_size, struct operation* op){
	int i;
	for (i = 0; i < buffer_size; i++){
		if (buffer->ptr[i]==1){
			buffer->ptr[i]=0;
			*op = buffer->buffer[i];
			return;
		}
	}
	op->id = -1;
}


/* Função que lê uma operação de um buffer circular, se houver alguma 
* disponível para ler. A leitura deve ser feita segundo as regras de
* leitura em buffers circular. Se não houver nenhuma operação disponível,
* afeta op->id com o valor -1.
*/
void read_circular_buffer(struct circular_buffer* buffer, int buffer_size, struct operation* op){
	if (*buffer->in == buffer->out){ // nao ha nada para ler
		op->id=-1;
		return;
	}
	*op = buffer->buffer[buffer->out];
	buffer->out = (buffer->out + 1) % buffer_size;
}