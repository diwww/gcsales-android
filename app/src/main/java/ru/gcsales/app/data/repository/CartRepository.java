package ru.gcsales.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import ru.gcsales.app.data.model.internal.CartEntry;
import ru.gcsales.app.data.model.internal.Item;

/**
 * Repository for managing user's shopping cart.
 *
 * @author Maxim Surovtsev
 * @since 09/04/2019
 */
public class CartRepository extends RxFirestoreRepository {

    private static final String COLLECTION_PATH = "users/%s/items";
    private static final String DOCUMENT_PATH = "users/%s/items/%s";
    private static final String ORDER_BY_SHOP = "shop";
    private static final String ORDER_BY_TIMESTAMP = "timestamp";

    private final FirebaseAuth mAuth;
    private ListenerRegistration mListenerRegistration;

    public CartRepository(@NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        super(firestore);
        mAuth = auth;
    }

    /**
     * Adds an item to the shopping cart.
     * If item is already present, then {@link CartRepository#incrementCount(CartEntry)} is called.
     *
     * @param item item to add
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable addItem(@NonNull Item item) {
        String uid = mAuth.getCurrentUser().getUid();

        return getDocument(String.format(DOCUMENT_PATH, uid, item.getId()))
                .map(this::convertDocumentSnapshot)
                .flatMap(this::checkEndDate)
                .defaultIfEmpty(CartEntry.fromItem(item))
                .flatMap(entry -> incrementCount(entry).toMaybe())
                .ignoreElement();
    }

    /**
     * Increments count of shopping cart entry.
     *
     * @param entry shopping cart entry
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable incrementCount(@NonNull CartEntry entry) {
        String uid = mAuth.getCurrentUser().getUid();
        return setDocument(String.format(DOCUMENT_PATH, uid, entry.getId()), entry.incrementCount(), SetOptions.merge());
    }

    /**
     * Decrements count of shopping cart entry or
     * deletes this entry if its count is less or equal to {@code 1}.
     *
     * @param entry shopping cart entry
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable decrementCount(@NonNull CartEntry entry) {
        String uid = mAuth.getCurrentUser().getUid();

        if (entry.getCount() <= 1) {
            return deleteDocument(String.format(DOCUMENT_PATH, uid, entry.getId()));
        } else {
            return setDocument(String.format(DOCUMENT_PATH, uid, entry.getId()), entry.decrementCount(), SetOptions.merge());
        }
    }

    /**
     * Returns {@link Observable} which emits shopping cart entries every time update occurs.
     */
    public Observable<List<CartEntry>> getUpdateObservable() {
        String uid = mAuth.getCurrentUser().getUid();

        return Observable.create(emitter -> {
            mListenerRegistration = mFirestore
                    .collection(String.format(COLLECTION_PATH, uid))
                    .orderBy(ORDER_BY_SHOP)
                    .orderBy(ORDER_BY_TIMESTAMP)
                    .addSnapshotListener((querySnapshot, e) -> {
                        if (e != null && !emitter.isDisposed()) {
                            emitter.onError(e);
                        }

                        if (querySnapshot != null) {
                            List<CartEntry> entries = processEntries(convertQuerySnapshot(querySnapshot));
                            emitter.onNext(entries);
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

    /**
     * Checks whether entry is expired.
     * <p>And if so returns empty result and this causes cart entry recreation
     * with new end date.
     * <p>This method is needed in case of discounted item was reissued and
     * now has the new end date.
     *
     * @param entry cart entry
     * @return {@link Maybe} with the result
     */
    @NonNull
    private Maybe<CartEntry> checkEndDate(@NonNull CartEntry entry) {
        LocalDate endDate = LocalDate.fromDateFields(entry.getEndDate());
        LocalDate today = LocalDate.now();
        if (today.compareTo(endDate) < 0) {
            return Maybe.just(entry);
        } else {
            return Maybe.empty();
        }
    }

    @Nullable
    private CartEntry convertDocumentSnapshot(@NonNull DocumentSnapshot documentSnapshot) {
        CartEntry entry = documentSnapshot.toObject(CartEntry.class);
        if (entry != null) {
            entry.setId(documentSnapshot.getId());
        }
        return entry;
    }

    @NonNull
    private List<CartEntry> convertQuerySnapshot(@NonNull QuerySnapshot querySnapshot) {
        List<CartEntry> entries = new ArrayList<>(querySnapshot.size());
        for (QueryDocumentSnapshot snapshot : querySnapshot) {
            entries.add(convertDocumentSnapshot(snapshot));
        }
        return entries;
    }

    private List<CartEntry> processEntries(@NonNull List<CartEntry> entries) {
        Iterator<CartEntry> iterator = entries.iterator();
        String currentShop = null;

        if (iterator.hasNext()) {
            CartEntry entry = iterator.next();
            currentShop = entry.getShop();
            entry.setShowShop(true);
        }
        while (iterator.hasNext()) {
            CartEntry entry = iterator.next();
            String shop = entry.getShop();
            if (!shop.equals(currentShop)) {
                currentShop = shop;
                entry.setShowShop(true);
            }
        }

        return entries;
    }
}
