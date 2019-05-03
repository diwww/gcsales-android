package ru.gcsales.app.presentation.view.shops;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import androidx.annotation.NonNull;
import ru.gcsales.app.data.model.internal.Shop;
import ru.gcsales.app.presentation.view.base.ErrorView;
import ru.gcsales.app.presentation.view.base.ProgressView;

/**
 * MVP view for shops screen.
 *
 * @author Maxim Surovtsev
 * @since 02/04/2019
 */
public interface ShopsView extends MvpView, ProgressView, ErrorView {

    /**
     * Sets the list of shops.
     *
     * @param shops list of shops
     */
    void setShops(@NonNull List<Shop> shops);

    /**
     * Start items flow.
     *
     * @param shop shop
     */
    void startItemsFlow(@NonNull Shop shop);
}
