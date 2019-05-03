package ru.gcsales.app.data.model.internal;

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
    private Double oldPrice;
    private Double newPrice;

    public Item() {
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

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
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
