package ru.gcsales.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import ru.gcsales.app.data.model.Item;
import ru.gcsales.app.data.model.ListEntry;
import ru.gcsales.app.data.model.Shop;

/**
 * Repository for managing user's shopping list.
 *
 * @author Maxim Surovtsev
 * @since 09/04/2019
 */
public class ListRepository extends RxFirestoreRepository {

    private static final String COLLECTION_PATH = "users/%s/items";
    private static final String DOCUMENT_PATH = "users/%s/items/%s";
    private static final String ORDER_BY_SHOP = "shop";
    private static final String ORDER_BY_TIMESTAMP = "timestamp";

    private final FirebaseAuth mAuth;
    private ListenerRegistration mListenerRegistration;

    public ListRepository(@NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        super(firestore);
        mAuth = auth;
    }

    /**
     * Gets shopping list entries.
     *
     * @return {@link Maybe} with the result
     */
    public Maybe<List<ListEntry>> getEntries() {
        String uid = mAuth.getCurrentUser().getUid();

        return getCollection(String.format(COLLECTION_PATH, uid))
                .map(this::convertQuerySnapshot);
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

    /**
     * Returns {@link Observable} which emits shopping list entries every time update occurs.
     */
    public Observable<List<ListEntry>> getUpdateObservable() {
        String uid = mAuth.getCurrentUser().getUid();

        return Observable.create(emitter -> {
            mListenerRegistration = mFirestore
                    .collection(String.format(COLLECTION_PATH, uid))
                    .orderBy(ORDER_BY_SHOP)
                    .orderBy(ORDER_BY_TIMESTAMP)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                            if (e != null && !emitter.isDisposed()) {
                                emitter.onError(e);
                            }

                            if (querySnapshot != null) {
                                List<ListEntry> entries = processEntries(convertQuerySnapshot(querySnapshot));
                                emitter.onNext(entries);
                            }
                        }
                    });

            emitter.setDisposable(new Disposable() {
                @Override
                public void dispose() {
                    mListenerRegistration.remove();
                    mListenerRegistration = null;
                }

                @Override
                public boolean isDisposed() {
                    return mListenerRegistration == null;
                }
            });
        });
    }

    @Nullable
    private ListEntry convertDocumentSnapshot(@NonNull DocumentSnapshot documentSnapshot) {
        ListEntry entry = documentSnapshot.toObject(ListEntry.class);
        if (entry != null) {
            entry.setId(documentSnapshot.getId());
        }
        return entry;
    }

    @NonNull
    private List<ListEntry> convertQuerySnapshot(@NonNull QuerySnapshot querySnapshot) {
        List<ListEntry> entries = new ArrayList<>(querySnapshot.size());
        for (QueryDocumentSnapshot snapshot : querySnapshot) {
            entries.add(convertDocumentSnapshot(snapshot));
        }
        return entries;
    }

    private List<ListEntry> processEntries(@Nonnull List<ListEntry> entries) {
        Iterator<ListEntry> iterator = entries.iterator();
        String currentShop = null;

        if (iterator.hasNext()) {
            ListEntry entry = iterator.next();
            currentShop = entry.getShop();
            entry.setShowShop(true);
        }
        while (iterator.hasNext()) {
            ListEntry entry = iterator.next();
            String shop = entry.getShop();
            if (!shop.equals(currentShop)) {
                currentShop = shop;
                entry.setShowShop(true);
            }
        }

        return entries;
    }
}
