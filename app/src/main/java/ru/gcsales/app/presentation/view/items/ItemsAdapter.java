package ru.gcsales.app.presentation.view.items;

import android.content.res.Resources;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.gcsales.app.R;
import ru.gcsales.app.data.model.Item;
import ru.gcsales.app.presentation.view.ItemClickListener;

/**
 * Recycler view adapter for displaying items.
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final List<Item> mItems;
    private final ItemClickListener<Item> mListener;

    public ItemsAdapter(@NonNull ItemClickListener<Item> listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(mItems.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(@NonNull List<Item> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;
        private final TextView mNameTextView;
        private final TextView mOldPriceTextView;
        private final TextView mNewPriceTextView;
        private final ImageButton mAddButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mNameTextView = itemView.findViewById(R.id.text_name);
            mOldPriceTextView = itemView.findViewById(R.id.text_old_price);
            mNewPriceTextView = itemView.findViewById(R.id.text_new_price);
            mAddButton = itemView.findViewById(R.id.button_add);
        }

        public void bind(@NonNull Item item, @NonNull ItemClickListener<Item> listener) {
            final Resources resources = itemView.getResources();
            mNameTextView.setText(item.getName());
            mOldPriceTextView.setText(resources.getString(R.string.price, item.getOldPrice()));
            mOldPriceTextView.setPaintFlags(mOldPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mNewPriceTextView.setText(resources.getString(R.string.price, item.getNewPrice()));

            Glide.with(mImageView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.item_placeholder)
                    .into(mImageView);
            mAddButton.setOnClickListener(v -> listener.onItemClicked(item));
        }
    }
}
