package com.codemybrainsout.placesearch;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Rahul Juneja on 30-10-2015.
 */
public class PlaceSearchDialog extends Dialog implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, View.OnClickListener {

    private AppCompatAutoCompleteTextView locationET;
    private TextInputLayout locationTIL;
    private AppCompatTextView cancelTV;
    private AppCompatTextView okTV;

    public GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUNDS_WORLD = new LatLngBounds(
            new LatLng(-85, 180), new LatLng(85, -180));

    LocationNameListener locationNameListener;
    Context context;
    private String TAG = "PlaceSearchDialog";

    public interface LocationNameListener {
        public void locationName(String locationName);
    }

    public PlaceSearchDialog(Context context, LocationNameListener locationNameListener) {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.place_picker_dialog);
        this.context = context;
        this.locationNameListener = locationNameListener;
    }

    public void init() {

        locationET = (AppCompatAutoCompleteTextView) findViewById(R.id.location_ET);
        locationTIL = (TextInputLayout) findViewById(R.id.location_TIL);
        cancelTV = (AppCompatTextView) findViewById(R.id.cancelTV);
        okTV = (AppCompatTextView) findViewById(R.id.okTV);

        okTV.setOnClickListener(this);
        cancelTV.setOnClickListener(this);

        locationET.setOnItemClickListener(mAutocompleteClickListener);
        mAdapter = new PlaceAutocompleteAdapter(context, mGoogleApiClient, BOUNDS_WORLD, null);
        locationET.setThreshold(3);
        locationET.setAdapter(mAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            //Hide Keyboard
            InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getWindowToken(), 0);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);
        }
    };


    private void ok() {
        if (locationNameListener != null) {
            locationNameListener.locationName(locationET.getText().toString().trim());
        }
        dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                //.enableAutoManage(activity, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e(TAG, "Google API client connected");
        init();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Google API client suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(context,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.okTV) {
            ok();
        } else if (view.getId() == R.id.cancelTV) {
            dismiss();
        }
    }

}
