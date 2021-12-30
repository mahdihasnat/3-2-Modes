CC = g++
PARAMETERS = input.txt
# compile every cpp file in this directory with g++ , and target

# the object file in the same directory
%.o : %.cpp %.h
	${CC} -c $< -o $@

clean :
	rm -f *.o
	rm -f *.out

.PHONY : clean

a.out : main.o passenger.o kiosk.o logger.o Semaphore.o \
vip_channel.o SecurityBelt.o security.o Sleep.o boarding.o Queue.o timer.o
	${CC} -pthread *.o -o a.out

# run the executable
run : a.out
	./a.out ${PARAMETERS}

# compile and run
all : run

time : timer.o
	g++ -o time timer.o
	./time	

# compile and debug memory-check
memory : a.out
	valgrind --leak-check=full -s ./a.out input.txt
# compile and run
.PHONY : run

# compile and run
.PHONY : all

# compile and run
.PHONY : clean

.PHONY : run

