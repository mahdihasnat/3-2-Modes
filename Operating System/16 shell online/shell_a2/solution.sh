#!/bin/bash

dir="./"

files=$(find "$dir" -regex ".*\.pdf")

declare -a arr
while read -r file
do 
	# echo "file:$file"
	fileSize=$(stat -c "%s" "$file")
	arr+=( "$fileSize -$file" )
	
done <<< "$files" 

echo "${arr[*]}"
IFS=$'\n' sorted=($(sort -n <<< "${arr[*]}"));

mkdir "./out/"
n=1
for x in ${sorted[*]}
do
	fileName="${x#*-}"
	echo "f:${fileName}"
	cp "$fileName" "./out/$n.pdf"
	n=$((n+1))
done