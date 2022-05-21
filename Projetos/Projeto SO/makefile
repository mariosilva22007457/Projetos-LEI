# Grupo S06 (Grupo 6) composto por:
# José Sobral a22005813
# Alexandre Costa a22007578
# Mário Silva a22007457 

OBJECTS = obj/main.o obj/client.o obj/memory.o obj/process.o obj/proxy.o obj/server.o obj/synchronization.o obj/configuration.o obj/sotime.o obj/log.o

sovac: $(OBJECTS)
	gcc $(OBJECTS) -o bin/sovac -lrt -lpthread

obj/main.o: src/main.c include/memory.h
	gcc -c src/main.c -I include -o obj/main.o

obj/client.o: src/client.c include/main.h include/memory.h
	gcc -c src/client.c -I include -o obj/client.o

obj/memory.o: src/memory.c include/memory-private.h
	gcc -c src/memory.c -I include -o obj/memory.o

obj/process.o:src/process.c include/memory.h include/main.h
	gcc -c src/process.c -I include -o obj/process.o

obj/proxy.o: src/proxy.c include/memory.h include/main.h
	gcc -c src/proxy.c -I include -o obj/proxy.o

obj/server.o: src/server.c include/memory.h include/main.h
	gcc -c src/server.c -I include -o obj/server.o

obj/synchronization.o: src/synchronization.c include/synchronization.h 
	gcc -c src/synchronization.c -I include -o obj/synchronization.o

obj/configuration.o: src/configuration.c include/configuration.h
	gcc -c src/configuration.c -I include -o obj/configuration.o

obj/sotime.o: src/sotime.c include/sotime.h
	gcc -c src/sotime.c -I include -o obj/sotime.o

obj/log.o: src/log.c include/log.h
	gcc -c src/log.c -I include -o obj/log.o
	
clean:
	rm -f obj/*.o out
	rm -f out