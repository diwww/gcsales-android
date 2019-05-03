package ru.gcsales.app.data.model.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import org.jetbrains.annotations.NotNull;

/**
 * Place model.
 *
 * @author Maxim Surovtsev
 * @since 03/05/2019
 */
public class Place {

    private Double mLatitude;
    private Double mLongitude;
    private String mName;
    private String mAddress;

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }
        Place place = (Place) o;
        return Objects.equal(mLatitude, place.mLatitude) &&
                Objects.equal(mLongitude, place.mLongitude) &&
                Objects.equal(mName, place.mName) &&
                Objects.equal(mAddress, place.mAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mLatitude, mLongitude, mName, mAddress);
    }

    @NotNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mLatitude", mLatitude)
                .add("mLongitude", mLongitude)
                .add("mName", mName)
                .add("mAddress", mAddress)
                .toString();
    }
}
