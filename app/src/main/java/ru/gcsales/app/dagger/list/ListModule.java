package ru.gcsales.app.dagger.list;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.gcsales.app.data.repository.ListRepository;
import ru.gcsales.app.presentation.presenter.ListPresenter;

/**
 * {@link Module} for providing dependencies for grocery list.
 *
 * @author Maxim Surovtsev
 * @since 04/05/2019
 */
@Module
public class ListModule {

    @Provides
    @Singleton
    public ListRepository provideListRepository(@NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        return new ListRepository(firestore, auth);
    }

    // FIXME: unscoped
    @Provides
    public ListPresenter provideListPresenter(@NonNull ListRepository repository) {
        return new ListPresenter(repository);
    }
}
