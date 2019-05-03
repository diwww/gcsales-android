package ru.gcsales.app.data.service;

import java.util.Map;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ru.gcsales.app.data.model.remote.PlacesResponse;

/**
 * Retrofit service for obtaining nearest places.
 *
 * @author Maxim Surovtsev
 * @since 03/05/2019
 */
public interface PlacesService {

    /**
     * Gets nearest places.
     *
     * @param params query params map
     * @return {@link Maybe} with the result of request
     */
    @GET("maps/api/place/nearbysearch/json")
    Maybe<PlacesResponse> getPlaces(@QueryMap Map<String, String> params);
}
