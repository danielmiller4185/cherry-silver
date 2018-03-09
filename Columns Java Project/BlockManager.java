import java.awt.*;
import java.util.*;

/**
 * A class for managing the blocks used in the game.
 */
public class BlockManager {
    static boolean isCurrentPieceFinishedFalling = false;
    static BlockObject[][] boardArray;
    static ArrayList<BlockObject> currentPiece;
    Random randOb = new Random();
    HashSet<BlockObject> matchedBlocks = new HashSet<>();
    static boolean fallDown;
    int numOfColors;
    Dimension boardSizeInBlocks;
    static boolean wereBlocksRemoved;

    /**
     * Constructs a BlockManager object
     * @param randOb the random object used in the pieceGenerator
     * @param rows number of rows on the game board
     * @param cols number of cols on the game board
     * @param numOfColors number of different colored pieces in the game.
     */
    public BlockManager(Random randOb, int rows, int cols, int numOfColors){
        boardArray = new BlockObject[rows][cols];
        this.randOb = randOb;
        this.numOfColors = numOfColors;
        boardSizeInBlocks = new Dimension(cols,rows);
        initializeBoard();
    }

    /**
     * Constructs a BlockManager object
     * @param rows number of rows on the game board
     * @param cols number of cols on the game board
     * @param numOfColors number of different colored pieces in the game.
     */
    public BlockManager(int rows, int cols, int numOfColors){
        boardArray = new BlockObject[rows][cols];
        this.numOfColors = numOfColors;
        boardSizeInBlocks = new Dimension(cols,rows);
        initializeBoard();
    }

    /**
     * Initializes the game board by filling it with empty block objects
     */
    public void initializeBoard(){
        for(int i = 0; i < boardArray.length; i++){
            for(int j = 0; j < boardArray[i].length; j++){
                boardArray[i][j] = new BlockObject(BlockType.EMPTY);
            }
        }
    }

