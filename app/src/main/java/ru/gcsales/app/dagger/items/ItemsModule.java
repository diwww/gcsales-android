package ru.gcsales.app.dagger.items;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.gcsales.app.data.repository.ItemsRepository;
import ru.gcsales.app.data.repository.ListRepository;
import ru.gcsales.app.presentation.presenter.items.ItemsPresenter;

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
    public ItemsPresenter provideItemsPresenter(@NonNull ItemsRepository itemsRepository, @NonNull ListRepository listRepository) {
        return new ItemsPresenter(itemsRepository, listRepository);
    }
}
