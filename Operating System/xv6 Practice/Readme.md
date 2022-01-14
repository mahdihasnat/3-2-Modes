`sudo apt install gcc make gdb qemu`

`sudo apt install qemu`

`sudo apt install qemu-system-x86`

`make`

`make qemu`

`make qemy-nox`

## Steps to add user program
- Create .c file
- Add to makefile
- make 
- make qemu-nox

## Steps to add system call
- Add extern declaration in syscall.c
- Add SYS_xyz to syscall.h
- Add interrupt defination to SYSCALL to usys.S
- Add declaration to user.h
- Add implementation to sysproc.c
- Use system call in user program by including user.h

## printf
- include types.h
- include user.h



# ChangeLog
- Added user program named `userp`
- Added user program named `shutdown` - does shutdown qemu by printing acpi commands


