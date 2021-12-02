#!/bin/bash

usage_message="usage:\"$0 input_file_name [dir_name]\""

declare -A forbidden_extensions

read_extension(){
    # $1 -> input file name
    lines=`cat "$1"`
    IFS=" \n\r\t"
    while read line;
    do
        forbidden_extensions["$line"]=1
    done <<< "$lines"
    echo "forbidden_ext ${!forbidden_extensions[@]}"
}

is_allowed_extension(){
    # $1 is the extension to check

    if [ -n "${forbidden_extensions["$1"]}" ];
    then
        return 1
    else
        return 0
    fi
}

# check for proper command line arguments
if (( $#  == 0 || $# > 2 ));
then
    echo "${usage_message}"
    exit 1
fi

echo "fine"

# check if input file exist
if [ ! -f "$1" ];
then
    echo "First argument is not a file!"
    echo "${usage_message}"
    exit 1
fi

echo "fine1"

# check if input file is readable
if [ ! -r "$1" ];
then
    echo "Read Permission not granted for: $1"
    exit 1
fi

# read forbidden_extensionss 
read_extension "$1"
echo "ext: ${!forbidden_extensions[@]}"

# define working_dir
if (( $# >=2 ));
then
    working_dir="$2"
else
    working_dir="."
fi


echo "wdir:${working_dir}"

# check if working_dir exists
if [ ! -d "${working_dir}" ];
then
    echo "Working directory is invalid"
    exit 1
fi 

