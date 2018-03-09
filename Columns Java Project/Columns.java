import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * @author Daniel Miller
 */
public class Columns {
    GameGUI gui;
    static boolean gamePaused = false;
    Timer timer;
    static Columns game;

    static int numBlocksRemoved;
    static int score = 0;

    public static void main(String[] args) {
        if(args.length != 0) {
            int colors = Integer.parseInt(args[0]);
            game = new Columns(14, 8, colors);
        }else{
            game = new Columns(14,8,4);
        }

    }

    /**
     * Constructor for the columns game.
     * @param rows number of rows on the board.
     * @param cols number of columns on the board.
     * @param colors number of block colors.
     */
    public Columns(int rows, int cols, int colors) {
        Random randOb = new Random();
        gui = new GameGUI(colors,rows,cols);

    }

    /**
     * sets the gamePaused field.
     * @param b
     */
    public void setGamePaused(boolean b) {
        gamePaused = b;
    }

    /**
     * Code that is run to start the game.
     */
    public void startGame(){
        game.gui.gamePanel.bm.pieceGenerator();
        game.gui.gamePanel.requestFocusInWindow();

        //this timer controls the falling of blocks from the top.
        //After each tick, the piece falls down one space.
        timer = new Timer(750, new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {


                if (!Columns.gamePaused) {
                    game.lossDetection(BlockManager.boardArray);

                    //guards against arrayOutOfBoundsException
                    if (BlockManager.currentPiece.get(2).getY() + 1 < gui.gamePanel.bm.boardArray.length) {

                        //moves the piece down one space if the space below it is empty.
                        if (gui.gamePanel.bm.boardArray[BlockManager.currentPiece.get(2).getY() + 1][BlockManager.currentPiece.get(2).getX()].getBlockType() == BlockType.EMPTY) {

                            for (int i = 2; i >= 0; i--) {
                                BlockManager.currentPiece.get(i).setY(BlockManager.currentPiece.get(i).getY() + 1);
                                BlockManager.currentPiece.get(i).setX(BlockManager.currentPiece.get(2).getX());
                                if (BlockManager.currentPiece.get(i).getY() >= 0) {
                                    //add it to board
                                    gui.gamePanel.bm.boardArray[BlockManager.currentPiece.get(i).getY()][BlockManager.currentPiece.get(i).getX()] = BlockManager.currentPiece.get(i);
                                }
                            }
                            if (BlockManager.currentPiece.get(0).getY() > 0) {
                                gui.gamePanel.bm.boardArray[BlockManager.currentPiece.get(0).getY() - 1][BlockManager.currentPiece.get(0).getX()] = new BlockObject(BlockType.EMPTY);
                            }

                            gui.gamePanel.repaint();

                        } else {
                            BlockManager.isCurrentPieceFinishedFalling = true;
                            //System.out.println("finished falling");

                        }
                    } else {
                        BlockManager.isCurrentPieceFinishedFalling = true;
                        //System.out.println("finished falling");
                    }
                }

            }
        });
        timer.start();


        //this timer controls the removal and falling of matched blocks
        Timer timer2 = new Timer(1000, new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

                if (BlockManager.isCurrentPieceFinishedFalling) {
                    timer.stop();

                    game.gui.gamePanel.bm.wholeBoardCheckForMatch();

                    game.gui.gamePanel.bm.wholeBoardFallDown();

                    while(BlockManager.fallDown){
                        game.gui.gamePanel.bm.wholeBoardFallDown();
                    }
                    game.gui.gamePanel.repaint();

                    if(!BlockManager.wereBlocksRemoved){
                        game.gui.gamePanel.bm.pieceGenerator();
                        timer.start();
                    }
                }
            }
        });
        timer2.start();
    }

    /**
     * sets the game score.
     * @param removedBlocks number of blocks removed.
     */
    public void setScore(int removedBlocks){
        this.numBlocksRemoved += removedBlocks;
        score = numBlocksRemoved * 100;
        game.gui.updateScore();
    }

    /**
     * detects a loss
     * @param boardArray the boardArray for the game.
     */
    public void lossDetection(BlockObject[][] boardArray){
        int counter = 0;
        for(int x = 0; x < boardArray[0].length; x++) {
            for(int y = 0; y < boardArray.length; y++){
                if(boardArray[y][x].getBlockType() == BlockType.EMPTY){
                    break;
                }else{
                    counter++;
                }

                if(counter == boardArray.length - 1){
                    game.gui.startButton.setText("GAME OVER");
                    timer.stop();
                }
            }
        }
    }
}


