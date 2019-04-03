package ru.gcsales.app.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.gcsales.app.AuthManager;

/**
 * {@link Module} which provides Firebase authentication classes.
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
@Module
public class AuthModule {

    @Provides
    @Singleton
    public AuthManager provideAuthManager(@NonNull Context context) {
        return new AuthManager(context);
    }
}
