package ru.gcsales.app.presentation.view.cart;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import androidx.annotation.NonNull;
import ru.gcsales.app.data.model.internal.CartEntry;
import ru.gcsales.app.presentation.view.base.ErrorView;
import ru.gcsales.app.presentation.view.base.ProgressView;

/**
 * MVP view for shopping cart screen.
 *
 * @author Maxim Surovtsev
 * @since 11/04/2019
 */
public interface CartView extends MvpView, ProgressView, ErrorView {

    /**
     * Sets the list of entries.
     *
     * @param entries list of entries
     */
    void setEntries(@NonNull List<CartEntry> entries);

    /**
     * Sets total price and total discount sum texts.
     *
     * @param price    total price
     * @param discount total discount
     */
    void setSums(double price, double discount);

    /**
     * Starts map flow.
     *
     * @param entry shopping cart entry
     */
    void startMapFlow(@NonNull CartEntry entry);
}
