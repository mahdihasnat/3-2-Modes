CC = g++

# compile every cpp file in this directory with g++ , and target

# the object file in the same directory
%.o : %.cpp %.h
	${CC} -c $< -o $@

clean :
	rm -f *.o
	rm -f *.out

.PHONY : clean

a.out :
	${CC} *.o -o a.out

# run the executable
run : main.o passenger.o a.out
	./a.out

# compile and run
all : run

# compile and run
.PHONY : run

# compile and run
.PHONY : all

# compile and run
.PHONY : clean

.PHONY : run

