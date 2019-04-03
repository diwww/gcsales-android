package ru.gcsales.app.dagger.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.gcsales.app.dagger.shops.ShopsModule;
import ru.gcsales.app.presentation.view.main.MainActivity;
import ru.gcsales.app.presentation.view.main.SignInActivity;
import ru.gcsales.app.presentation.view.main.SplashScreenActivity;
import ru.gcsales.app.presentation.view.shops.ShopsFragment;

/**
 * Main dagger {@link Component component} of the application.
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
@Component(modules = {
        AppModule.class,
        // region Feature modules
        ShopsModule.class
        // endregion
})
@Singleton
public interface AppComponent {

    void inject(SplashScreenActivity activity);

    void inject(SignInActivity activity);

    void inject(MainActivity activity);

    void inject(ShopsFragment shopsFragment);
}
