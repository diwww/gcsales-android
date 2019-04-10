package ru.gcsales.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import ru.gcsales.app.data.model.Item;
import ru.gcsales.app.data.model.ListEntry;

/**
 * Repository for managing user's shopping list.
 *
 * @author Maxim Surovtsev
 * @since 09/04/2019
 */
public class ListRepository extends RxFirestoreRepository {

    private static final String PATH = "users/%s/items/%s";

    private final FirebaseAuth mAuth;

    public ListRepository(@NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        super(firestore);
        mAuth = auth;
    }

    /**
     * Add an item to the shopping list.
     *
     * @param item item to add
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable addItem(@NonNull Item item) {
        String uid = mAuth.getCurrentUser().getUid();
        ListEntry entry = ListEntry.fromItem(item);
        return setDocument(String.format(PATH, uid, entry.getId()), entry, SetOptions.merge());
    }
}
