package ru.gcsales.app.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.gcsales.app.data.model.Item;
import ru.gcsales.app.data.repository.ItemsRepository;
import ru.gcsales.app.data.repository.ListRepository;
import ru.gcsales.app.presentation.view.items.ItemsView;

/**
 * MVP presenter for items screen.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
@InjectViewState
public class ItemsPresenter extends MvpPresenter<ItemsView> {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final ItemsRepository mItemsRepository;
    private final ListRepository mListRepository;

    private String mShopId;

    public ItemsPresenter(@NonNull ItemsRepository itemsRepository, @NonNull ListRepository listRepository) {
        mItemsRepository = itemsRepository;
        mListRepository = listRepository;
    }

    @Override
    protected void onFirstViewAttach() {

        Disposable disposable = mItemsRepository.getItems(mShopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().showProgress(true))
                .doOnEvent((__, ___) -> getViewState().showProgress(false))
                .subscribe(this::onItemsLoaded, this::onError);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    /**
     * Add an item to the shopping list.
     *
     * @param item item to add
     */
    public void addToList(Item item) {
        Disposable disposable = mListRepository.addItem(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().showProgress(true))
                .doOnEvent(__ -> getViewState().showProgress(false))
                .subscribe(this::onItemAdded, this::onError);
        mCompositeDisposable.add(disposable);
    }

    public void setShopId(String shopId) {
        mShopId = shopId;
    }

    private void onItemsLoaded(List<Item> items) {
        getViewState().setItems(items);
    }

    private void onItemAdded() {
        getViewState().showSnackbar();
    }

    private void onError(Throwable throwable) {
        getViewState().showError(throwable);
    }
}
