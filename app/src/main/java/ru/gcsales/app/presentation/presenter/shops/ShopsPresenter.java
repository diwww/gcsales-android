package ru.gcsales.app.presentation.presenter.shops;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
        Disposable disposable = mRepository.getShops()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().showProgress(true))
                .doOnEvent((__, ___) -> getViewState().showProgress(false))
                .subscribe(this::onShopsLoaded, this::onError);
    }

    private void onShopsLoaded(List<Shop> shops) {
        getViewState().setShops(shops);
    }

    private void onError(Throwable throwable) {
        getViewState().showError(throwable);
    }
}
