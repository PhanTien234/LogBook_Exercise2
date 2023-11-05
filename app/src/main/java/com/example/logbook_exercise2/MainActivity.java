package com.example.logbook_exercise2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    private GifImageView imageView;
    private TextView imageIDTextView;
    private List<Integer> imageResources;
    private int currentImageIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        imageIDTextView = findViewById(R.id.imageID);
        imageResources = getImageResources();
    }
    // Method to update the displayed image
    private void updateImage() {
        int imageResourceId = imageResources.get(currentImageIndex);
        imageView.setImageResource(imageResourceId);
        // Set the image ID in the TextView
        int imageID = currentImageIndex;
        imageIDTextView.setVisibility(View.VISIBLE);
        imageIDTextView.setText("Image ID: " + imageID);
    }
    // Button click handler for displaying the next image
    public void onNextButtonClick(View view) {
        if (currentImageIndex < imageResources.size() - 1) {
            currentImageIndex++;
        } else {
            currentImageIndex = 0;
        }
        updateImage();
    }
    // Button click handler for displaying the previous image
    public void onPreviousButtonClick(View view) {
        if (currentImageIndex > 0) {
            currentImageIndex--;
        } else {
            currentImageIndex = imageResources.size() - 1;
        }
        updateImage();
    }

    // Method to dynamically get the list of image resources from the drawable folder
    private List<Integer> getImageResources() {
        List<Integer> imageResources = new ArrayList<>();
        Field[] drawables = R.drawable.class.getFields();

        TypedValue typedValue = new TypedValue();
        for (Field field : drawables) {
            try {
                getResources().getValue(field.getInt(null), typedValue, true);
                if (typedValue.string.toString().endsWith(".jpg")
                        || typedValue.string.toString().endsWith(".png")
                        || typedValue.string.toString().endsWith(".gif")) {
                    int resourceId = field.getInt(null);
                    imageResources.add(resourceId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageResources;
    }
}
