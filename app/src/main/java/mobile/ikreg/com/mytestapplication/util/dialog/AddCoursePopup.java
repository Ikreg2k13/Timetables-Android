package mobile.ikreg.com.mytestapplication.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import mobile.ikreg.com.mytestapplication.R;
import mobile.ikreg.com.mytestapplication.widget.colorPicker.ColorPicker;
import mobile.ikreg.com.mytestapplication.widget.colorPicker.MaterialColorPalette;
import mobile.ikreg.com.mytestapplication.widget.colorPicker.OnColorChangedListener;


public class AddCoursePopup extends Dialog {

    public AddCoursePopup(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_add_course);
        setCancelable(false);
        setColorPickers();

        Button cancel = (Button)findViewById(R.id.button2);
        Button add = (Button)findViewById(R.id.button);

        cancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AddCoursePopup.this.cancel();
          }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCoursePopup.this.cancel();
            }
        });
    }

    private void setColorPickers() {
        final ColorPicker mainPicker = (ColorPicker)findViewById(R.id.pickerMain);
        final ColorPicker subPicker = (ColorPicker)findViewById(R.id.pickerSub);
        final View selectedColor = findViewById(R.id.selectedColor);
        final GradientDrawable circleShape = (GradientDrawable)selectedColor.getBackground();

        mainPicker.setSelectedColor(Color.parseColor("#F44336"));
        subPicker.setColors(MaterialColorPalette.RED);
        subPicker.setSelectedColor(Color.parseColor("#F44336"));
        circleShape.setColor(Color.parseColor("#F44336"));

        mainPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                updateSubPicker(mainPicker, subPicker);
            }
        });
        subPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                circleShape.setColor(Color.parseColor(subPicker.getHexColor()));
            }
        });
    }

    private void updateSubPicker(ColorPicker main, ColorPicker sub) {
        switch(main.getHexColor()) {
            case "#F44336":
                sub.setColors(MaterialColorPalette.RED);
                sub.setSelectedColor(Color.parseColor("#F44336"));
                break;
            case "#FF9800":
                sub.setColors(MaterialColorPalette.ORANGE);
                sub.setSelectedColor(Color.parseColor("#FF9800"));
                break;
            case "#FFC107":
                sub.setColors(MaterialColorPalette.AMBER);
                sub.setSelectedColor(Color.parseColor("#FFC107"));
                break;
            case "#FFEB3B":
                sub.setColors(MaterialColorPalette.YELLOW);
                sub.setSelectedColor(Color.parseColor("#FFEB3B"));
                break;
            case "#CDDC39":
                sub.setColors(MaterialColorPalette.LIME);
                sub.setSelectedColor(Color.parseColor("#CDDC39"));
                break;
            case "#4CAF50":
                sub.setColors(MaterialColorPalette.GREEN);
                sub.setSelectedColor(Color.parseColor("#4CAF50"));
                break;
            case "#009688":
                sub.setColors(MaterialColorPalette.TEAL);
                sub.setSelectedColor(Color.parseColor("#009688"));
                break;
            case "#2196F3":
                sub.setColors(MaterialColorPalette.BLUE);
                sub.setSelectedColor(Color.parseColor("#2196F3"));
                break;
            case "#3F51B5":
                sub.setColors(MaterialColorPalette.INDIGO);
                sub.setSelectedColor(Color.parseColor("#3F51B5"));
                break;
            case "#9C27B0":
                sub.setColors(MaterialColorPalette.PURPLE);
                sub.setSelectedColor(Color.parseColor("#9C27B0"));
                break;
            default:
                sub.setColors(MaterialColorPalette.RAINBOW);
                break;

        }
    }
}
