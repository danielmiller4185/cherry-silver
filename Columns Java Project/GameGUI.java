import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Daniel Miller
 */
public class GameGUI  {
    static JFrame jFrame = new JFrame("Columns ");
    GameBoard gamePanel;
    JPanel previewPanel;
    JLabel scoreTextField;
    JButton startButton;
    JPanel scorePanel;
    JPanel rightPanel = new JPanel();
    //JPanel leftPanel = new JPanel();
    JPanel startPanel;
    int pauseCounter = 0;

    /**
     * GameGUI constructor
     * @param numOfColors
     */
    public GameGUI(int numOfColors, int rows, int cols) {
        jFrame.setVisible(true);

        gamePanel = new GameBoard(560, 320, rows,cols,numOfColors);


        previewPanel = new JPanel();
        scorePanel = new JPanel();
        startPanel = new JPanel();

        scoreTextField = new JLabel("Score: " + Columns.score);

        Dimension dim = new Dimension(100,35);
        scoreTextField.setPreferredSize(dim);
        startButton = new JButton("Start");

        startPanel.add(startButton);
        scorePanel.add(scoreTextField);

        rightPanel.add(previewPanel, BorderLayout.PAGE_START);
        rightPanel.add(scorePanel, BorderLayout.PAGE_END);

        //an action listener for the startButton
        jFrame.add(startButton, BorderLayout.PAGE_END);
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(pauseCounter == 0){
                    Columns.game.startGame();
                    startButton.setText("Pause");
                    Columns.game.gui.gamePanel.requestFocusInWindow();
                }else{
                    Columns.gamePaused = !Columns.gamePaused;
                    if(Columns.gamePaused){
                        startButton.setText("Start");
                    }else{
                        startButton.setText("Pause");
                    }
                    Columns.game.gui.gamePanel.requestFocusInWindow();
                }
                pauseCounter++;
            }
        });

        jFrame.add(gamePanel, BorderLayout.CENTER);
        jFrame.add(rightPanel, BorderLayout.EAST);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension d = new Dimension(600, 700);
        jFrame.setSize(d);
        jFrame.setVisible(true);


        jFrame.addKeyListener(new KeyEvents());
        jFrame.repaint();


    }

    /**
     * updates the score
     */
    public void updateScore(){
        scoreTextField.setText("Score: " + Columns.score);
    }
}

class GameBoard extends JPanel{

    BlockManager bm;
    int blockSize;

    /**
     * GameBoard constructor
     * @param height the height of the gamePanel in pixels
     * @param width the width of the gamePanel in pixels
     * @param numOfColors the number of colored blocks.
     */
    public GameBoard(int height, int width,int rows, int cols, int numOfColors) {

        bm = new BlockManager(rows, cols, numOfColors);

        final int boardHeight = height;
        final int boardWidth = width;
        Dimension d = new Dimension(boardWidth, boardHeight);
        setSize(d);
        setVisible(true);

        blockSize = (int) (boardWidth / bm.boardSizeInBlocks.getWidth());

        addKeyListener((new KeyEvents()));
        setFocusable(true);
        requestFocusInWindow();

    }

    public GameBoard() {
        //default constructor
    }

    /**
     * An overridden paintComponent
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < bm.boardSizeInBlocks.getWidth(); x++) {
            for(int y = 0; y < bm.boardSizeInBlocks.getHeight(); y++){
                g.setColor(bm.boardArray[y][x].getColor());
                if(bm.boardArray[y][x].getBlockType() == BlockType.EMPTY){
                    g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
                }else {
                    g.setColor(Color.black);
                    g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
                    g.setColor(bm.boardArray[y][x].getColor());
                    g.fillRoundRect(x * blockSize, y * blockSize, blockSize, blockSize,20,20);
                }
            }
        }
    }
}

