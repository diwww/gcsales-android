package ru.gcsales.app.presentation.view.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import ru.gcsales.app.R;

/**
 * Fragment with map of nearest shops.
 *
 * @author Maxim Surovtsev
 * @since 01/04/2019
 */
public class MapFlowFragment extends Fragment implements OnMapReadyCallback {

    public static final String TAG = "MapFragment";

    private MapView mMapView;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static MapFlowFragment newInstance() {
        return new MapFlowFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        map.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
