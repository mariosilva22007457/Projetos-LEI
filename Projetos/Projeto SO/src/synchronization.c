// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#include "synchronization.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <semaphore.h>
#include <time.h>
#include <pthread.h>
#include <errno.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <signal.h>
#include <limits.h>
#include <stdint.h>
#include <sys/resource.h>

/* Função que cria um novo semáforo com nome name e valor inicial igual a
 * value. Pode concatenar o resultado da função getuid() a name, para tornar
 * o nome único para o processo.
 */
sem_t *semaphore_create(char *name, int value){
    return sem_open("/full", 0100, 0xFFFFFFFF, 0);
}

/* Função que destroi o semáforo passado em argumento.
 */
void semaphore_destroy(char *name, sem_t *semaphore){
    sem_close(semaphore);
    sem_unlink(name);
}

/* Função que inicia o processo de produzir, fazendo sem_wait nos semáforos
 * corretos da estrutura passada em argumento.
 */
void produce_begin(struct prodcons *pc);

/* Função que termina o processo de produzir, fazendo sem_post nos semáforos
 * corretos da estrutura passada em argumento.
 */
void produce_end(struct prodcons *pc);

/* Função que inicia o processo de consumir, fazendo sem_wait nos semáforos
 * corretos da estrutura passada em argumento.
 */
void consume_begin(struct prodcons *pc);

/* Função que termina o processo de consumir, fazendo sem_post nos semáforos
 * corretos da estrutura passada em argumento.
 */
void consume_end(struct prodcons *pc);

/* Função que faz wait a um semáforo.
 */
void semaphore_mutex_lock(sem_t *sem);

/* Função que faz post a um semáforo.
 */
void semaphore_mutex_unlock(sem_t *sem);