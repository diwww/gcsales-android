package ru.gcsales.app.dagger.list;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import ru.gcsales.app.data.repository.ListRepository;

/**
 * {@link Module} for providing dependencies for shopping list.
 *
 * @author Maxim Surovtsev
 * @since 09/04/2019
 */
@Module
public class ListModule {

    @Provides
    @Singleton
    public ListRepository provideListRepository(@NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        return new ListRepository(firestore, auth);
    }
}
