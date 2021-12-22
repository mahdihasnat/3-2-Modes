CC = g++ -std=c++2a

# compile every cpp file in this directory with g++ , and target

# the object file in the same directory
%.o : %.cpp %.h
	${CC} -c $< -o $@

clean :
	rm -f *.o
	rm -f *.out

.PHONY : clean

a.out : main.o passenger.o kiosk.o logger.o Semaphore.o
	${CC} -pthread *.o -o a.out

# run the executable
run : a.out
	./a.out

# compile and run
all : run

# compile and debug memory-check
memory : a.out
	valgrind --leak-check=full -s ./a.out
# compile and run
.PHONY : run

# compile and run
.PHONY : all

# compile and run
.PHONY : clean

.PHONY : run

