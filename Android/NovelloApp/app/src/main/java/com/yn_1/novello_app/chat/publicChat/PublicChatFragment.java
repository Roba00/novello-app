package com.yn_1.novello_app.chat.publicChat;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.R;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.chat.Chat;
import com.yn_1.novello_app.chat.ChatFragmentDirections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class PublicChatFragment extends Fragment implements PublicChatContract.View, PublicChatContract.ViewListener {

    private PublicChatContract.Presenter presenter;

    private static final String ARG_PRIVATE_CHATS = "private-chats";
    private static final String ARG_CURRENT_USER = "current-user";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PublicChatFragment() {
    }

    public static PublicChatFragment newInstance(List<Chat> privateChats) {
        PublicChatFragment fragment = new PublicChatFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PRIVATE_CHATS, (ArrayList<? extends Parcelable>) privateChats);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            List<Chat> privateChats = getArguments().getParcelableArrayList(ARG_PRIVATE_CHATS);
            User currentUser = getArguments().getParcelable(ARG_CURRENT_USER);
            presenter = new PublicChatPresenter(
                    new PublicChatModel(currentUser, privateChats), this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onFragmentCreated();
    }

    @Override
    public void displayChatList(List<Chat> items1, Map<Integer, Bitmap> items2) {
        View view = getView();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(new PublicChatRecyclerViewAdapter(items1, items2,
                    ((NavBarActivity)getActivity()).getUser(), this));
        }
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void navigateToMessageView(int id) {
        ChatFragmentDirections.ActionChatFragmentToMessageFragment action =
                ChatFragmentDirections.actionChatFragmentToMessageFragment
                        (((NavBarActivity) getActivity()).getUser(),
                                presenter.transferChatsToView().get(id));
        ((NavBarActivity) getActivity()).getController()
                .navigate(action);
    }
}