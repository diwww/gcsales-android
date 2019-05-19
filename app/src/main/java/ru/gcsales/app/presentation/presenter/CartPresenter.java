package ru.gcsales.app.presentation.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.gcsales.app.data.model.internal.CartEntry;
import ru.gcsales.app.data.repository.CartRepository;
import ru.gcsales.app.presentation.view.cart.CartView;

/**
 * MVP presenter for shopping cart screen.
 *
 * @author Maxim Surovtsev
 * @since 11/04/2019
 */
@InjectViewState
public class CartPresenter extends MvpPresenter<CartView> {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private final CartRepository mRepository;
    private Disposable mListenerDisposable;

    public CartPresenter(@NonNull CartRepository repository) {
        mRepository = repository;
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    public void attachListener() {
        mListenerDisposable = mRepository.getUpdateObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().showProgress(true))
                .doOnNext(__ -> getViewState().showProgress(false))
                .doOnTerminate(() -> getViewState().showProgress(false))
                .subscribe(this::onEntriesLoaded, this::onError);
        mCompositeDisposable.add(mListenerDisposable);
    }

    public void detachListener() {
        mListenerDisposable.dispose();
    }

    public void incrementCount(@NonNull CartEntry entry) {
        Disposable disposable = mRepository.incrementCount(entry)
                .subscribe(this::onComplete, this::onError);
        mCompositeDisposable.add(disposable);
    }

    public void decrementCount(@NonNull CartEntry entry) {
        Disposable disposable = mRepository.decrementCount(entry)
                .subscribe(this::onComplete, this::onError);
        mCompositeDisposable.add(disposable);
    }

    public void openMap(@NonNull CartEntry entry) {
        getViewState().startMapFlow(entry);
    }

    private void onEntriesLoaded(List<CartEntry> entries) {
        getViewState().setEntries(entries);
        getViewState().setSums(getTotalPrice(entries), getTotalDiscount(entries));
    }

    private void onComplete() {
        // TODO: maybe logging
    }

    private void onError(Throwable throwable) {
        getViewState().showError(throwable);
    }

    private double getTotalPrice(List<CartEntry> entries) {
        double price = 0.;
        for (CartEntry entry : entries) {
            price += entry.getNewPrice() * entry.getCount();
        }
        return price;
    }

    private double getTotalDiscount(List<CartEntry> entries) {
        double discount = 0;
        for (CartEntry entry : entries) {
            if (entry.getOldPrice() != 0) {
                discount += (entry.getOldPrice() - entry.getNewPrice()) * entry.getCount();
            }
        }
        return discount;
    }
}
