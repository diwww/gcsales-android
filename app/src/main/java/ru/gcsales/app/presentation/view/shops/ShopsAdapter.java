package ru.gcsales.app.presentation.view.shops;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.gcsales.app.R;
import ru.gcsales.app.data.model.Shop;

/**
 * Recycler view adapter for displaying shops.
 *
 * @author Maxim Surovtsev
 * @since 03/04/2019
 */
public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ShopViewHolder> {

    private final List<Shop> mShops;

    public ShopsAdapter() {
        mShops = new ArrayList<>();
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.bind(mShops.get(position));
    }

    @Override
    public int getItemCount() {
        return mShops.size();
    }

    public void setShops(List<Shop> shops) {
        mShops.clear();
        mShops.addAll(shops);
        notifyDataSetChanged();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNameTextView;
        private final ImageView mLogoImageView;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.text_name);
            mLogoImageView = itemView.findViewById(R.id.image_logo);
        }

        public void bind(Shop shop) {
            mNameTextView.setText(shop.getName());
            Glide.with(mLogoImageView.getContext())
                    .load(shop.getImageUrl())
                    .into(mLogoImageView);
        }
    }
}