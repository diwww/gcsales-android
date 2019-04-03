package ru.gcsales.app.data.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Repository implementation for retrieving shops.
 *
 * @author Maxim Surovtsev
 * @since 03/04/2019
 */
public class ShopsRepository {

    private static final String PATH = "shops";

    private final FirebaseFirestore mFirestore;

    public ShopsRepository(@NonNull FirebaseFirestore firestore) {
        mFirestore = firestore;
    }

    /**
     * Gets shops
     *
     * @return task with list of shops
     */
    public Task<QuerySnapshot> getShops() {
        return mFirestore.collection(PATH).get();
    }
}
