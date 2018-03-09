/*This program solves a sequence of input sudoku boards by using the simple, but effective
 *method of backtracking*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define BLANK '.'
#define ROWS 9
#define COLS 9
#define TRUE 1
#define FALSE 0

int isValidMove(char board[ROWS][COLS], int row,int col, char c);
int isValidBoard(char board[9][9]);
void prettyPrint(char grid[]);
int readLine(char grid[82]);
int hasRepeats(char* array, int array_size, char number);
void toGrid(char grid[82],char board[ROWS][COLS]);
void gridTo2DArray(char grid[82],char board[9][9]);
int squareUnitChecker(int row,int col,char board[9][9]);
int offset(int row);
void possibleMovesAtLocation(int row, int col, char board[ROWS][COLS],char (*possibleMoves)[10]);
int solveBoard(char board[ROWS][COLS],int row, int col);

char listBoard[81];
char currentBoard[ROWS][COLS];
unsigned long total;

/*the main method reads a puzzle individually in from stdin
 *and tries to solve it if it's valid. Otherwise, it prints an error message.
 *if there's no solution, No solution is printed.
 */
int main()
{
  char puzzleString[82];
  char c;
  int resultFromReadLine;
  char puzzleBoard[ROWS][COLS];

  while((c = getchar()) != EOF)
  {
    ungetc(c,stdin);
    resultFromReadLine = readLine(puzzleString);
    gridTo2DArray(puzzleString,puzzleBoard);
    if(resultFromReadLine && isValidBoard(puzzleBoard))
    {
      if(solveBoard(puzzleBoard,0,0))
      {
        toGrid(puzzleString,puzzleBoard);
        printf("solution:\n");
        prettyPrint(puzzleString);
        char first3[4];
        first3[0] = puzzleBoard[0][0];
        first3[1] = puzzleBoard[0][1];
        first3[2] = puzzleBoard[0][2];
        first3[3] = '\0';
        char *ptr;
        unsigned long num = strtoul(first3,&ptr,10);
        printf("num is %lu\n",num);
        total += num;
        printf("total is %lu\n",total);
      }
      else
      {
        printf("No solution\n\n");
      }
    }
    else
    {
      printf("Error\n\n");
    }
  }
  printf("%lu\n",total);
  return 0;
}

/* determines if a character c at index  row, col is valid, given the 2D Array board*/
int isValidMove(char board[ROWS][COLS], int row,int col, char c)
{
  char possibleMoves[10];
  char (*possibleMovesPtr)[10];
  int indexOfChar;

  possibleMovesPtr = &possibleMoves;
  indexOfChar = c - 49;

  possibleMovesAtLocation(row,col,board,possibleMovesPtr);
  if(possibleMoves[indexOfChar] != 'x')
  {
    return TRUE;
  }
  return FALSE;
}

/* 
 * determines if the given grid is valid by checking all rows, columns, and square units
 * for repeated characters.
 */
int isValidBoard(char board[9][9])
{
  int row;
  int col;
  char charDigit;
  char unitValues[10];
  unitValues[9] = '\0';

  /*these nested for loops determine if the rows of the board fit the rules for sudoku.*/
  for(row = 0; row < 9; row++)
  {
    for(col = 0; col < 9; col++)
    {
      unitValues[col] = board[row][col];
    }   
    for(charDigit = '1'; charDigit <= '9'; ++charDigit)
    {
      if(hasRepeats(unitValues,9,charDigit))
      {
        return FALSE;
      }
    }
  }

  /*these nested for loops determine if the columns of the board are valid*/
  for(col = 0; col < COLS; col++)
  {
    for(row = 0; row < ROWS; row++)
    {
      unitValues[row] = board[row][col];
    }
    for(charDigit = '1'; charDigit <= '9'; ++charDigit)
    {
      if(hasRepeats(unitValues,9,charDigit))
      {
        return FALSE;
      }
    }
  }

  /*these nested for loops check the sudoku rule for the square units*/
  for(row = 0; row < 9; row += 3)
  {
    for(col = 0; col < 9; col += 3)
    {
      if(!squareUnitChecker(row,col,board))
      {
        return FALSE;
      }
    }
  }
  return TRUE;
}

