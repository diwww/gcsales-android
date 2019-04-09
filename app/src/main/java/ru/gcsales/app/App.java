package ru.gcsales.app;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ru.gcsales.app.dagger.app.AppComponent;
import ru.gcsales.app.dagger.app.AppModule;
import ru.gcsales.app.dagger.app.DaggerAppComponent;

/**
 * Main {@link Application}.
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
public class App extends Application {

    private static AppComponent sComponent;

    public static AppComponent getComponent() {
        return sComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, FirebaseFirestore.getInstance(), FirebaseAuth.getInstance()))
                .build();
    }
}
