package mobile.ikreg.com.mytestapplication.widget.colorPicker;

import android.graphics.Color;

public class MaterialColorPalette {

    public static int[] MAIN;
    public static int[] RED;
    public static int[] ORANGE;
    public static int[] AMBER;

    static {
        MAIN = new int[] { Color.parseColor("#F44336"),
                Color.parseColor("#FF9800"), Color.parseColor("#FFC107"), Color.parseColor("#FFEB3B"),
                Color.parseColor("#CDDC39"), Color.parseColor("#4CAF50"), Color.parseColor("#009688"),
                Color.parseColor("#2196F3"), Color.parseColor("#3F51B5"), Color.parseColor("#9C27B0")};
    }
}
