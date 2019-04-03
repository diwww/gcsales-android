package ru.gcsales.app.presentation.presenter.shops;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ru.gcsales.app.data.model.Shop;
import ru.gcsales.app.data.repository.ShopsRepository;
import ru.gcsales.app.presentation.view.shops.ShopsView;

/**
 * MVP presenter for shops screen.
 *
 * @author Maxim Surovtsev
 * @since 02/04/2019
 */
@InjectViewState
public class ShopsPresenter extends MvpPresenter<ShopsView> {

    private final ShopsRepository mRepository;

    public ShopsPresenter(@NonNull ShopsRepository repository) {
        mRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        getViewState().showProgress(true);
        mRepository.getShops()
                .addOnSuccessListener(this::onShopsLoaded)
                .addOnCompleteListener(__ -> getViewState().showProgress(false))
                .addOnFailureListener(this::onError);
    }

    private void onShopsLoaded(QuerySnapshot snapshots) {
        List<Shop> shops = new ArrayList<>();
        for (QueryDocumentSnapshot snapshot : snapshots) {
            Shop shop = snapshot.toObject(Shop.class);
            shops.add(shop);
        }
        getViewState().setShops(shops);
    }

    private void onError(Exception e) {
        getViewState().showError(e);
    }
}
