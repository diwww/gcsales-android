package ru.gcsales.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import ru.gcsales.app.data.model.internal.ListEntry;

/**
 * Repository for managing user's grocery list.
 *
 * @author Maxim Surovtsev
 * @since 04/05/2019
 */
public class ListRepository extends RxFirestoreRepository {

    private static final String COLLECTION_PATH = "users/%s/list";
    private static final String DOCUMENT_PATH = "users/%s/list/%s";
    private static final String ORDER_BY_TIMESTAMP = "timestamp";

    private final FirebaseAuth mAuth;
    private ListenerRegistration mListenerRegistration;

    public ListRepository(@NonNull FirebaseFirestore firestore, @NonNull FirebaseAuth auth) {
        super(firestore);
        mAuth = auth;
    }

    /**
     * Returns {@link Observable} which emits grocery list entries every time update occurs.
     */
    public Observable<List<ListEntry>> getUpdateObservable() {
        String uid = mAuth.getCurrentUser().getUid();

        return Observable.create(emitter -> {
            mListenerRegistration = mFirestore
                    .collection(String.format(COLLECTION_PATH, uid))
                    .orderBy(ORDER_BY_TIMESTAMP)
                    .addSnapshotListener((querySnapshot, e) -> {
                        if (e != null && !emitter.isDisposed()) {
                            emitter.onError(e);
                        }

                        if (querySnapshot != null) {
                            List<ListEntry> entries = convertQuerySnapshot(querySnapshot);
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

    public Completable addEntry(@NonNull ListEntry entry) {
        String uid = mAuth.getCurrentUser().getUid();
        return addDocument(String.format(COLLECTION_PATH, uid), entry);
    }

    public Completable removeEntry(@NonNull ListEntry entry) {
        String uid = mAuth.getCurrentUser().getUid();
        return deleteDocument(String.format(DOCUMENT_PATH, uid, entry.getId()));
    }

    @NonNull
    private List<ListEntry> convertQuerySnapshot(@NonNull QuerySnapshot querySnapshot) {
        List<ListEntry> entries = new ArrayList<>(querySnapshot.size());
        for (QueryDocumentSnapshot snapshot : querySnapshot) {
            entries.add(convertDocumentSnapshot(snapshot));
        }
        return entries;
    }

    @NonNull
    private ListEntry convertDocumentSnapshot(QueryDocumentSnapshot snapshot) {
        ListEntry entry = snapshot.toObject(ListEntry.class);
        entry.setId(snapshot.getId());
        return entry;
    }
}
