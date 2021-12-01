print_in_level(){
	# $1 is file name
	# $2 is indentation level
	for(( i=1; i <= $2; i++))
	do
		echo -n "| "
	done
	echo "|-$1"
}

print_directory(){
	# $1 is the directory to print
	# $2 is the indentation level
	# echo "prind dir \$# $#"
	cd "$1"
	for f in *
	do
		
		if [[ -d "$f" ]] ; then
			#echo "folder found"
			x=$(expr $2 + 1)
			
			print_in_level "$f" $2
			print_directory "$f" $x

		else
			#echo "file found"
			print_in_level "$f" $2
		fi
		
	done
	cd ../
}

# echo "Total arguments:$#"
# echo "\$1  = $1"

print_directory "$1" 0