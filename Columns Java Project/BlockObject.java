import java.awt.*;
import java.util.Random;

/**
 * @author Daniel Miller
 * A class that represents individual block objects.
 */
public class BlockObject implements  Comparable<BlockObject>{
    BlockType blocktype = null;
    int x;
    int y;

    /**
     * Constructor
     * @param bt the block type that will be assigned to the block object.
     */
    public BlockObject(BlockType bt){
        this.blocktype = bt;
    }

    /**
     * a default constructor.
     */
    public BlockObject(){
    }

    /**
     * a constructor that initializes all fields.
     * @param bt the object's block type.
     * @param x the object's x location
     * @param y the object's y location
     */
    public BlockObject(BlockType bt, int x, int y){
        this.blocktype = bt;
        this.setX(x);
        this.setY(y);
    }

    /**
     * Randomly generates a block.
     * @param randOb a random object with a constant seed.
     * @param numOfColors the number of colors that are available to be generated.
     * @return a block object with a random color
     */
    public static BlockObject randomBlockGenerator(Random randOb, int numOfColors){
        int x = randOb.nextInt(numOfColors);
        //System.out.println(x);
        BlockObject block = new BlockObject();
        switch(x){
            case 0 : block.setBlockType(BlockType.GREEN);
                break;
            case 1 : block.setBlockType(BlockType.BLUE);
                break;
            case 2 : block.setBlockType(BlockType.RED);
                break;
            case 3 : block.setBlockType(BlockType.CYAN);
                break;
            case 4 : block.setBlockType(BlockType.PINK);
                break;
            default : System.out.println("Error in random generator." + x);
        }
        //System.out.println("This should never happen.");
        return block;
    }

    /**
     * @param x the x value
     * @param y the y value
     */
    public void setLocation(int x, int y){
        //System.out.println("Setting location");
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getY(){
        return this.y;
    }

    public String getLocation() {
        return this.y + " " + this.x;
    }
    public void setBlockType(BlockType b){
        this.blocktype = b;
    }
    public BlockType getBlockType(){
        return this.blocktype;
    }
    public String toString(){
        return this.getBlockType().toString(); //+ this.getY() + this.getX();
    }
    public int compareTo(BlockObject b){
        if(this.getLocation() == b.getLocation() && this.getBlockType() == b.getBlockType()){
            return 0;
        }else{return 1;}
    }
    public boolean equals(BlockObject b){
        if(b.getBlockType() == this.getBlockType() && b.getLocation() == this.getLocation()){
            return true;
        }else{return false;}
    }
    public Color getColor(){
        return this.blocktype.color;
    }
}
