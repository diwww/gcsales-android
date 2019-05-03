package ru.gcsales.app.data.repository;

import android.annotation.SuppressLint;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import ru.gcsales.app.data.model.internal.Place;
import ru.gcsales.app.data.model.remote.PlacesResponse;
import ru.gcsales.app.data.model.remote.Result;
import ru.gcsales.app.data.service.PlacesService;

/**
 * Repository implementation for getting current location
 * and list of nearest places (shops).
 *
 * @author Maxim Surovtsev
 * @since 03/05/2019
 */
public class MapRepository {

    private static final String KEY_RADIUS = "radius";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_KEYWORD = "keyword";
    private static final String KEY_KEY = "key";
    private static final String VALUE_RADIUS = "5000";

    private final PlacesService mPlacesService;
    private final FusedLocationProviderClient mFusedLocationProviderClient;
    private final String mApiKey;

    public MapRepository(@NonNull PlacesService service,
                         @NonNull FusedLocationProviderClient client,
                         @NonNull String apiKey) {
        mPlacesService = service;
        mFusedLocationProviderClient = client;
        mApiKey = apiKey;
    }

    @SuppressLint("MissingPermission")
    public Maybe<Location> getCurrentLocation() {
        return Maybe.create(emitter -> {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            emitter.onSuccess(location);
                        } else {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    public Maybe<List<Place>> getPlaces(@NonNull String keyword) {
        return getCurrentLocation()
                // FIXME: why change thread here?
                .observeOn(Schedulers.io())
                .flatMap(location -> {
                    Map<String, String> params = new HashMap<>();
                    params.put(KEY_RADIUS, VALUE_RADIUS);
                    params.put(KEY_LANGUAGE, getLanguageQueryParam());
                    params.put(KEY_LOCATION, getLocationQueryParam(location));
                    params.put(KEY_KEYWORD, keyword);
                    params.put(KEY_KEY, mApiKey);
                    return mPlacesService.getPlaces(params);
                })
                .map(this::convertResponse);
    }

    @NonNull
    private String getLanguageQueryParam() {
        String language = Locale.getDefault().getLanguage();
        return !language.equals("") ? language : "en";
    }

    @NonNull
    private String getLocationQueryParam(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        return String.format(Locale.US, "%f,%f", lat, lng);
    }

    @NonNull
    private List<Place> convertResponse(@NonNull PlacesResponse response) {
        List<Place> places = new ArrayList<>();
        List<Result> results = response.getResults();
        if (results != null) {
            for (Result result : results) {
                Place place = new Place();
                place.setLatitude(result.getGeometry().getLocation().getLat());
                place.setLongitude(result.getGeometry().getLocation().getLng());
                place.setName(result.getName());
                place.setAddress(result.getVicinity());
                places.add(place);
            }
        }
        return places;
    }
}
