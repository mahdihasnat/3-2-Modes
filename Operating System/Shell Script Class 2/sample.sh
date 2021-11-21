#!/bin/bash

echo "Hello World"
echo "is it morning?"
read answer
case $answer in
	[yY] | [yY][eE][sS])
		echo "Yes, Good morning"
		;;
	[nN] | [nN][oO])
		echo "No, Good afternoon"
		;;
	*)
		echo "Sorry, I don't understand"
		;;
esac