package ru.gcsales.app.presentation.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * @since 04/05/2019
 */
public class ListEntriesAdapter extends RecyclerView.Adapter<ListEntriesAdapter.ListEntryViewHolder> {

    private final List<ListEntry> mEntries;
    private final ItemClickListener<ListEntry> mLongClickListener;

    public ListEntriesAdapter(@NonNull ItemClickListener<ListEntry> longClickListener) {
        mLongClickListener = longClickListener;
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
        holder.bind(mEntries.get(position), mLongClickListener);
    }

    public void setEntries(@NonNull List<ListEntry> entries) {
        mEntries.clear();
        mEntries.addAll(entries);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public static class ListEntryViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNameTextView;


        public ListEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.text_name);
        }

        public void bind(@NonNull ListEntry entry, @NonNull ItemClickListener<ListEntry> listener) {
            mNameTextView.setText(entry.getName());
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(v -> {
                listener.onItemClicked(entry);
                return true;
            });
        }
    }
}
