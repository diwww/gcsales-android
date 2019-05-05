package ru.gcsales.app.data.model.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

import androidx.annotation.NonNull;

/**
 * Shopping list entry model.
 *
 * @author Maxim Surovtsev
 * @since 04/05/2019
 */
public class ListEntry {

    private String mId;
    private String mName;
    private Date mTimestamp;

    @Exclude
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(Date timestamp) {
        mTimestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListEntry)) {
            return false;
        }
        ListEntry entry = (ListEntry) o;
        return Objects.equal(mId, entry.mId) &&
                Objects.equal(mName, entry.mName) &&
                Objects.equal(mTimestamp, entry.mTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mTimestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .add("mTimestamp", mTimestamp)
                .toString();
    }
}
