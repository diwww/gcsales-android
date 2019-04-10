package ru.gcsales.app.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.firebase.firestore.Exclude;

import org.jetbrains.annotations.NotNull;

/**
 * Item model.
 *
 * @author Maxim Surovtsev
 * @since 10/04/2019
 */
public class Item {

    private String id;
    private String name;
    private String shop;
    private String imageUrl;
    private double oldPrice;
    private double newPrice;

    public Item() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public Item setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getShop() {
        return shop;
    }

    public Item setShop(String shop) {
        this.shop = shop;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Item setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public Item setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public Item setNewPrice(double newPrice) {
        this.newPrice = newPrice;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return Double.compare(item.oldPrice, oldPrice) == 0 &&
                Double.compare(item.newPrice, newPrice) == 0 &&
                Objects.equal(id, item.id) &&
                Objects.equal(name, item.name) &&
                Objects.equal(shop, item.shop) &&
                Objects.equal(imageUrl, item.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, shop, imageUrl, oldPrice, newPrice);
    }

    @NotNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("shop", shop)
                .add("imageUrl", imageUrl)
                .add("oldPrice", oldPrice)
                .add("newPrice", newPrice)
                .toString();
    }
}