/*checks if an array has repeated elements in it*/
int hasRepeats(char *array, int array_size, char number) {
  int i;
  int counter;
  counter = 0;
  for (i = 0; i < array_size; ++i) {
    if (array[i] == number && array[i] != '.') {
      counter++;
    }
  }
  if(counter > 1)
  {
    return 1;
  }
  return 0;
}
/*This method does all of the hard work in the program. When it is called, it checks if the square
 *it is being called on is blank. If it isn't blank, then the solveBoard() is called recursively at
 *next location on the board. If the square it is called on is blank, then possible moves are found and
 *one is picked. Then solveBoard() is recursively called again at the next board location.
 *if the possibilities of a given square have all been tried, then solveBoard() backtracks to the first
 *location that has more possibilities and picks the next possible value for that square.
 *Bookkeeping is not required because previous states of the board are pushed onto the stack for later use.
 */
int solveBoard(char board[ROWS][COLS],int row, int col)
{
  char tryDigit;
  int index;
  char possibilities[10];
  char (*possibilitiesPtr)[10];
  possibilitiesPtr = &possibilities;

  /*This code is my "breakout" of recursion.
   *It only runs when the whole board has been filled with valid moves.
   */
  if(row == 9)
  {
    return TRUE;
  }

  /*checks if the input square is not blank, if so then call solveBoard() again with updated board location*/
  if(board[row][col] != BLANK)
  {
    if(col == 8)
    {
      return solveBoard(board,row+1,0);
    }
    else
    {
      return solveBoard(board,row,col+1);
    }
  }

  /*This code finds the possible values at the input location and places a valid move there.
   *Then, solveBoard() is called recursively at the next location.
   */
  for(index = 0; index < ROWS; index++)
  {
    possibleMovesAtLocation(row,col,board,possibilitiesPtr);
    tryDigit = possibilities[index];
    if(tryDigit != 'x')
    {
      if(isValidMove(board,row,col,tryDigit))
      {
        board[row][col] = tryDigit;
        if(col == 8)
        {
          if(solveBoard(board,row+1,0))
          {
            return TRUE;
          }
        }
        else
        {
          if(solveBoard(board,row,col+1))
          {
            return TRUE;
          }
        }
      }
    }
  }
  /*If there are no possible moves at this location, then a blank space is placed there 
   *and false is returned, initiating backtracking.
   */
  board[row][col] = BLANK;
  return FALSE;
}

/*Prints out the grid array to screen in a 9x9 square*/
void prettyPrint(char grid[])
{
  int i;
  int j;

  for(i = 0; i < 9; i++)
  {
    if(i == 3 || i == 6)
    {
      printf("------+-------+------\n");
    }
    for(j = 0; j < 9; j++)
    {
      if(j == 3 || j == 6)
      {
        printf("| ");
      }
      if(j == 8)
      {
       printf("%c",grid[i*9 + j]); 
      }
      else
      {
        printf("%c ",grid[i*9 + j]);
      }
    }
    printf("\n");
  }
}

/*Reads in one puzzle and checks if the puzzle is valid*/
int readLine(char grid[82])
{
  int i;
  char c;
  int result;
  result = TRUE;
  for(i = 0; i < 81; i++)
  {
    c = getchar();
    /*checks to see if the input character is a valid symbol
    if it isn't, then print the error and remove the rest of the puzzle from the input stream.
    return a FALSE saying that readLine failed*/
    if(c == '\n' || c == EOF)
    {
      grid[i] = '\0';
      printf("%s\n",grid);
      return FALSE;
    }

    if((c < '1' || c > '9') && c != '.')
    {
      result = FALSE;
    }
    grid[i] = c;
  }
  grid[81] = '\0';
  printf("%s",grid);
  while((c = getchar()) != EOF && (c != '\n'))
  {
    printf("%c",c);
    result = FALSE;
  }
  printf("\n");
  return result;
}

