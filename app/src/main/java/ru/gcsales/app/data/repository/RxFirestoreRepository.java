package ru.gcsales.app.data.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
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

    /**
     * Gets the document at given path.
     *
     * @param path path to document
     * @return {@link Maybe} with the result
     */
    public Maybe<DocumentSnapshot> getDocument(@NonNull String path) {
        return Maybe.create(emitter -> {
            mFirestore.document(path).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            emitter.onSuccess(documentSnapshot);
                        } else {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    /**
     * Creates or overwrites document.
     * <p>
     * There is also {@link SetOptions} parameter which specifies how to set document.
     * For example, {@link SetOptions#merge()} implies that the document will not be
     * fully overwritten, only certain fields will be overwritten instead.
     * </p>
     *
     * @param data    document data
     * @param path    path to document
     * @param options set options
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable setDocument(@NonNull String path, Object data, SetOptions options) {
        return Completable.create(emitter -> {
            mFirestore.document(path).set(data, options)
                    .addOnSuccessListener(__ -> emitter.onComplete())
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    /**
     * Adds a new document to the collection.
     * <p>
     * If this method is used, then document id will be auto-generated.
     * </p>
     *
     * @param path path to the collection
     * @param data document data
     * @return {@link Completable} with the result.
     */
    public Completable addDocument(@NonNull String path, Object data) {
        return Completable.create(emitter -> {
            mFirestore.collection(path).add(data)
                    .addOnSuccessListener(__ -> emitter.onComplete())
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    /**
     * Deletes document at given path.
     *
     * @param path path to document
     * @return {@link Completable} with the result
     */
    @NonNull
    public Completable deleteDocument(@NonNull String path) {
        return Completable.create(emitter -> {
            mFirestore.document(path).delete()
                    .addOnSuccessListener(__ -> emitter.onComplete())
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    });
        });
    }
}
