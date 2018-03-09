#include <stdlib.h>
#include <stdio.h>

int main()
{
    char c;
    int numLines = 0;
    while((c = getchar()) != EOF)
    {

        if(c == '\n')
        {
            numLines++;
            //printf("increment num lines to %d\n",numLines);
            if(numLines % 9 == 0)
            {
                printf("\n");
            }
        }
        else if(c == '0')
        {
            printf(".");
        }
        else
        {
            printf("%c",c);
        }
    }
}