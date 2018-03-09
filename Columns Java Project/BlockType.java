import java.awt.*;

/**
 * @author Daniel Miller 4-20-17
 * cs251
 * An enum that contains the different types of blocks.
 */
public enum BlockType implements Comparable<BlockType>{
    RED("R", Color.red),
    GREEN("G", Color.green),
    BLUE("B", Color.blue),
    CYAN("C", Color.cyan),
    PINK("P",Color.pink),
    GREY("G",Color.gray),
    EMPTY(".",Color.black);

    public final String symbol;
    public final Color color;

    /**
     * Constructs enum objects.
     * @param symbol the symbol that will be printed in toString()
     */
    BlockType(String symbol, Color color){
        this.symbol = symbol;
        this.color = color;
    }


    @Override
    public String toString(){
        return this.symbol;
    }
}
