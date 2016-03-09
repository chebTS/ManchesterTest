package ua.ck.manchester.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by eKreative on 7/1/14.
 */
public abstract class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener {

	private static final String TAG = LocationActivity.class.getSimpleName();
	private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101;
	protected Location currentLocation;
	private GoogleApiClient googleApiClient;
	private LocationRequest locationRequest = new LocationRequest();
	boolean requestingLocationUpdate = true;
	private Callback callback;
	public interface Callback {
		void connected(Location currentLocation);
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		locationRequest.setInterval(10000);
		locationRequest.setFastestInterval(5000);
//		locationRequest.setSmallestDisplacement(10f);
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	public static boolean isLocationEnabled(Context context) {
		int locationMode = 0;
		String locationProviders;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			try {
				locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
			} catch (Settings.SettingNotFoundException e) {
				e.printStackTrace();
			}
			return locationMode != Settings.Secure.LOCATION_MODE_OFF;
		}else{
			locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			return !TextUtils.isEmpty(locationProviders);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		googleApiClient.connect();
	}

	@Override
	public void onConnected(Bundle bundle) {
		currentLocation = getLocation();
		if(requestingLocationUpdate){
			if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

				ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

				// MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			} else {
				startLocationUpdate();
			}
		}
		if (callback != null) {
			callback.connected(currentLocation);
		}
	}

	private void startLocationUpdate() {
		LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,this);
	}

	private void stopLocationUpdate() {
		try {
			LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
		} catch (Exception e) {
			Log.e(TAG, "stopLocationUpdate", e);
		}
	}

	private Location getLocation() {
		return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.d(TAG, "connection has been suspended");
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.d(TAG, "not connected with GoogleApiClient");
	}

	@Override
	protected void onStop() {
		super.onStop();
		stopLocationUpdate();
		googleApiClient.disconnect();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(googleApiClient.isConnected()){
			startLocationUpdate();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		currentLocation = location;
		if (googleApiClient.isConnected()) {
			startLocationUpdate();
		}
		if (callback != null) {
			callback.connected(currentLocation);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					// permission was granted, yay! Do the task you need to do.
					startLocationUpdate();

				} else {
					finish();
					// permission denied, boo! Disable the functionality that depends on this permission.
				}
				return;
			}
		}
	}


	public Location getCurrentLocation() {
		return currentLocation;
	}
}