package ru.gcsales.app.dagger.cart;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.gcsales.app.data.repository.CartRepository;
import ru.gcsales.app.presentation.presenter.CartPresenter;

/**
 * {@link Module} for providing dependencies for shopping cart.
 *
 * @author Maxim Surovtsev
 * @since 09/04/2019
 */
@Module
public class CartModule {

    @Provides
    @Singleton
    public CartRepository provideListRepository(@NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        return new CartRepository(firestore, auth);
    }

    // FIXME: unscoped
    @Provides
    public CartPresenter provideListPresenter(@NonNull CartRepository repository) {
        return new CartPresenter(repository);
    }
}
