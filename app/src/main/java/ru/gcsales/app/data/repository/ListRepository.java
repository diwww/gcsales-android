package ru.gcsales.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
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

    private static final String TAG = "ListRepository";

    private static final String COLLECTION_PATH = "users/%s/items";
    private static final String DOCUMENT_PATH = "users/%s/items/%s";

    private final FirebaseAuth mAuth;

    public ListRepository(@NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        super(firestore);
        mAuth = auth;
    }

    /**
     * Adds an item to the shopping list.
     * If item is already present, then {@link ListRepository#incrementCount(ListEntry)} is called.
     *
     * @param item item to add
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable addItem(@NonNull Item item) {
        String uid = mAuth.getCurrentUser().getUid();

        return getDocument(String.format(DOCUMENT_PATH, uid, item.getId()))
                .map(this::convertDocumentSnapshot)
                .defaultIfEmpty(ListEntry.fromItem(item))
                .flatMap(entry -> incrementCount(entry).toMaybe())
                .ignoreElement();
    }

    /**
     * Increments count of shopping list entry.
     *
     * @param entry shopping list entry
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable incrementCount(@NonNull ListEntry entry) {
        String uid = mAuth.getCurrentUser().getUid();
        return setDocument(String.format(DOCUMENT_PATH, uid, entry.getId()), entry.incrementCount(), SetOptions.merge());
    }

    /**
     * Decrements count of shopping list entry or
     * deletes this entry if its count is less or equal to {@code 1}.
     *
     * @param entry shopping list entry
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable decrementCount(@NonNull ListEntry entry) {
        String uid = mAuth.getCurrentUser().getUid();

        if (entry.getCount() <= 1) {
            return deleteDocument(String.format(DOCUMENT_PATH, uid, entry.getId()));
        } else {
            return setDocument(String.format(DOCUMENT_PATH, uid, entry.getId()), entry.decrementCount(), SetOptions.merge());
        }
    }

    public ListenerRegistration addSnapshotListener(@NonNull EventListener<QuerySnapshot> listener) {
        String uid = mAuth.getCurrentUser().getUid();

        return mFirestore.collection(String.format(COLLECTION_PATH, uid)).orderBy("shop").orderBy("timestamp")
                .addSnapshotListener(listener);
    }

    private ListEntry convertDocumentSnapshot(DocumentSnapshot documentSnapshot) {
        ListEntry entry = documentSnapshot.toObject(ListEntry.class);
        entry.setId(documentSnapshot.getId());
        return entry;
    }
}
