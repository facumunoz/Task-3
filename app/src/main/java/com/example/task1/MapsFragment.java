package com.example.task1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.xml.namespace.QName;

public class MapsFragment extends Fragment {

    FusedLocationProviderClient client;
    private GoogleMap mMap;
    List<Address> addresses;
    TextView tvInfo;
    ArrayList<Address> addressArrayList = new ArrayList<Address>();
    //ArrayList<String> names = new ArrayList<String>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        client = LocationServices.getFusedLocationProviderClient(this.getActivity());
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
        else {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.action_hybridd:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.action_satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.action_terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }

        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_maps, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

            private void getCurrentLocation() {

            Task<Location > task = client.getLastLocation();

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                SupportMapFragment mapFragment =
                        (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                if (location != null) {
                    assert mapFragment != null;
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();

                            mMap = googleMap;
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));

                            for (int i = 0; i < ApplicationClass.locations.size(); i += 1) {
                                try {
                                    addresses = geocoder.getFromLocation(ApplicationClass.locations.get(i).getLatitude(), ApplicationClass.locations.get(i).getLongitude(), 1);
                                    Address address = addresses.get(0);
                                    addressArrayList.add(address);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .icon(BitmapDescriptorFactory.defaultMarker((float) Math.random() * 300))
                                        .anchor(0.0f, 1.0f)
                                        .title(addressArrayList.get(i).getFeatureName())
                                        .position(new LatLng(ApplicationClass.locations.get(i).getLatitude(), ApplicationClass.locations.get(i).getLongitude())));
                                marker.showInfoWindow();
                                marker.setTag(i);

                                builder.include(new LatLng(ApplicationClass.locations.get(i).getLatitude(), ApplicationClass.locations.get(i).getLongitude()));

                            }

                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    int tag = (int) marker.getTag();
                                    Intent intent = new Intent(getActivity(), com.example.task1.LocationInfo.class);
                                    intent.putExtra("Name", marker.getTitle());
                                    intent.putExtra("Phone", addressArrayList.get(tag).getPhone());
                                    intent.putExtra("Url", addressArrayList.get(tag).getUrl());
                                    startActivity(intent);

                                }
                            });

                            builder.include(latLng);
                            LatLngBounds bounds = builder.build();
                            int padding = 40;
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                            mMap.animateCamera(cu);
                        }
                    });
                } else {
                    assert mapFragment != null;
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                            mMap = googleMap;

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();


                            for (int i = 0; i < ApplicationClass.locations.size(); i += 1) {
                                try {
                                    addresses = geocoder.getFromLocation(ApplicationClass.locations.get(i).getLatitude(), ApplicationClass.locations.get(i).getLongitude(), 1);
                                    Address address = addresses.get(0);
                                    addressArrayList.add(address);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .icon(BitmapDescriptorFactory.defaultMarker((float) Math.random() * 300))
                                        .anchor(0.0f, 1.0f)
                                        .title(addressArrayList.get(i).getFeatureName())
                                        .position(new LatLng(ApplicationClass.locations.get(i).getLatitude(), ApplicationClass.locations.get(i).getLongitude())));
                                marker.showInfoWindow();
                                marker.setTag(i);

                                builder.include(new LatLng(ApplicationClass.locations.get(i).getLatitude(), ApplicationClass.locations.get(i).getLongitude()));

                            }

                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    int tag = (int) marker.getTag();
                                    Intent intent = new Intent(getActivity(), com.example.task1.LocationInfo.class);
                                    intent.putExtra("Name", marker.getTitle());
                                    intent.putExtra("Phone", addressArrayList.get(tag).getPhone());
                                    intent.putExtra("Url", addressArrayList.get(tag).getUrl());
                                    startActivity(intent);

                                }
                            });


                            LatLngBounds bounds = builder.build();
                            int padding = 40;
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                            mMap.animateCamera(cu);

                        }
                    });

                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation();
            }
        }
    }

}


