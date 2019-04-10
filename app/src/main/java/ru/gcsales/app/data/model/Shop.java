package ru.gcsales.app.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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

    public String getId() {
        return id;
    }

    public Shop setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Shop setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Shop setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
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