    /**
     * Prints the boardArray in its current state.
     */
    public void printBoardString(){
        for(int i = 0; i < this.boardArray.length; i++){
            for(int j = 0; j < this.boardArray[i].length; j++){
                System.out.print(boardArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     *Generates the random blockObjects and stores them in the currentPiece ArrayList.
     */
    public void pieceGenerator(){

        currentPiece = new ArrayList<BlockObject>();

        currentPiece.add(BlockObject.randomBlockGenerator(randOb,numOfColors));
        currentPiece.add(BlockObject.randomBlockGenerator(randOb,numOfColors));
        currentPiece.add(BlockObject.randomBlockGenerator(randOb,numOfColors));
        currentPiece.get(2).setLocation(3,-1);
        currentPiece.get(1).setY(-2);
        currentPiece.get(0).setY(-3);

        isCurrentPieceFinishedFalling = false;
    }

    /**
     * Drops the piece on the board.
     * @param row the row that the piece will try to be dropped.
     * @param col the col the the piece will dropped on.
     */
    public void dropPiece(int row, int col){
        while(true) {

            //checks to see if the board is empty at that location and assigns
            //the piece its respective coordinates.
            if (boardArray[row][col].getBlockType() == BlockType.EMPTY) {

                this.currentPiece.get(0).setLocation(col,row - 2);
                boardArray[row - 2][col] = this.currentPiece.get(0);
                this.currentPiece.get(1).setLocation(col, row - 1);
                boardArray[row - 1][col] = this.currentPiece.get(1);
                this.currentPiece.get(2).setLocation(col,row);
                boardArray[row][col] = this.currentPiece.get(2);



                isCurrentPieceFinishedFalling = true;
                break;
            } else {
                row--;
            }
        }
    }

    /**
     * Checks for a match at a specific location.
     * @param x the x value that will be checked
     * @param y the y value that will be checked
     */
    public void checkForMatch(int x, int y){
        //all of the different directions that will be checked
        checkForMatchByDirection(x,y,0,1);
        checkForMatchByDirection(x,y,0,-1);
        checkForMatchByDirection(x,y,1,0);
        checkForMatchByDirection(x,y,1,-1);
        checkForMatchByDirection(x,y,1,1);
        checkForMatchByDirection(x,y,-1,0);
        checkForMatchByDirection(x,y,-1,1);
        checkForMatchByDirection(x,y,-1,-1);
    }

    /**
     * Checks for a match at a specific direction.
     * @param x the starting x value
     * @param y the starting y value
     * @param xOffset the offset that allows a directional check
     * @param yOffset the offset that allows a directional check
     */
    public void checkForMatchByDirection(int x, int y, int xOffset, int yOffset){

        boolean loopControl = true;
        int updatedX = x;
        int updatedY = y;
        HashSet<BlockObject> localMatchedBlocks = new HashSet<>();
        BlockObject tempOb1 = boardArray[updatedY][updatedX];
        tempOb1.setLocation(updatedX,updatedY);
        localMatchedBlocks.add(tempOb1);

        while(loopControl) {

            //prevents ArrayIndexOutOfBoundsException
            if ((((updatedX + xOffset) < boardArray[1].length) && ((updatedY + yOffset) < boardArray.length)) &&
                    (((updatedX + xOffset) >= 0) && ((updatedY + yOffset) >= 0))) {

                //checks to see if the new block is of the same type as the old block
                //and makes sure that the space isn't empty
                if(boardArray[updatedY][updatedX].getBlockType().equals(boardArray[updatedY + yOffset][updatedX + xOffset].getBlockType()) && boardArray[y][x].getBlockType() != BlockType.EMPTY){

                    BlockObject tempOb = boardArray[updatedY + yOffset][updatedX + xOffset];
                    tempOb.setLocation(updatedX + xOffset,updatedY + yOffset);
                    if(!localMatchedBlocks.contains(tempOb)) {
                        localMatchedBlocks.add(tempOb);
                    }

                    //update the x and y values and repeat the process until the blocks don't match
                    updatedX += xOffset;
                    updatedY += yOffset;
                }else{
                    loopControl = false;}
            }else{loopControl = false;}
        }

        if(localMatchedBlocks.size() >= 3) {

            if(matchedBlocks.isEmpty()) {
                matchedBlocks.addAll(localMatchedBlocks);
            }else{
                for (BlockObject b : localMatchedBlocks) {

                    //check for duplicates and add the blocks to the total
                    if(!matchedBlocks.contains(b)){ matchedBlocks.add(b);}
                }
                matchedBlocks.addAll(localMatchedBlocks);
            }
        }
    }

    /**
     * Removes the blocks from the game board that are stored in the matchedBlocks array list.
     */
    public void removeBlocks(){
        int blockCounter = 0;


        //checks for duplicates, removes the extras from the list, and then removes the remaining blocks.
        for(BlockObject b : matchedBlocks){
            if(boardArray[b.getY()][b.getX()].getBlockType() != BlockType.EMPTY) {
                blockCounter++;
                boardArray[b.getY()][b.getX()] = new BlockObject(BlockType.EMPTY);
            }
        }
        if(blockCounter != 0){
            System.out.println("Removing " + blockCounter + " blocks.");
            Columns.game.setScore(blockCounter);
            wereBlocksRemoved = true;
            System.out.println(wereBlocksRemoved);
        }else{
            wereBlocksRemoved = false;
        }
        Columns.game.gui.gamePanel.repaint();
        matchedBlocks.clear();
    }

    /**
     * Checks for empty space under a specific location and makes the block move down a space if there is.
     * @param x the x location that will be checked for gravity
     * @param y the y location that will be checked for gravity
     */
    public void fallDownAtLocation(int x, int y){
        BlockObject tempObj = boardArray[y][x];
        int xLoc = tempObj.getX();
        int yLoc = tempObj.getY();

        if(tempObj.getBlockType() != BlockType.EMPTY){

            //prevents ArrayOutOfBoundsException
            if ((((xLoc) < boardArray[1].length) && ((yLoc + 1) < boardArray.length))){
                //makes the block fall down if there is an empty space underneath it.
                if(boardArray[yLoc + 1][xLoc].getBlockType() == BlockType.EMPTY){
                    tempObj.setLocation(xLoc, yLoc + 1);
                    boardArray[yLoc + 1][xLoc] = tempObj;
                    boardArray[yLoc][xLoc] = new BlockObject(BlockType.EMPTY,xLoc,yLoc);
                    int newYLoc = yLoc + 1;
                    setFallDown(true);
                }
            }
        }

    }

    /**
     * Goes over the whole board and checks for empty space under blocks.
     */
    public void wholeBoardFallDown(){
        setFallDown(false);
        for(int x = 0; x < boardArray[1].length; x++){
            for(int y = 0 ; y < boardArray.length; y++){
                fallDownAtLocation(x,y);
            }
        }
    }

    /**
     * Checks for at least three in a row at every location on the board.
     */
    public void wholeBoardCheckForMatch(){
        for(int x = 0; x < boardArray[1].length; x++){
            for(int y = 0; y < boardArray.length; y++){
                checkForMatch(x,y);
            }
        }
        removeBlocks();
    }

    /**
     * @param b the boolean variable that is true if an object has fallen down on a given turn.
     */
    public void setFallDown(boolean b){
        fallDown = b;
    }
    public static void rotatePiece(ArrayList<BlockObject> arrayList){

        //update each y location for each block and change its location in that boardArray
        BlockManager.currentPiece.get(2).setY(BlockManager.currentPiece.get(2).getY() - 1);
        BlockManager.boardArray[BlockManager.currentPiece.get(2).getY()][BlockManager.currentPiece.get(2).getX()] = BlockManager.currentPiece.get(2);

        BlockManager.currentPiece.get(1).setY(BlockManager.currentPiece.get(1).getY() - 1);
        BlockManager.boardArray[BlockManager.currentPiece.get(1).getY()][BlockManager.currentPiece.get(1).getX()] = BlockManager.currentPiece.get(1);

        BlockManager.currentPiece.get(0).setY(BlockManager.currentPiece.get(0).getY() + 2);
        BlockManager.boardArray[BlockManager.currentPiece.get(0).getY()][BlockManager.currentPiece.get(0).getX()] = BlockManager.currentPiece.get(0);


        System.out.println(arrayList);
        Collections.rotate(arrayList, - 1);
        System.out.println(arrayList);

        BlockManager.boardArray[BlockManager.currentPiece.get(0).getY() - 1][BlockManager.currentPiece.get(0).getX()] = new BlockObject(BlockType.EMPTY);
    }
}
