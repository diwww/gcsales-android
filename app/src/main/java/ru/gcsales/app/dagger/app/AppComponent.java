package ru.gcsales.app.dagger.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.gcsales.app.dagger.items.ItemsModule;
import ru.gcsales.app.dagger.list.ListModule;
import ru.gcsales.app.dagger.shops.ShopsModule;
import ru.gcsales.app.presentation.view.AppActivity;
import ru.gcsales.app.presentation.view.items.ItemsFlowFragment;
import ru.gcsales.app.presentation.view.list.ListFragment;
import ru.gcsales.app.presentation.view.main.MainFlowFragment;
import ru.gcsales.app.presentation.view.shops.ShopsFragment;
import ru.gcsales.app.presentation.view.signin.SignInFlowFragment;

/**
 * Main dagger {@link Component component} of the application.
 *
 * @author Maxim Surovtsev
 * @since 31/03/2019
 */
@Component(modules = {
        AppModule.class,
        // region Feature modules
        ShopsModule.class,
        ItemsModule.class,
        ListModule.class
        // endregion
})
@Singleton
public interface AppComponent {

    void inject(AppActivity activity);

    void inject(SignInFlowFragment fragment);

    void inject(MainFlowFragment fragment);

    void inject(ShopsFragment fragment);

    void inject(ItemsFlowFragment fragment);

    void inject(ListFragment fragment);
}
