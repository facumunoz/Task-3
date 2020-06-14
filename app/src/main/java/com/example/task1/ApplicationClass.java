package com.example.task1;

import android.app.Application;

import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static ArrayList<Locations> locations;

    @Override
    public void onCreate() {
        super.onCreate();

        locations = new ArrayList<Locations>();
        locations.add(new Locations(41.6984196, -86.2362891));//debart
        locations.add(new Locations(41.702256, -86.237645));//lafun
        locations.add(new Locations(41.700782, -86.231959));//jordan
        locations.add(new Locations(41.699584, -86.241428));//sdh
        locations.add(new Locations(41.704641, -86.235425));//ndh
        locations.add(new Locations(41.702462, -86.234868));//hes

    }

}
