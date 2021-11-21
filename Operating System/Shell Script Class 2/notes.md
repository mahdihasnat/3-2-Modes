Why needed?
- to automate daily life

Shell script extension is .sh

Grant persmission to execute script: `chmod +x script.sh`

```

`var1=1+3 echo $var1`

`1+3`

var1="Hello" 

echo "$var1" [Hello]

echo '$var1' [$var1]

echo $var1 [Hello]

echo \$var1 [$var1]

echo expr 1+1 [expr 1+1]

echo \`expr 1+1\` [2]

((y=1+1))
echo $y [2]

expr always works with integer

```

```
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
```

## SELF Study
- Array