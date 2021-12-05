#!/bin/bash

declare -A totalNum

add_num(){
	# 1 -> sid
	# 2 -> num
	if [ -z "${totalNum["$1"]}" ];
	then
		totalNum["$1"]=0
	fi 
	totalNum["$1"]=$((totalNum["$1"] + $2))
	echo "sid: $1 , num $2 , tot: ${totalNum["$1"]} "
}
marks=0
get_marks(){
	# 1 -> sid
	# 2 -> sub
	# echo "sid:$1 sub: $2"
	line=`grep "$1" "$2.txt"`
	
	if [ -z "$line" ];
	then
		marks=0
	else 
		# echo "line:$line"
		{
			read -d ' ' sid
			read -d ' ' marks
			
		} <<< $line
	fi
	# echo "marks:$marks"
}
grade=0
get_grade(){
	# 1 -> avg
	if (( $1 >= 80 ));
	then
		grade='A'
	elif (($1 >= 60));
	then
		grade='B'
	elif (( $1 >=  40));
	then
		grade='C'
	else 
		grade='F'
	fi
}

courses=`cat "course.txt"`

# echo "${courses}"

courseList=( )
totalCourse=0

IFS=$'\n'
while read -r course 
do
	courseList+=( "$course" )
	echo "courseList: $courseList"
	echo "c:${course}"
	totalCourse=$((totalCourse+1))
	fileName=${course}".txt"
	echo "fileName: $fileName"
	contents=`cat "$fileName"`
	
	while read line
	do
		# echo "sid,num: $line"
		{
			read -d ' ' sid  
			read -d ' ' num 
		}<<< "$line"
		# echo "sid: $sid , num: $num"
		add_num "$sid" "$num"
	done <<< "$contents"

done <<< "${courses}"

echo "totalCourse : $totalCourse"

tmp=$(tr '\n' ',' <<< "${courses}")
IFS=','
echo "sid,${tmp}total,avg,grade" > out.csv

unset IFS

for sid in ${!totalNum[@]}
do
	
	avg=$((totalNum[$sid] / totalCourse))
	echo "sid:$sid , avg: $avg"
	csvLine="$sid"
	IFS=$'\n'
	while read course
	do
		get_marks "$sid" "$course"
		csvLine="${csvLine},$marks"
	done <<< "$courses"
	unset IFS
	echo "$csvLine"
	get_grade "$avg"
	csvLine="${csvLine},${totalNum[$sid]},$avg,$grade" 
	echo "$csvLine" >> out.csv
done