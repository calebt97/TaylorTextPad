
/*Changes font and size.*/
public class FontBuilder {

    String style;
    static int size;
    static int PLAIN;
    public FontBuilder(){
        style = "Arial";
        size = 20;
        PLAIN = 0;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        FontBuilder.size = size;
    }

    public static int getPLAIN() {
        return PLAIN;
    }

    public static void setPLAIN(int PLAIN) {
        FontBuilder.PLAIN = PLAIN;
    }






}
