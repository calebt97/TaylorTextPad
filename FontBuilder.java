/**
 * Created by calebtaylor on 7/20/2017.
 */
public class FontBuilder {
    String style = "Arial";
    static int size = 20;
    static int PLAIN = 0;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPLAIN() {
        return PLAIN;
    }

    public void setPLAIN(int PLAIN) {
        this.PLAIN = PLAIN;
    }

    public FontBuilder(){}


}
