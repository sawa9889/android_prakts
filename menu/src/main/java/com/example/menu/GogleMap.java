package com.example.menu;

import android.content.Context;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GogleMap.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GogleMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GogleMap extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GogleMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GogleMap.
     */
    // TODO: Rename and change types and number of parameters
    public static GogleMap newInstance() {
        GogleMap fragment = new GogleMap();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_gogle_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment locationMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map_fragment);
        if (locationMap != null) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null ) {
                locationMap.getMapAsync(this);
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private static final int overview = 0;

    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) {

        DateTime now = new DateTime();

        try {

            return DirectionsApi.newRequest(getGeoContext())

                    .mode(mode)

                    .origin(origin)

                    .destination(destination)

                    .departureTime(now)

                    .await();

        } catch (ApiException e) {

            e.printStackTrace();

            return null;

        } catch (InterruptedException e) {

            e.printStackTrace();

            return null;

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }

    }

    public void onMapReady(GoogleMap googleMap) {
        setupGoogleMapScreenSettings(googleMap);

        DirectionsResult results = getDirectionsDetails("55.680962, 37.717012","55.794141, 37.701635",TravelMode.DRIVING);
        System.out.println("Построен маршрут ");
        if (results != null) {
            System.out.println("Точно Построен маршрут ");
            addPolyline(results, googleMap);

            positionCamera(results.routes[overview], googleMap);

            addMarkersToMap(results, googleMap);

        }

    }



    private void setupGoogleMapScreenSettings(GoogleMap mMap) {

        mMap.setBuildingsEnabled(true);

        mMap.setIndoorEnabled(true);

        mMap.setTrafficEnabled(true);

        UiSettings mUiSettings = mMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);

        mUiSettings.setCompassEnabled(true);

        mUiSettings.setMyLocationButtonEnabled(true);

        mUiSettings.setScrollGesturesEnabled(true);

        mUiSettings.setZoomGesturesEnabled(true);

        mUiSettings.setTiltGesturesEnabled(true);

        mUiSettings.setRotateGesturesEnabled(true);

    }



    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].startLocation.lat,results.routes[overview].legs[overview].startLocation.lng)).title(results.routes[overview].legs[overview].startAddress));

        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].endLocation.lat,results.routes[overview].legs[overview].endLocation.lng)).title(results.routes[overview].legs[overview].startAddress).snippet(getEndLocationTitle(results)));

    }



    private void positionCamera(DirectionsRoute route, GoogleMap mMap) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 12));

    }



    private void addPolyline(DirectionsResult results, GoogleMap mMap) {

        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());

        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));

    }



    private String getEndLocationTitle(DirectionsResult results){

        return  "Time :"+ results.routes[overview].legs[overview].duration.humanReadable + " Distance :" + results.routes[overview].legs[overview].distance.humanReadable;

    }



    private GeoApiContext getGeoContext() {

        GeoApiContext geoApiContext = new GeoApiContext();

        return geoApiContext

                .setQueryRateLimit(3)

                .setApiKey("AIzaSyD_MK5RRKtSnO5Q4cPikVcbEICBSG-LxqU")

                .setConnectTimeout(1, TimeUnit.SECONDS)

                .setReadTimeout(1, TimeUnit.SECONDS)

                .setWriteTimeout(1, TimeUnit.SECONDS);

    }

}
