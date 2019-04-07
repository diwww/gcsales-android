package ru.gcsales.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Maybe;
import ru.gcsales.app.data.model.Item;

/**
 * Repository implementation for getting shop's items.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
public class ItemsRepository extends RxFirestoreRepository {

    private static final String PATH = "shops/%s/items";

    public ItemsRepository(@NonNull FirebaseFirestore firestore) {
        super(firestore);
    }

    /**
     * Get list of items for given shop.
     *
     * @param id id of the shop
     * @return {@link Maybe} with list of items
     */
    public Maybe<List<Item>> getItems(@NonNull String id) {
        return getCollection(String.format(PATH, id))
                .map(this::convertQuerySnapshot);
    }

    @NonNull
    private List<Item> convertQuerySnapshot(@NonNull QuerySnapshot querySnapshot) {
        List<Item> items = new ArrayList<>(querySnapshot.size());
        for (QueryDocumentSnapshot snapshot : querySnapshot) {
            Item item = snapshot.toObject(Item.class);
            items.add(item);
        }
        return items;
    }
}
