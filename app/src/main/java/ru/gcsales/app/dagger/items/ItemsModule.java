package ru.gcsales.app.dagger.items;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.gcsales.app.data.repository.ItemsRepository;
import ru.gcsales.app.data.repository.CartRepository;
import ru.gcsales.app.presentation.presenter.ItemsPresenter;

/**
 * {@link Module} which provides dependencies for items screen.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
@Module
public class ItemsModule {

    @Provides
    @Singleton
    public ItemsRepository provideItemsRepository(@Nonnull FirebaseFirestore firestore) {
        return new ItemsRepository(firestore);
    }

    // FIXME: unscoped
    @Provides
    public ItemsPresenter provideItemsPresenter(@NonNull ItemsRepository itemsRepository, @NonNull CartRepository cartRepository) {
        return new ItemsPresenter(itemsRepository, cartRepository);
    }
}
