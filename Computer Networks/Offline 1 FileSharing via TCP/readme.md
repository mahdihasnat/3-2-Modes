
showstudents - to show all student 
showmyfiles - to show my public and private files
showallfiles - to show all public files
showfiles SID - to show all public files of specific students

Protocol for file Upload from client:

| Server   |   Client |
|----------|----------|
|          | upload filename filesize|
|check for buffer availability|      |
|send fileId  and chunk size|           |
|          | split first chunk |
| send ack|                 |
|           | if ack dont arrive within 30 sec then send timeout, otherwise send next chunk|
| if receive timeout then abort transmission|  |
| send ack of last chunk |              |
|               | send completion |
|check file integrity by summing total length of reeived byte| |
| send success or failure| |
| | report siuccess or failure|


