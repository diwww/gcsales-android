package ru.gcsales.app.presentation.presenter.items;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.gcsales.app.data.model.Item;
import ru.gcsales.app.data.repository.ItemsRepository;
import ru.gcsales.app.presentation.view.items.ItemsView;

/**
 * MVP presenter for items screen.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
@InjectViewState
public class ItemsPresenter extends MvpPresenter<ItemsView> {

    private final ItemsRepository mRepository;

    private String mShopId;

    public ItemsPresenter(@NonNull ItemsRepository repository) {
        mRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {

        Disposable disposable = mRepository.getItems(mShopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().showProgress(true))
                .doOnEvent((__, ___) -> getViewState().showProgress(false))
                .subscribe(this::onItemsLoaded, this::onError);
    }

    public void setShopId(String shopId) {
        mShopId = shopId;
    }

    private void onItemsLoaded(List<Item> items) {
        getViewState().setItems(items);
    }

    private void onError(Throwable throwable) {
        getViewState().showError(throwable);
    }
}
