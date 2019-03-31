package ru.gcsales.app.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * {@link Module} which provides {@link Context}.
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
@Module
public class AppModule {

    private final Context mContext;

    public AppModule(@NonNull Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }
}
