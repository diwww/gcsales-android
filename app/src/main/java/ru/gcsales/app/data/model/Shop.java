package ru.gcsales.app.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.firebase.firestore.Exclude;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Shop model.
 *
 * @author Maxim Surovtsev
 * @since 10/04/2019
 */
public class Shop implements Serializable {

    private String id;
    private String name;
    private String imageUrl;

    public Shop() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shop)) {
            return false;
        }
        Shop shop = (Shop) o;
        return Objects.equal(id, shop.id) &&
                Objects.equal(name, shop.name) &&
                Objects.equal(imageUrl, shop.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, imageUrl);
    }

    @NotNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("imageUrl", imageUrl)
                .toString();
    }
}
