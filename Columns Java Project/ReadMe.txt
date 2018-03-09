Daniel Miller
Columns Game: ReadMe

Game Play
	Game play begins when you hit the start button at the bottom of the screen. This is a standard columns game. The pieces consist of three randomly generated blocks that will fall unaided from the fourth column to the bottom of the screen. Blocks will be removed when a match of at least three is formed in any of the eight directions. When blocks are removed, the remaining blocks will be matched and removed until there are no more matches. Then, a new random piece will be dropped and the process repeates. 

	Controls:
	The left and right arrows move the piece to the left or right as it falls. The up arrow shuffles the order of the blocks. If hit once, the bottom block gets moved to the top, and the top two pieces are moved down two spaces respectively. The down arrow moves the piece down one space and the space bar drops the piece to the lowest available space in the current column that the piece is in.

	Scoring:
	Each time you remove a block, 100 is added to the overall score.

Internals
	The code is made up of seven classes.

	BlockManager:
		The block manager class does the heavy lifting for the game. It contains the 2D array of BlockObjects that represents the board and the  currentPiece ArrayList. It is also in charge of dropping pieces on the board and making them "fall" down to the bottom. It contains methods for checking for matches, removing blocks, and making the remaining blocks fall down to the bottom of the game.

		Algorithms:

		Matching Blocks
		Checking for matches at a specific direction is done by feeding in an x and a y location and an xOffset and a yOffst. I used a HashSet called matchedBlocks to contain the individual blocks that have been matched and not allow any duplicates. The current block is stored and the first block next to the current block is compared. If it matches, then it is stored and the process repeats until the edge of the board is reached, or there isn't a match. This process is then repeated for every direction and on the every location on the board.

		Removing Blocks
		This is very simple. The matchedBlocks HashSet is iterated over and empty blocks are placed at each location of a matched block.

		Gravity
		Pieces fall down when there is empty space under them. Check underneath the current piece to see if there is empty space. If there is, move it down a space. Repeat this process for the whole board.

	BlockType:
		This is my enum of different block types. It contains six different colors of blocks and one empty block type. Each type of block has its own color field that is used for painting the screen and symbol field that is used in the boardArray.

	BlockObject:
		Each block object contains its own blocktype field and an x and y location on the boardArray. This class contains all of the necessary getter and setter methods for these fields, and it also contains the randomBlockGenerator method that uses a switch statement and a random object to generate randomly colored blocks.

	GameGUI: 
		This class contains all of the information needed to run the game's gui. There is a JFrame that is the container for the gui and several JPanels and JTextFields that are used for the score and start button. It also contains an inner class called GameBoard which extends JPanel and is where the game will actually be played. GameBoard does override paintComponent, and it has a KeyListener attached to it that is explained below. It also has a block manager.

	KeyEvents:
		This class extends KeyAdapter and contains one overridden method, keyPressed, which contains the logic for the five different keys.

			Algorithms:

			For the down key, each individual block's y location is simply moved down one and that block is placed on the boardArray at the new location. Then an empty block is placed where the top block was before the move.

			For the left and right keys, each individual block's x location is either increased one or decreased one and placed into the boardArray at the new location. Empty blocks are then placed where the piece was before the move.

			For the up key, I used the Collections.rotate() method and placed the blocks at their updated locations on the boardArray.

			For the space bar, I used my dropPiece() method that is in BlockManager. It is passed a row and a col that the piece is trying to be dropped in. If there is an empty space at the bottom of the board, it updates the individual block's locations and places them into boardArray. If there isn't space at the bottom of the board, it moves up one row and checks again until an empty space is found.

	Columns:
		This is the main class. It contains a GameGUI object and several important fields like score, gamePaused, and two very important timers. It also contains a startGame method which is called when the start button is clicked. This method contains two timers. The first timer is in charge of dropping the current piece one space for each tick until it reaches the bottom or another piece below it. At this point, the piece is finished falling, and the first timer is paused while the second timer takes over. For each tick of the second timer, the whole board is checked for a match, blocks are removed, and the remaining blocks fall down. This continues until no more blocks are removed and a new piece is generated while the first timer is started again. This process is repeated until a loss is detected by checking to see if a whole column is filled with colored blocks.

Bugs:

	There are ArrayIndexOutOfBoundsExceptions that occur when you use the arrow keys while the piece is not completely on the screen. I wanted to fix this, but I ran out of time. It doesn't effect game play, so I just left it alone. Weird key exceptions occur when you hold down the side arrows when the piece is on the edge of the screen. I don't know how to fix this and it didn't effect game play so I didn't mess with it. Sometimes the timers and buttons do strange things. For example, the game can suddenly break if the piece is rotated right when it hits the ground. It rarely happens, and I don't know how to fix it.

Next Steps:
	I would have loved to create an exploding block that removes all blocks on the screen that are the same color as the one it lands on. With more time, I would have added levels to the game. Maybe after the score reaches 10_000, the level increases and the timer is sped up. I also thought it would be cool to have the blocks colored with images of different planets and music running in the background. (Possibly doctor mario or some other classic game's soundtrack.) The biggest thing that I would like to try would be a command line argument that creates a two player version. I don't think it would be that difficult to pull off and it would greatly improve the game.


	