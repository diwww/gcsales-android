package ru.gcsales.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Maybe;
import ru.gcsales.app.data.model.internal.Item;

/**
 * Repository implementation for getting shop's items.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
public class ItemsRepository extends RxFirestoreRepository {

    private static final String PATH = "items";
    private static final String SHOP = "shop";
    private static final String KEYWORDS = "keywords";

    public ItemsRepository(@NonNull FirebaseFirestore firestore) {
        super(firestore);
    }

    /**
     * Get list of items with given conditions.
     *
     * @param shop    shop name
     * @param keyword search keyword
     * @return {@link Maybe} with list of items
     */
    public Maybe<List<Item>> getItems(@Nullable String shop, @Nullable String keyword) {
        Query query = mFirestore.collection(PATH);
        if (shop != null) {
            query = query.whereEqualTo(SHOP, shop);
        }
        if (keyword != null) {
            query = query.whereArrayContains(KEYWORDS, keyword.toLowerCase()).orderBy(SHOP);
        }

        return getCollection(query)
                .map(this::convertQuerySnapshot);

    }

    @NonNull
    private List<Item> convertQuerySnapshot(@NonNull QuerySnapshot querySnapshot) {
        List<Item> items = new ArrayList<>(querySnapshot.size());
        for (QueryDocumentSnapshot snapshot : querySnapshot) {
            Item item = snapshot.toObject(Item.class);
            item.setId(snapshot.getId());
            items.add(item);
        }
        return items;
    }
}