/*turns a 2D array (board) into a 1D array (grid)*/
void toGrid(char grid[82],char board[ROWS][COLS])
{
  int row;
  int col;
  for(row = 0; row < ROWS; row++)
  {
    for(col = 0; col < COLS; col++)
    {
      grid[row*9 + col] = board[row][col];
    }
  }
  grid[81] = '\0';
}

/*turns a 1D Array (grid) into a 2D Array (board)*/
void gridTo2DArray(char grid[82],char board[9][9])
{
  int row;
  int col;
  for(row = 0; row < ROWS; row++)
  {
    for(col = 0; col < COLS; col++)
    {
      board[row][col] = grid[row*9 + col];
    }
  }
}
/*Checks the square unit that the input square is in for repeated values.*/
int squareUnitChecker(int row,int col,char board[9][9])
{
  int rowOffset;
  int colOffset;
  int i;
  int j;
  int unitIndex;
  char unitString[10];
  char charDigit;
  unitIndex = 0;
  unitString[9] = '\0';
  rowOffset = offset(row);
  colOffset = offset(col);
  for(i = row + rowOffset; i < row + rowOffset + 3; i++)
  {
    for(j = col + colOffset; j < col + colOffset + 3; j++)
    {
      unitString[unitIndex] = board[i][j];
      unitIndex++;
    }
  }
  for(charDigit = '1'; charDigit <= '9'; ++charDigit)
    {
      if(hasRepeats(unitString,9,charDigit))
      {
        return FALSE;
      }
    }
  return TRUE;
}

/*helper function that returns the offset of row value*/
int offset(int row)
{
  return -(row % 3);
}

/*this function finds the possible moves at a particular square on the board by enumerating
 *it's peers and removing those moves from the possibleMoves array through pointers.
 */
void possibleMovesAtLocation(int row, int col, char board[ROWS][COLS],char (*possibleMoves)[10])
{
  int tempRow;
  int tempCol;
  char currentDigit;
  int currentDigitIndex;
  int rowOffset;
  int colOffset;
  strcpy(*possibleMoves,"123456789");

  /*this for loop enumerates over the peers on the row of the current square and 
   *removes them from the possibleMoves array*/
  for(tempCol = 0; tempCol < COLS; tempCol++)
  {
    currentDigit = board[row][tempCol];
    if(currentDigit != BLANK && tempCol != col)
    {
      currentDigitIndex = currentDigit - 49;
      /*an x is placed at the index of a move in the possibleMoves arrray if it is invalid*/
      (*possibleMoves)[currentDigitIndex] = 'x';
    }
  }

  /*this for loop enumerates over the peers on the columns of the current square and 
   *removes them from the possibleMoves array*/
  for(tempRow = 0; tempRow < ROWS; ++tempRow)
  {
    currentDigit = board[tempRow][col];
    if(currentDigit != BLANK && tempRow != row)
    {
      currentDigitIndex = currentDigit - '0' - 1;
      /*an x is placed at the index of a move in the possibleMoves arrray if it is invalid*/
      (*possibleMoves)[currentDigitIndex] = 'x';
    }
  }

  /*this code checks the peers of the square unit and removes them from the possibleMoves array*/
  rowOffset = offset(row);
  colOffset = offset(col);
  for(tempRow = row + rowOffset; tempRow < row + rowOffset + 3; tempRow++)
  {
    for(tempCol = col + colOffset; tempCol < col + colOffset + 3; tempCol++)
    {
      currentDigit = board[tempRow][tempCol];
      if(currentDigit != BLANK && (tempRow != row || tempCol != col))
      {
        currentDigitIndex = currentDigit - '0' - 1;
        /*an x is placed at the index of a move in the possibleMoves arrray if it is invalid*/
        (*possibleMoves)[currentDigitIndex] = 'x';
      }
    }
  }
  return;
}