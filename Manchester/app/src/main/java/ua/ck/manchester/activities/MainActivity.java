package ua.ck.manchester.activities;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.widget.Toast;

import ua.ck.manchester.R;
import ua.ck.manchester.fragments.TouchableMapFragment;
import ua.ck.manchester.utils.MapWrapperLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends LocationActivity implements OnMapReadyCallback, LocationActivity.Callback {

    private TouchableMapFragment mapFragment;
    private GoogleMap map;
    private Marker toMarker, fromMarker;
    private Location myLocation;
    private boolean launch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setCallback(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = new TouchableMapFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, mapFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);

//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 17f));
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
//                if (toWrapper.isSelected()) {
//                    drawLineToCameraPosition();
//                } else {
//                    drawLineFromCameraPosition();
//                }
            }
        });
        mapFragment.setOnDragListener(new MapWrapperLayout.OnDragListener() {
            @Override
            public void onDrag(MotionEvent motionEvent) {
//                if (toWrapper.isSelected()) {
//                    drawLineToCameraPosition();
//                } else {
//                    drawLineFromCameraPosition();
//                }
            }
        });

//        toMarker = map.addMarker(new MarkerOptions().position(new LatLng(goToPlace.getGeometry().getLocation().getLat(), goToPlace.getGeometry().getLocation().getLng())).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.to_map_icon_inactive)));

//        fromWrapper.setSelected(true);
    }

    @Override
    public void connected(Location location) {
        if (launch) {
            if (location != null ) {
                myLocation = location;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    //            nearestStopsWidget.setLocation(latLng);
    //            stopDetailWidget.setLocation(latLng);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17f));
                fromMarker = map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.mipmap.start_route_pin)));
            }else{
                Toast.makeText(MainActivity.this, "Location is on available. Please, turn GPS on", Toast.LENGTH_LONG).show();
            }
            launch = false;
        } else {
            if (location != null ) {
//                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                mapFragment.setCompassLocation(latLng);
//                mapFragment.setNewNearestLocation(location);
            }
        }
    }

//    private synchronized void drawLineFromCameraPosition() {
//        goFromPlace = null;
//        final LatLng fromPosition = map.getCameraPosition().target;
//        if (polyline != null) {
//            polyline.remove();
//        }
//        polyline = map.addPolyline(new PolylineOptions()
//                        .width(6)
//                        .geodesic(true)
//                        .color(getResources().getColor(R.color.route_part_walking))
//                        .add(fromPosition)
//                        .add(toMarker.getPosition())
//        );
//        if (fromMarker != null) {
//            fromMarker.setPosition(fromPosition);
//        } else {
//            fromMarker = map.addMarker(new MarkerOptions().position(fromPosition).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.from_map_icon_active)));
//        }
//        if (timer != null) {
//            timer.cancel();
//        }
//        fromWrapper.setText(getString(R.string.moving));
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                fromWrapper.setText(getString(R.string.obtaining_address));
//                final GoogleReverseGeocodingTask task = new GoogleReverseGeocodingTask(LetsGoFromActivity.this, fromPosition);
//                task.setCallback(new BaseTaskMaterial.Callback() {
//                    @Override
//                    public void successful() {
//                        goFromPlace = task.getPlace();
//                        fromWrapper.setText(goFromPlace.getVicinity());
//                    }
//
//                    @Override
//                    public void failed() {
//                        SystemUtils.toast(LetsGoFromActivity.this, R.string.something_was_wrong_msg);
//                    }
//                });
//                task.execute();
//            }
//        }, GEOCODE_DELAY);
//    }

//    private synchronized void drawLineToCameraPosition() {
//        goToPlace = null;
//        final LatLng toPosition = map.getCameraPosition().target;
//        if (polyline != null) {
//            polyline.remove();
//        }
//        polyline = map.addPolyline(new PolylineOptions()
//                        .width(6)
//                        .geodesic(true)
//                        .color(getResources().getColor(R.color.route_part_walking))
//                        .add(toPosition)
//                        .add(fromMarker.getPosition())
//        );
//        if (toMarker != null) {
//            toMarker.setPosition(toPosition);
//        } else {
//            toMarker = map.addMarker(new MarkerOptions().position(toPosition).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.to_map_icon_active)));
//        }
//        if (timer != null) {
//            timer.cancel();
//        }
//        toWrapper.setText(getString(R.string.moving));
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toWrapper.setText(getString(R.string.obtaining_address));
//                final GoogleReverseGeocodingTask task = new GoogleReverseGeocodingTask(LetsGoFromActivity.this, toPosition);
//                task.setCallback(new BaseTaskMaterial.Callback() {
//                    @Override
//                    public void successful() {
//                        goToPlace = task.getPlace();
//                        toWrapper.setText(goToPlace.getVicinity());
//                    }
//
//                    @Override
//                    public void failed() {
//                        SystemUtils.toast(LetsGoFromActivity.this, R.string.something_was_wrong_msg);
//                    }
//                });
//                task.execute();
//            }
//        }, GEOCODE_DELAY);
//    }

//    private synchronized void drawLineToPosition(GooglePlace place) {
//        final LatLng toPosition = new LatLng(place.getGeometry().getLocation().getLat(), place.getGeometry().getLocation().getLng());
//        if (polyline != null) {
//            polyline.remove();
//        }
//        polyline = map.addPolyline(new PolylineOptions()
//                        .width(6)
//                        .geodesic(true)
//                        .color(getResources().getColor(R.color.route_part_walking))
//                        .add(toPosition)
//                        .add(toMarker.getPosition())
//        );
//        if (toMarker != null) {
//            toMarker.setPosition(toPosition);
//        } else {
//            toMarker = map.addMarker(new MarkerOptions().position(toPosition).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.to_map_icon_active)));
//        }
//        goToPlace = place;
//        //TODO fix obtain address after move camera
//        map.setOnCameraChangeListener(null);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(toPosition, map.getCameraPosition().zoom));
//        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//            @Override
//            public void onCameraChange(CameraPosition cameraPosition) {
////                if (toWrapper.isSelected()) {
////                    drawLineToCameraPosition();
////                } else {
////                    drawLineFromCameraPosition();
////                }
//            }
//        });
//    }

//    private synchronized void drawLineFromPosition(GooglePlace place) {
//        final LatLng fromPosition = new LatLng(place.getGeometry().getLocation().getLat(), place.getGeometry().getLocation().getLng());
//        if (polyline != null) {
//            polyline.remove();
//        }
//        polyline = map.addPolyline(new PolylineOptions()
//                        .width(6)
//                        .geodesic(true)
//                        .color(getResources().getColor(R.color.route_part_walking))
//                        .add(fromPosition)
//                        .add(toMarker.getPosition())
//        );
//        if (fromMarker != null) {
//            fromMarker.setPosition(fromPosition);
//        } else {
//            fromMarker = map.addMarker(new MarkerOptions().position(fromPosition).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.from_map_icon_active)));
//        }
//        goFromPlace = place;
//        //TODO fix obtain address after move camera
//        map.setOnCameraChangeListener(null);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(fromPosition, map.getCameraPosition().zoom));
//        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//            @Override
//            public void onCameraChange(CameraPosition cameraPosition) {
//                if (toWrapper.isSelected()) {
//                    drawLineToCameraPosition();
//                } else {
//                    drawLineFromCameraPosition();
//                }
//            }
//        });
//    }
}
