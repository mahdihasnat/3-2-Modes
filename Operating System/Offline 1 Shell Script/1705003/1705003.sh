#!/bin/bash

usage_message="usage:\"$0 input_file_name [dir_name]\""

declare -A forbidden_extensions

read_extension(){
    # $1 -> input file name
    lines=`cat "$1"`
    while read -r line;
    do
        # echo "line#$line"
        forbidden_extensions["$line"]=1
    done <<< "$lines"
    # echo "forbidden_ext ${!forbidden_extensions[@]}"
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


set_output_dir(){
    # $1 = working_dir
    output_dir="$working_dir""/../output_dir/"

    # check if output_dir already exist
    if [ ! -d "$output_dir" ];
    then
        mkdir "$output_dir"
        return 0
    fi

    n=1
    output_dir="$working_dir""/../output_dir($n)/"
    while [ -d "$output_dir" ];
    do
        n=$((n+1))
        output_dir="$working_dir""/../output_dir($n)/"
    done

    mkdir "${output_dir}"

}


# check for proper command line arguments
if (( $#  == 0 || $# > 2 ));
then
    echo "${usage_message}"
    exit 1
fi

echo "Total arguments are $# : Ok"

# check if input file exist
if [ ! -f "$1" ];
then
    echo "First argument is not a file!"
    echo "${usage_message}"
    exit 1
fi

echo "First arguments is file : Ok"

# check if input file is readable
if [ ! -r "$1" ];
then
    echo "Read Permission not granted for: $1"
    exit 1
fi

echo "Read Persmission is granted for file : Ok"

# read forbidden_extensionss 
read_extension "$1"
echo "Forbidden Extensions are: ${!forbidden_extensions[@]}"

# define working_dir
if (( $# >=2 ));
then
    working_dir="$2"
else
    working_dir="."
fi


echo "Working Directory is: ${working_dir}"

# check if working_dir exists
if [ ! -d "${working_dir}" ];
then
    echo "Working directory is invalid"
    exit 1
fi 

echo "Working Directory is valid : Ok"

# set output_dir
set_output_dir "${working_dir}"
echo "Output directory is: $output_dir"


declare -A files # stores all file names
declare -A totalFiles # stores totalFiles key as extension

increment_totalFiles(){
    # $1 -> extension

    if [ -z "${totalFiles["$1"]}" ];
    then
        totalFiles["$1"]=0
    fi
    tmp="${totalFiles["$1"]}"
    totalFiles["$1"]=$(($tmp + 1))
    # echo "tmp:$tmp ${totalFiles["$1"]}"
    # echo "${!totalFiles[*]}"
}

add_file_to_output_dir(){
    # $1 -> filePath
    # $2 -> name
    # $3 -> extension

    # echo "fileapth: $1 fileName: $2 fileExt: $3"

    if [ -z "${files["$2"]}" ];
    then
        files["$2"]=1

        targetDir="${output_dir}/$3"
        target="$targetDir/$2"
        targetDescription="$targetDir/desc_$3.txt"
        # echo "target: $target"

        # create target dir
        if [ ! -d "$targetDir" ];
        then
            mkdir -p "$targetDir"
            touch "$targetDescription"
        fi

        cp -p "$1" "$target" # copy file
        echo "$1" >> "$targetDescription" # append output to description file
        increment_totalFiles "$3" # increment extension count

    fi

}
write_csv(){
    csvFileLocation="${output_dir}/output.csv"

    touch "${csvFileLocation}"
    echo "file_type,no_of_files" >> "${csvFileLocation}"
    for extension in ${!totalFiles[@]};
    do
        echo "${extension},${totalFiles[$extension]}" >> "${csvFileLocation}"
    done
}


find "${working_dir}" -type f -print0 |
{
    while IFS= read -r -d '' filePath;
    do 
        # echo "${filePath}"
        fileName="${filePath##*/}"
        fileExtension="${fileName##*.}"
        # echo "fileName:$fileName fileExtension:$fileExtension"
        

        if [ "$fileName" != "$fileExtension" ];
        then
            # echo "ext found"
            if is_allowed_extension "$fileExtension" ;
            then
                add_file_to_output_dir "$filePath" "$fileName" "$fileExtension"
            else 
                increment_totalFiles "ignored"
            fi
        else
            add_file_to_output_dir "$filePath" "$fileName" "others"
        fi
    done

    echo "Files copied to Output Directory : Ok"
    # echo "totalFiles: ${totalFiles[*]}"
    # echo "files: ${files}"

    write_csv
    echo "Statistics are written to $csvFileLocation : Ok"
}


