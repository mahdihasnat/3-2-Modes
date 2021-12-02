#!/bin/bash

usage_message="$0 input_file_name [dir_name]"

declare -A forbidden_extension

read_extension(){
    # $1 -> input file name
    lines=`cat $1`
    for line in $lines;
    do
        forbidden_extension["$line"]=1
    done < "$1"
    echo "forbidden_ext ${!forbidden_extension[@]}"
}

if (( $#  == 0 || $# > 2 ));
then
    echo "${usage_message}"
else
    echo "fine"
    if [ -f $1 ];
    then
        echo "fine1"
        read_extension $1
        echo "ext: ${!forbidden_extension[@]}"
        

    else
        echo "First argument is not a file!"
        echo "${usage_message}"
    fi
fi
