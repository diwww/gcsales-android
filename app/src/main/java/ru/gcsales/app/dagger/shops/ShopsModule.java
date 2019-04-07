package ru.gcsales.app.dagger.shops;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.gcsales.app.data.repository.ShopsRepository;
import ru.gcsales.app.presentation.presenter.shops.ShopsPresenter;

/**
 * {@link Module} which provides dependencies for shops screen.
 *
 * @author Maxim Surovtsev
 * @since 03/04/2019
 */
@Module
public class ShopsModule {

    @Provides
    @Singleton
    public ShopsRepository provideShopsRepository() {
        return new ShopsRepository(FirebaseFirestore.getInstance());
    }

    // FIXME: unscoped
    @Provides
    public ShopsPresenter provideShopsPresenter(@NonNull ShopsRepository repository) {
        return new ShopsPresenter(repository);
    }
}
