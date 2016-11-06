package mobile.ikreg.com.mytestapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import mobile.ikreg.com.mytestapplication.widget.colorPicker.ColorPicker;
import mobile.ikreg.com.mytestapplication.widget.colorPicker.MaterialColorPalette;
import mobile.ikreg.com.mytestapplication.widget.colorPicker.OnColorChangedListener;

public class ColorPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        final ColorPicker colorPicker = (ColorPicker)findViewById(R.id.colorPicker);
        final ColorPicker subColorPicker = (ColorPicker)findViewById(R.id.subColorPicker);
        subColorPicker.setColors(MaterialColorPalette.RED);
        colorPicker.setSelectedColor(Color.parseColor("#F44336"));
        subColorPicker.setSelectedColor(Color.parseColor("#F44336"));

        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                updateSecPicker(colorPicker, subColorPicker);
            }
        });
    }

    private void updateSecPicker(ColorPicker mainPicker, ColorPicker secPicker) {
        switch(mainPicker.getHexColor()) {
            case "#F44336":
                secPicker.setColors(MaterialColorPalette.RED);
                secPicker.setSelectedColor(Color.parseColor("#F44336"));
                break;
            case "#FF9800":
                secPicker.setColors(MaterialColorPalette.ORANGE);
                secPicker.setSelectedColor(Color.parseColor("#FF9800"));
                break;
            case "#FFC107":
                secPicker.setColors(MaterialColorPalette.AMBER);
                secPicker.setSelectedColor(Color.parseColor("#FFC107"));
                break;
            case "#FFEB3B":
                secPicker.setColors(MaterialColorPalette.YELLOW);
                secPicker.setSelectedColor(Color.parseColor("#FFEB3B"));
                break;
            case "#CDDC39":
                secPicker.setColors(MaterialColorPalette.LIME);
                secPicker.setSelectedColor(Color.parseColor("#CDDC39"));
                break;
            case "#4CAF50":
                secPicker.setColors(MaterialColorPalette.GREEN);
                secPicker.setSelectedColor(Color.parseColor("#4CAF50"));
                break;
            case "#009688":
                secPicker.setColors(MaterialColorPalette.TEAL);
                secPicker.setSelectedColor(Color.parseColor("#009688"));
                break;
            case "#2196F3":
                secPicker.setColors(MaterialColorPalette.BLUE);
                secPicker.setSelectedColor(Color.parseColor("#2196F3"));
                break;
            case "#3F51B5":
                secPicker.setColors(MaterialColorPalette.INDIGO);
                secPicker.setSelectedColor(Color.parseColor("#3F51B5"));
                break;
            case "#9C27B0":
                secPicker.setColors(MaterialColorPalette.PURPLE);
                secPicker.setSelectedColor(Color.parseColor("#9C27B0"));
                break;
            default:
                secPicker.setColors(MaterialColorPalette.RAINBOW);
                break;

        }
    }
}
