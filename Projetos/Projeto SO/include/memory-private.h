// Grupo S06 (Grupo 6) composto por:
// José Sobral a22005813
// Alexandre Costa a22007578
// Mário Silva a22007457

#ifndef MEMORY_PRIVATE_H_GUARD
#define MEMORY_PRIVATE_H_GUARD


//estrutura que representa um buffer circular, completar pelos alunos
struct circular_buffer { 	
    struct operation* buffer;
    int* in;
    int out;
};

//estrutura que representa um buffer de acesso aleatório, completar pelos alunos
struct rnd_access_buffer { 		
    int* ptr;
    struct operation* buffer;
};

#endif
