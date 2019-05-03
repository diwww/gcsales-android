package ru.gcsales.app.presentation.presenter;

import android.location.Location;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.gcsales.app.data.model.internal.Place;
import ru.gcsales.app.data.repository.MapRepository;
import ru.gcsales.app.presentation.view.map.MapFlowView;

/**
 * MVP presenter for map screen.
 *
 * @author Maxim Surovtsev
 * @since 03/05/2019
 */
@InjectViewState
public class MapPresenter extends MvpPresenter<MapFlowView> {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final MapRepository mMapRepository;

    public MapPresenter(@NonNull MapRepository mapRepository) {
        mMapRepository = mapRepository;
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    public void getCurrentLocation() {
        Disposable disposable = mMapRepository.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLocationLoaded, this::onError);
        mCompositeDisposable.add(disposable);
    }

    public void getNearestShops(@NonNull String shop) {
        Disposable disposable = mMapRepository.getPlaces(shop)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().showProgress(true))
                .doOnEvent((__, ___) -> getViewState().showProgress(false))
                .subscribe(this::onPlacesLoaded, this::onError);
        mCompositeDisposable.add(disposable);
    }

    private void onLocationLoaded(Location location) {
        getViewState().setCurrentLocation(location);
    }

    private void onPlacesLoaded(List<Place> places) {
        List<MarkerOptions> markerOptionsList = new ArrayList<>();

        for (Place place : places) {
            markerOptionsList.add(new MarkerOptions()
                    .position(new LatLng(place.getLatitude(), place.getLongitude()))
                    .title(place.getName())
                    .snippet(place.getAddress()));
        }

        getViewState().addMarkers(markerOptionsList);
    }

    private void onError(Throwable throwable) {
        getViewState().showError(throwable);
    }
}
