package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Achievement extends BaseActivity {

    int total = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        //Populate the array of imagesView
        ImageView[] images = createImageArray(total);
        //set all listener to the img scroll view
        for (int i = 0; i < images.length; i++) {
            //lambda need finals not locals as value passed
            int atImgNumb = i;
            //lambda recommended way (old java wont work)
            images[i].setOnClickListener(v -> changeImg(images[atImgNumb].getDrawable()));
        }
    }

    //Change the img according to drawable selected (used by lambda above) (callback)
    public void changeImg(Drawable drawableImg) {
        ImageView toShow = findViewById(R.id.imageViewPrincipal);
        toShow.setImageDrawable(drawableImg);
    }

    //Using the tags of the linear images ImgView children to create the img array
    private ImageView[] createImageArray(int total) {
        ImageView[] images = new ImageView[total];
        LinearLayout linear = findViewById(R.id.linear_images);
        for (int position = 1; position <= total; position++) {
            String imgTag = "img";
            imgTag += position;
            images[position-1] = linear.findViewWithTag(imgTag);
        }
        return images;
    }
}
