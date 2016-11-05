package mobile.ikreg.com.mytestapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import mobile.ikreg.com.mytestapplication.widget.colorPicker.ColorPicker;
import mobile.ikreg.com.mytestapplication.widget.colorPicker.OnColorChangedListener;

public class ColorPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        ColorPicker colorPicker = (ColorPicker)findViewById(R.id.colorPicker);
        //colorPicker.setSelectedColor(Color.RED);

        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                Log.i("ColorPicker", "Color" + Integer.toHexString(c) + "chosen!");
            }
        });
    }
}
