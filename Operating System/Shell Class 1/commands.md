Command tools: 

cd - change directory
pwd - print working directory
cd ~ - change directory to home directory
cd .. - change directory to parent directory
cd - change directory to home directory
cd - - change directory to previous directory

ls - list files
hidden file - file name starting with a dot
ls -a - list all files including hidden files
ls -l - list files in long format
ls -lh - list files in long format with human readable sizes
man - manual page
mkdir - makes directory
cp - copy | cp SOURCE DEST if source is folder then -r must be used
rm - abdolute delate , no recycle bin
mv - move, used for rename , may used for moving files to another folder
pushd - push directory
popd - pop directory
clear - clear screen , or Ctrl+L
history - history of commands
!HISTORY_NUMBER - run command from history

sort - sort files
uniq - remove duplicate lines
commands started with space is not incuded in history


Permissions :
Linux is a multi-user operating system.
A user can own files and directories.When a user owns a file or directory, the user has control over its access.
Users can, in turn, belong to a group condisting

chmod - owner or superuser can change permissions
	  - symbolic notation : [a=all , u=user , g=group , o=others] [+ - ] [r=read , w=write , x=execute]
	  - octal notation : [0-7] rwx = owner  rwx = group  rwx= others
su [ user ] - switch user
sudo - super user do something


File Viewing

more - display file content in pages
less - display file content in pages
head - display first 10 lines
tail - display last 10 lines
cat - concatenate files
wc - word count
grep - search for a pattern in a file
	- PATTERN [FILE..]
find - 
diff - compare files



I/O Redirection

< - get input from a file 
> - send output to a file
>> - append output to a file

Pipelines
| - pipe
