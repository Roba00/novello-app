package com.yn_1.novello_app.create_chat;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.databinding.FragmentFriendBinding;

import java.util.List;

public class MyCreateChatRecyclerViewAdapter extends RecyclerView.Adapter<MyCreateChatRecyclerViewAdapter.ViewHolder> {

    interface OnItemCheckListener {
        void onItemCheck(Integer itemId);
        void onItemUncheck(Integer itemId);
    }

    private final List<Integer> mFriendIds;
    private final List<String> mFriendNames;

    private OnItemCheckListener onItemClick;


    public MyCreateChatRecyclerViewAdapter(List<Integer> friendIds, List<String> friendNames, OnItemCheckListener onItemCheckListener) {
        mFriendIds = friendIds;
        mFriendNames = friendNames;
        onItemClick = onItemCheckListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentFriendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.username.setText(mFriendNames.get(position));
        // TODO: Add user profile image
        // holder.profileImage.setImageBitmap(null);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectableCheckBox.setChecked(
                        !holder.selectableCheckBox.isChecked());
                if (holder.selectableCheckBox.isChecked()) {
                    onItemClick.onItemCheck(holder.getBindingAdapterPosition());
                } else {
                    onItemClick.onItemUncheck(holder.getBindingAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriendIds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox selectableCheckBox;
        // public final ImageView profileImage;
        public final TextView username;
        public User mItem;

        public ViewHolder(FragmentFriendBinding binding) {
            super(binding.getRoot());
            selectableCheckBox = binding.friendCheckButton;
            selectableCheckBox.setClickable(false);
            selectableCheckBox.setChecked(false);
            // profileImage = binding.friendImage;
            username = binding.friendName;
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + username.getText() + "'";
        }
    }
}