#!/bin/bash

# for variable in list
# do statement
# done

echo "Number of arguments: $#"
i=1
for x in $*
do
	echo "Argument $1: $x"
	i=$((i+1))
done

for x in *
do
	echo $x
done

for x in `ls`
do
	echo $x
done

for x in {1..20}
do
	for ((y=1; y<=x;y++))
	do
		echo $y
	done
done

