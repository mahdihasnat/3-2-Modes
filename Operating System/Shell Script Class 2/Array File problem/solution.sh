#!/bin/bash

filename="$1"
n=0
while read line ;
do
	echo "line no: $((n+1)) ,line: $line"
	n=$((n+1))
	ARRAY[$n]="$line"
done < "$filename"
echo ${!ARRAY[@]}

found=0
while (($found == 0));
do
	read pass
	for i in ${!ARRAY[@]}
	do
		echo ${ARRAY[$i]}
		echo $pass
		if [[ "${ARRAY[$i]}" = "${pass}" ]];
		then
			index=$i;
			found=1
			break
		fi 
	done
done 
echo "pass matched at index :$index"