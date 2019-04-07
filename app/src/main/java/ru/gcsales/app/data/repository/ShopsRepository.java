package ru.gcsales.app.data.repository;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import ru.gcsales.app.data.model.Shop;

/**
 * Repository implementation for retrieving shops.
 *
 * @author Maxim Surovtsev
 * @since 03/04/2019
 */
public class ShopsRepository extends RxFirestoreRepository {

    private static final String PATH = "shops";

    public ShopsRepository(@NonNull FirebaseFirestore firestore) {
        super(firestore);
    }

    /**
     * Gets shops
     *
     * @return task with list of shops
     */
    public Maybe<List<Shop>> getShops() {
        return getCollection(PATH)
                .map(this::convertQuerySnapshot);
    }

    private List<Shop> convertQuerySnapshot(@NonNull QuerySnapshot querySnapshot) {
        List<Shop> shops = new ArrayList<>(querySnapshot.size());
        for (QueryDocumentSnapshot snapshot : querySnapshot) {
            Shop shop = snapshot.toObject(Shop.class);
            shops.add(shop);
        }
        return shops;
    }
}
