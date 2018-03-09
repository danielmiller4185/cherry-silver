import jdk.nashorn.internal.ir.BlockLexicalContext;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Owner on 4/27/2017.
 */
public class KeyEvents extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        int keys = e.getKeyCode();
        if(!Columns.gamePaused) {

            if (keys == KeyEvent.VK_LEFT) {
                if (BlockManager.isCurrentPieceFinishedFalling == false) {

                    if (BlockManager.currentPiece.get(2).getX() >= 0) {

                        if (BlockManager.boardArray[Columns.game.gui.gamePanel.bm.currentPiece.get(2).getY()][BlockManager.currentPiece.get(2).getX() - 1].blocktype == BlockType.EMPTY) {

                            for (int i = 2; i >= 0; i--) {
                                BlockManager.currentPiece.get(i).setX(BlockManager.currentPiece.get(i).getX() - 1);
                                BlockManager.boardArray[BlockManager.currentPiece.get(i).getY()][BlockManager.currentPiece.get(i).getX()] = BlockManager.currentPiece.get(i);
                                BlockManager.boardArray[BlockManager.currentPiece.get(i).getY()][BlockManager.currentPiece.get(i).getX() + 1] = new BlockObject(BlockType.EMPTY);
                            }
                            Columns.game.gui.gamePanel.repaint();
                        }
                    }
                }
            }
            if (keys == KeyEvent.VK_RIGHT) {
                if (BlockManager.isCurrentPieceFinishedFalling == false) {

                    //prevents arrayOutOfBoundsException
                    if (BlockManager.currentPiece.get(2).getX() < BlockManager.boardArray[1].length) {

                        if (BlockManager.boardArray[BlockManager.currentPiece.get(2).getY()][BlockManager.currentPiece.get(2).getX() + 1].blocktype == BlockType.EMPTY) {

                            for (int i = 2; i >= 0; i--) {
                                BlockManager.currentPiece.get(i).setX(BlockManager.currentPiece.get(i).getX() + 1);
                                BlockManager.boardArray[BlockManager.currentPiece.get(i).getY()][BlockManager.currentPiece.get(i).getX()] = BlockManager.currentPiece.get(i);
                                BlockManager.boardArray[BlockManager.currentPiece.get(i).getY()][BlockManager.currentPiece.get(i).getX() - 1] = new BlockObject(BlockType.EMPTY);
                            }
                            Columns.game.gui.gamePanel.repaint();
                        }
                    }
                }
            }
            //todo fix down arrow
            if (keys == KeyEvent.VK_DOWN) {
                if (BlockManager.isCurrentPieceFinishedFalling == false) {


                    if (BlockManager.currentPiece.get(2).getY() + 1 < BlockManager.boardArray.length) {

                        if (BlockManager.boardArray[BlockManager.currentPiece.get(2).getY() + 1][BlockManager.currentPiece.get(2).getX()].blocktype == BlockType.EMPTY) {


                            //go through each block and move its y location down 1. Add the current block to the boardArray at the new location.
                            for (int i = 2; i >= 0; i--) {
                                BlockManager.currentPiece.get(i).setY(BlockManager.currentPiece.get(i).getY() + 1);
                                BlockManager.boardArray[BlockManager.currentPiece.get(i).getY()][BlockManager.currentPiece.get(i).getX()] = BlockManager.currentPiece.get(i);
                            }

                            //removes the old top block from the boardArray and places a new empty object there.
                            BlockManager.boardArray[BlockManager.currentPiece.get(0).getY() - 1][BlockManager.currentPiece.get(0).getX()] = new BlockObject(BlockType.EMPTY);

                            Columns.game.gui.gamePanel.repaint();
                        }
                    }
                }
            }


            if (keys == KeyEvent.VK_SPACE) {
                if (BlockManager.isCurrentPieceFinishedFalling == false) {

                    for (int i = 2; i >= 0; i--) {
                        BlockManager.boardArray[BlockManager.currentPiece.get(i).getY()][BlockManager.currentPiece.get(i).getX()] = new BlockObject(BlockType.EMPTY);
                    }
                    Columns.game.gui.gamePanel.bm.dropPiece(13,BlockManager.currentPiece.get(2).getX());

                    Columns.game.gui.gamePanel.repaint();
                }
            }


            if (keys == KeyEvent.VK_UP) {
                if (BlockManager.isCurrentPieceFinishedFalling == false) {

                    BlockManager.rotatePiece(BlockManager.currentPiece);
                    Columns.game.gui.gamePanel.repaint();
                }
            }
        }
    }
}


