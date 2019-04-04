package ru.gcsales.app.data.repository;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import io.reactivex.Maybe;

/**
 * RxJava wrapper for Firebase Firestore.
 *
 * @author Maxim Surovtsev
 * @since 04/04/2019
 */
public class RxFirestoreRepository {

    private final FirebaseFirestore mFirestore;

    public RxFirestoreRepository(@NonNull FirebaseFirestore firestore) {
        mFirestore = firestore;
    }

    /**
     * Gets firestore collection and wraps the result in {@link Maybe}.
     * <p>
     * It seems, that there is no need to change the execution thread because firestore
     * already performs asynchronous operation.
     * </p>
     *
     * @param path path to collection
     * @return {@link Maybe} with the result
     */
    @NonNull
    public Maybe<QuerySnapshot> getCollection(@NonNull String path) {
        return Maybe.create(emitter -> {
            mFirestore.collection(path).get()
                    .addOnSuccessListener(querySnapshot -> {
                        if (querySnapshot.isEmpty()) {
                            emitter.onComplete();
                        } else {
                            emitter.onSuccess(querySnapshot);
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    });
        });
    }
}
