package com.example.signbridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.signbridge.HomePageFragments.HistoryFragment;
import com.example.signbridge.HomePageFragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    FrameLayout frameLayout;

    private ImageView[] imageViews = new ImageView[3]; // Array to store ImageViews
    private TextView[] textViews = new TextView[3]; // Array to store TextViews
    private LinearLayout[] LLaout = new LinearLayout[3]; // Array to store TextViews
    private int selectedPosition = -1; // To keep track of the selected item


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frameLayout = findViewById(R.id.frame_layout);

        initializeViews();

        onImageClicked(0);


        replaceFragment(new HomeFragment());


    }



    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initializeViews() {
        imageViews[0] = findViewById(R.id.item1_img);
        imageViews[1] = findViewById(R.id.item2_img);
        imageViews[2] = findViewById(R.id.item3_img);

        textViews[0] = findViewById(R.id.item1_text);
        textViews[1] = findViewById(R.id.item2_text);
        textViews[2] = findViewById(R.id.item3_text);


        LLaout[0] = findViewById(R.id.item1);
        LLaout[1] = findViewById(R.id.item2);
        LLaout[2] = findViewById(R.id.item3);



        // Set onClickListeners for each ImageView
        for (int i = 0; i < LLaout.length; i++) {
            final int position = i;
            LLaout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageClicked(position);
                }
            });
        }
    }

    // Method to handle image/text color change on click
    private void onImageClicked(int position) {
        if (selectedPosition != -1) {
            // Reset the previously selected item to its original color
            imageViews[selectedPosition].setColorFilter(null); // Remove color filter
            textViews[selectedPosition].setTextColor(Color.BLACK); // Set text color to normal
        }

        switch (position) {
            case 0:
                // Replace with Home Fragment
                replaceFragment(new HomeFragment());
                break;
            case 1:
                // Replace with History Fragment
                replaceFragment(new HistoryFragment());
                break;
            case 2:
                // Replace with History Fragment
                //replaceFragment(new ProfileFragment());
                break;
        }

        if (selectedPosition == position) {
            // If the same item is clicked again, deselect it
            selectedPosition = -1;
        } else {
            // Apply the selection to the clicked item
            imageViews[position].setColorFilter(Color.parseColor("#FFA500")); // Change image color to orange
            textViews[position].setTextColor(Color.parseColor("#FFA500")); // Change text color to orange
            selectedPosition = position;
        }
    }


}