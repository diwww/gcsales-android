package ru.gcsales.app.presentation.view.list;

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
import ru.gcsales.app.data.model.internal.ListEntry;
import ru.gcsales.app.presentation.view.ItemClickListener;

/**
 * Recycler view adapter for displaying shopping list entries.
 *
 * @author Maxim Surovtsev
 * @since 11/04/2019
 */
public class ListEntriesAdapter extends RecyclerView.Adapter<ListEntriesAdapter.ListEntryViewHolder> {

    private final List<ListEntry> mEntries;
    private final ItemClickListener<ListEntry> mIncrementListener;
    private final ItemClickListener<ListEntry> mDecrementListener;
    private final ItemClickListener<ListEntry> mOpenMapListener;

    public ListEntriesAdapter(@NonNull ItemClickListener<ListEntry> incrementListener,
                              @NonNull ItemClickListener<ListEntry> decrementListener,
                              @NonNull ItemClickListener<ListEntry> openMapListener) {
        mIncrementListener = incrementListener;
        mDecrementListener = decrementListener;
        mOpenMapListener = openMapListener;
        mEntries = new ArrayList<>();
    }

    @NonNull
    @Override
    public ListEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_entry, parent, false);
        return new ListEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListEntryViewHolder holder, int position) {
        holder.bind(mEntries.get(position), mIncrementListener, mDecrementListener, mOpenMapListener);
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public void setEntries(@NonNull List<ListEntry> entries) {
        mEntries.clear();
        mEntries.addAll(entries);
        notifyDataSetChanged();
    }

    public static class ListEntryViewHolder extends RecyclerView.ViewHolder {

        private final TextView mShopTextView;
        private final ImageButton mOpenMapButton;
        private final View mTopDivider;
        private final ImageView mImageView;
        private final TextView mNameTextView;
        private final TextView mOldPriceTextView;
        private final TextView mNewPriceTextView;
        private final TextView mCountTextView;
        private final ImageButton mIncrementButton;
        private final ImageButton mDecrementButton;

        public ListEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            mShopTextView = itemView.findViewById(R.id.text_shop);
            mOpenMapButton = itemView.findViewById(R.id.button_open_map);
            mTopDivider = itemView.findViewById(R.id.top_divider);
            mImageView = itemView.findViewById(R.id.image);
            mNameTextView = itemView.findViewById(R.id.text_name);
            mOldPriceTextView = itemView.findViewById(R.id.text_old_price);
            mNewPriceTextView = itemView.findViewById(R.id.text_new_price);
            mCountTextView = itemView.findViewById(R.id.text_count);
            mIncrementButton = itemView.findViewById(R.id.button_increment);
            mDecrementButton = itemView.findViewById(R.id.button_decrement);
        }

        public void bind(@NonNull ListEntry entry,
                         @NonNull ItemClickListener<ListEntry> incrementListener,
                         @NonNull ItemClickListener<ListEntry> decrementListener,
                         @NonNull ItemClickListener<ListEntry> openMapListener) {
            final Resources resources = itemView.getResources();

            mShopTextView.setText(entry.getShop());
            mShopTextView.setVisibility(entry.isShowShop() ? View.VISIBLE : View.GONE);
            mOpenMapButton.setVisibility(entry.isShowShop() ? View.VISIBLE : View.GONE);
            mTopDivider.setVisibility(entry.isShowShop() ? View.VISIBLE : View.GONE);
            mNameTextView.setText(entry.getName());
            mOldPriceTextView.setText(resources.getString(R.string.price, entry.getOldPrice()));
            mOldPriceTextView.setPaintFlags(mOldPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mNewPriceTextView.setText(resources.getString(R.string.price, entry.getNewPrice()));
            mCountTextView.setText(resources.getString(R.string.count, entry.getCount()));

            Glide.with(mImageView.getContext())
                    .load(entry.getImageUrl())
                    .placeholder(R.drawable.item_placeholder)
                    .into(mImageView);
            mOpenMapButton.setOnClickListener(v -> openMapListener.onItemClicked(entry));
            mIncrementButton.setOnClickListener(v -> incrementListener.onItemClicked(entry));
            mDecrementButton.setOnClickListener(v -> decrementListener.onItemClicked(entry));
        }
    }
}
