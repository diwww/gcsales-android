package ru.gcsales.app.presentation.view.shops;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.gcsales.app.data.model.Shop;

/**
 * MVP view for shops screen.
 *
 * @author Maxim Surovtsev
 * @since 02/04/2019
 */
public interface ShopsView extends MvpView {

    /**
     * Shows/hides progress or content.
     *
     * @param show {@code true} - show progress and hide content
     */
    void showProgress(boolean show);

    /**
     * Shows an error message.
     *
     * @param throwable exception object
     */
    void showError(Throwable throwable);

    /**
     * Sets the list of shops.
     *
     * @param shops list of shops
     */
    void setShops(List<Shop> shops);
}
