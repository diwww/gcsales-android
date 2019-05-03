package ru.gcsales.app.presentation.view.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import ru.gcsales.app.App;
import ru.gcsales.app.R;
import ru.gcsales.app.presentation.presenter.MapPresenter;

/**
 * Fragment with map of nearest shops.
 *
 * @author Maxim Surovtsev
 * @since 01/04/2019
 */
public class MapFlowFragment extends MvpAppCompatFragment implements MapFlowView, OnMapReadyCallback {

    public static final String TAG = "MapFragment";

    private static final String EXTRA_SHOP_NAME = "EXTRA_SHOP_NAME";
    private static final String[] LOCATION_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1337;
    private static final float DEFAULT_ZOOM = 15.0f;

    private ProgressBar mProgressBar;
    private MapView mMapView;
    private GoogleMap mMap;
    private String mShopName;

    @Inject
    Provider<MapPresenter> mPresenterProvider;

    @InjectPresenter
    MapPresenter mPresenter;

    /**
     * Creates a new instance of this fragment.
     *
     * @return new fragment instance
     */
    public static MapFlowFragment newInstance() {
        return new MapFlowFragment();
    }

    /**
     * Creates a new instance of this fragment.
     *
     * @param shop name of the shop
     * @return new fragment instance
     */
    public static MapFlowFragment newInstance(@NonNull String shop) {
        Bundle args = new Bundle();
        args.putString(EXTRA_SHOP_NAME, shop);
        MapFlowFragment fragment = new MapFlowFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        App.getComponent().inject(this);
        super.onAttach(context);
        if (getArguments() != null) {
            mShopName = getArguments().getString(EXTRA_SHOP_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mProgressBar = view.findViewById(R.id.progress_bar);
        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
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
        mMap = map;
        requestLocationPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onLocationPermissionGranted();
        } else {
            onLocationPermissionDenied();
        }
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable throwable) {
        Snackbar.make(getView(), R.string.loading_error, Snackbar.LENGTH_SHORT).show();
        Log.e(TAG, throwable.toString());
    }

    @Override
    public void setCurrentLocation(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    @Override
    public void addMarkers(@NonNull List<MarkerOptions> markerOptionsList) {
        for (MarkerOptions markerOptions : markerOptionsList) {
            mMap.addMarker(markerOptions);
        }
    }

    @ProvidePresenter
    MapPresenter providePresenter() {
        return mPresenterProvider.get();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(LOCATION_PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            onLocationPermissionGranted();
        }
    }

    @SuppressLint("MissingPermission")
    private void onLocationPermissionGranted() {
        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mPresenter.getCurrentLocation();
        mPresenter.getNearestShops(mShopName);
    }

    private void onLocationPermissionDenied() {
        Snackbar.make(getView(), R.string.location_permission_message, Snackbar.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }
}
