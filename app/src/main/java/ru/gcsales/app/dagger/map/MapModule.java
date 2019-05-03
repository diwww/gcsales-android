package ru.gcsales.app.dagger.map;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.gcsales.app.R;
import ru.gcsales.app.data.repository.MapRepository;
import ru.gcsales.app.data.service.PlacesService;
import ru.gcsales.app.presentation.presenter.MapPresenter;

/**
 * {@link Module} which provides dependencies for place searching.
 *
 * @author Maxim Surovtsev
 * @since 03/05/2019
 */
@Module
public class MapModule {

    @Provides
    @Singleton
    public PlacesService providePlacesService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(PlacesService.class);
    }

    @Provides
    @Singleton
    public MapRepository providePlacesRepository(@NonNull Context context,
                                                 @NonNull PlacesService service,
                                                 @NonNull FusedLocationProviderClient client) {
        final String apiKey = context.getString(R.string.google_maps_key);
        return new MapRepository(service, client, apiKey);
    }

    @Provides
    @Singleton
    public FusedLocationProviderClient provideFusedLocationProviderClient(@NonNull Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    // FIXME: unscoped
    @Provides
    public MapPresenter provideMapPresenter(@NonNull MapRepository repository) {
        return new MapPresenter(repository);
    }
}
