package com.codemybrainsout.placesearchdialog;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.codemybrainsout.placesearch.PlaceSearchDialog;

public class MainActivity extends AppCompatActivity {

    TextInputEditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = (TextInputEditText) findViewById(R.id.etLocation);
        location.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    showPlacePickerDialog();
                }
                return false;
            }
        });

    }

    private void showPlacePickerDialog() {

        PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog.Builder(this)
                //.setHeaderImage(R.drawable.dialog_header)
                .setLocationNameListener(new PlaceSearchDialog.LocationNameListener() {
                    @Override
                    public void locationName(String locationName) {
                        location.setText(locationName);
                    }
                })
                .build();
        placeSearchDialog.show();
    }
}
