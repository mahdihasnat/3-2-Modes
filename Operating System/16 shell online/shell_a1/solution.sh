#!/bin/bash

declare -A places

add_user(){
	# $1 = place
	if [ -z "${places["$1"]}" ];
	then 
		places["$1"]=0
	fi
	places["$1"]=$((${places["$1"]}+1))
}

csv=$(cat "visited.csv")
echo $csv
# for line in $csv
while read -r line 
do
	echo "line: $line"
	{
		read -d ',' u
		echo "U:$u"
		while read -d ',' p
		do 
			echo "p:$p"
			if [ -n "$p" ];
			then
				add_user "$p"
			fi
		done 
	} <<< "$line"
done <<< "$csv"

for place in "${!places[@]}"
do

	echo "${places["$place"]}-$place"
done | sort -n | 
{
	read line
	echo "${line#*-}"
}

for place in "${!places[@]}" 
do 
	echo "$place ${places[$place]}" >> "my_visitor_count.txt"
done 