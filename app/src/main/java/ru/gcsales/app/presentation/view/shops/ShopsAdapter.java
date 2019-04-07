package ru.gcsales.app.presentation.view.shops;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import ru.gcsales.app.presentation.view.ItemClickListener;

/**
 * Recycler view adapter for displaying shops.
 *
 * @author Maxim Surovtsev
 * @since 03/04/2019
 */
public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ShopViewHolder> {

    private final List<Shop> mShops;
    private final ItemClickListener<Shop> mListener;

    public ShopsAdapter(@NonNull ItemClickListener<Shop> listener) {
        mListener = listener;
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
        holder.bind(mShops.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mShops.size();
    }

    public void setShops(@NonNull List<Shop> shops) {
        mShops.clear();
        mShops.addAll(shops);
        notifyDataSetChanged();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        private final View mRootView;
        private final TextView mNameTextView;
        private final ImageView mLogoImageView;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            mRootView = itemView;
            mNameTextView = itemView.findViewById(R.id.text_name);
            mLogoImageView = itemView.findViewById(R.id.image);
        }

        public void bind(@NonNull Shop shop, @NonNull ItemClickListener<Shop> listener) {
            mNameTextView.setText(shop.getName());
            Glide.with(mLogoImageView.getContext())
                    .load(shop.getImageUrl())
                    .into(mLogoImageView);
            mRootView.setOnClickListener(v -> listener.onItemClicked(shop));
        }
    }
}
