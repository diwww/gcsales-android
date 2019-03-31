package ru.gcsales.app;

import android.app.Application;

import ru.gcsales.app.dagger.AppComponent;
import ru.gcsales.app.dagger.AppModule;
import ru.gcsales.app.dagger.DaggerAppComponent;

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
                .appModule(new AppModule(this))
                .build();
    }
}
