
/*
Changes font and size. Pretty simple, just a list of Getter/Setter Methods to store various font configurations
that the user sees while using the textpad. It begins as plain, 20pt arial font and changes as the user desires.
*/
public class FontBuilder {

    String style;
    static int size;
    static int PLAIN;
    
    public FontBuilder(){.
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
