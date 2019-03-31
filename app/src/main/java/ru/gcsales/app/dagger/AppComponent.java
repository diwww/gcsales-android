package ru.gcsales.app.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.gcsales.app.MainActivity;
import ru.gcsales.app.SignInActivity;
import ru.gcsales.app.SplashScreenActivity;

/**
 * Main dagger {@link Component component} of the application.
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
@Component(modules = {AppModule.class, AuthModule.class})
@Singleton
public interface AppComponent {

    void inject(SplashScreenActivity activity);

    void inject(SignInActivity activity);

    void inject(MainActivity activity);
}
