/*this code finds all of the amicable numbers under 10000 and adds them together. 
Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are called amicable numbers.

For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.
*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define MAX 10000

int addDivisors(int number);

long long totalSum = 0;

int main(){

	//comparison code. adds amicable numbers to total.
	int currentNumber;
	int currentSum;
	for(int i = 0; i < MAX; i++){
		currentNumber = i;
		currentSum = addDivisors(i);
		if((addDivisors(currentSum) == currentNumber) && (currentSum != currentNumber)){
			totalSum += currentNumber;
			printf("%d and %d are amicable numbers. Total is %d \n",currentNumber,currentSum,totalSum);
		}
	}

	
}
//functions
int addDivisors(int number){
	int tempSum = 0;
	for(int i = 1; i <= number / 2 + 1; i++){
		if(number % i == 0){
			//printf("adding %d to %d\n", i,tempSum);
			tempSum += i;                                                               
		}
	}
	return tempSum;
}