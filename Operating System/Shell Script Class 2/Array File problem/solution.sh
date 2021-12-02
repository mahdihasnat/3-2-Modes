#!/bin/bash

filename="$1"
n=0
while read line ;
do
	echo "line no: $((n+1)) ,line: $line"
	n=$((n+1))
	ARRAY[$n]="$line"
done < "$filename"
echo ${ARRAY[@]}

found=0
while (($found == 0));
do
	read pass
	for i in ${!ARRAY[@]}
	do
		if [[ "${ARRAY[$i]}" = "${pass}" ]];
		then
			index=$i;
			found=1
			break
		fi 
	done
	if (($found == 0));
	then
		echo "Try again!"
	fi
done 
echo "pass matched at index :$index"

echo "enter new password:"
read pass

ARRAY[$i]=$pass

echo ${ARRAY[*]}

s=""
for val in ${ARRAY[*]};
do
	if [[ $s = "" ]];
	then
		s=$val
	else 
	s="$s
$val"
	fi
	
done

echo "$s" > "$filename"