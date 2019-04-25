package ru.gcsales.app.dagger.app;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.gcsales.app.data.manager.AuthManager;
import ru.gcsales.app.presentation.Router;

/**
 * {@link Module} which provides global app dependencies.
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
@Module
public class AppModule {

    private final Context mContext;
    private final FirebaseFirestore mFirestore;
    private final FirebaseAuth mAuth;

    public AppModule(@NonNull Context context, @NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        mContext = context;
        mFirestore = firestore;
        mAuth = auth;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    public Router provideRouter() {
        return new Router();
    }

    @Provides
    @Singleton
    public AuthManager provideAuthManager(@NonNull Context context, @NonNull FirebaseAuth auth) {
        return new AuthManager(context, auth);
    }

    @Provides
    @Singleton
    public FirebaseFirestore provideFirestore() {
        return mFirestore;
    }

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        return mAuth;
    }
}
