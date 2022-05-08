package com.yn_1.novello_app.chat.publicChat;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.chat.Chat;
import com.yn_1.novello_app.databinding.FragmentChatItemBinding;

import java.util.List;
import java.util.Map;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Chat}.
 */
public class PublicChatRecyclerViewAdapter extends RecyclerView.Adapter<PublicChatRecyclerViewAdapter.ViewHolder> {

    private final List<Chat> mValues1;
    private final Map<Integer, Bitmap> mValues2;
    private final User currentUser;
    private final PublicChatContract.ViewListener listener;

    public PublicChatRecyclerViewAdapter(List<Chat> items1, Map<Integer, Bitmap> items2, User currentUser,
                                         PublicChatContract.ViewListener listener) {
        mValues1 = items1;
        mValues2 = items2;
        this.currentUser = currentUser;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentChatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues1.get(position);

        holder.chatName.setText(mValues1.get(position).getChatName());
        // holder.chatImage.setImageBitmap(mValues2.get(position));

        holder.itemView.setOnClickListener(v -> {
            listener.navigateToMessageView(position);
        });
    }

    @Override
    public int getItemCount() {
        return mValues1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView chatImage;
        public final TextView chatName;
        public Chat mItem;

        public ViewHolder(FragmentChatItemBinding binding) {
            super(binding.getRoot());
            chatImage = binding.chatImage;
            chatName = binding.chatTitle;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + chatName + "'";
        }
    }
}