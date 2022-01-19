#include "types.h"
#include "stat.h"
#include "user.h"

static void
putc(int fd, char c)
{
  write(fd, &c, 1);
}

static void
printint(int fd, int xx, int base, int sgn)
{
  static char digits[] = "0123456789ABCDEF";
  char buf[16];
  int i, neg;
  uint x;

  neg = 0;
  if(sgn && xx < 0){
    neg = 1;
    x = -xx;
  } else {
    x = xx;
  }

  i = 0;
  do{
    buf[i++] = digits[x % base];
  }while((x /= base) != 0);
  if(neg)
    buf[i++] = '-';

  while(--i >= 0)
    putc(fd, buf[i]);
}

union 
{
  double fraction;
  int integerparts[2];
}Fraction;

static void
printfloat(int fd, float f)
{
  int integar = (int)f;
  int frac = ((int)(f*10000)) - ((int)(integar*10000));
  printint(fd, integar, 10, 1);
  putc(fd, '.');
  if(frac<1000) putc(fd, '0');
  if(frac<100) putc(fd, '0');
  if(frac<10) putc(fd, '0');
  printint(fd, frac, 10, 1);
}

// Print to the given fd. Only understands %d, %x, %p, %s, %f.
void
printf(int fd, const char *fmt, ...)
{
  char *s;
  int c, i, state;
  uint *ap;

  state = 0;
  ap = (uint*)(void*)&fmt + 1;
  for(i = 0; fmt[i]; i++){
    c = fmt[i] & 0xff;
    if(state == 0){
      if(c == '%'){
        state = '%';
      } else {
        putc(fd, c);
      }
    } else if(state == '%'){
      if(c == 'd'){
        printint(fd, *ap, 10, 1);
        ap++;
      } else if(c == 'x' || c == 'p'){
        printint(fd, *ap, 16, 0);
        ap++;
      } else if(c == 's'){
        s = (char*)*ap;
        ap++;
        if(s == 0)
          s = "(null)";
        while(*s != 0){
          putc(fd, *s);
          s++;
        }
      } else if(c == 'c'){
        putc(fd, *ap);
        ap++;
      } else if(c == '%'){
        putc(fd, c);
        } else if(c == 'f'){

        // printf(fd,"\n>*ap size %d\n",sizeof(*ap));
        // printf(fd,"\n>ap size %d \n",sizeof(ap));
        // printf(fd,"\n>ap = %d \n",ap);
        // printint(fd, *ap, 10, 0);
        Fraction.integerparts[0]=*ap;
        ap++;
        Fraction.integerparts[1]=*ap;
        ap++;
        printfloat(fd, Fraction.fraction);

      } else {
        // Unknown % sequence.  Print it to draw attention.
        putc(fd, '%');
        putc(fd, c);
      }
      state = 0;
    }
  }
}
