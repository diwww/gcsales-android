package ru.gcsales.app.data.model.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Shopping cart entry model.
 *
 * @author Maxim Surovtsev
 * @since 10/04/2019
 */
public class CartEntry extends Item {

    private Date mTimestamp;
    private int mCount;
    private boolean mShowShop;

    public CartEntry() {
    }

    public static CartEntry fromItem(Item item) {
        CartEntry entry = new CartEntry();
        entry.setId(item.getId());
        entry.setImageUrl(item.getImageUrl());
        entry.setName(item.getName());
        entry.setNewPrice(item.getNewPrice());
        entry.setOldPrice(item.getOldPrice());
        entry.setShop(item.getShop());
        return entry;
    }

    public CartEntry incrementCount() {
        mCount++;
        return this;
    }

    public CartEntry decrementCount() {
        mCount--;
        return this;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(Date timestamp) {
        mTimestamp = timestamp;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }


    @Exclude
    public boolean isShowShop() {
        return mShowShop;
    }

    public void setShowShop(boolean showShop) {
        mShowShop = showShop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartEntry)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CartEntry cartEntry = (CartEntry) o;
        return mCount == cartEntry.mCount &&
                Objects.equal(mTimestamp, cartEntry.mTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), mTimestamp, mCount);
    }

    @NotNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mTimestamp", mTimestamp)
                .add("mCount", mCount)
                .toString();
    }
}
