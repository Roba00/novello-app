package com.yn_1.novello_app.create_chat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.R;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.chat.Chat;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CreateChatFragment extends Fragment implements CreateChatContract.View {

    CreateChatContract.Presenter presenter;

    RecyclerView recyclerView;
    Button createChatButton;
    EditText titleInput;
    Spinner spinner;

    boolean[] friendValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = ((NavBarActivity) getActivity()).getUser();
        presenter = new CreateChatPresenter(new CreateChatModel(user), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.access_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.access_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        titleInput = view.findViewById(R.id.messageInputField);
        titleInput.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        createChatButton = view.findViewById(R.id.createChat);
        createChatButton.setOnClickListener(v -> presenter.onCreateChatButtonPressed(friendValues));
        // Set the adapter
        recyclerView = view.findViewById(R.id.friendRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.onFragmentCreated();
    }

    @Override
    public void createFriendsList(List<Integer> friendIds, List<String> friendUsernames) {
        friendValues = new boolean[friendIds.size()];
        recyclerView.setAdapter(new MyCreateChatRecyclerViewAdapter(friendIds, friendUsernames,
                new MyCreateChatRecyclerViewAdapter.OnItemCheckListener() {
                    @Override
                    public void onItemCheck(Integer itemId) {
                        friendValues[itemId] = true;
                    }

                    @Override
                    public void onItemUncheck(Integer itemId) {
                        friendValues[itemId] = false;
                    }
                }));
        // recyclerView.findViewHolderForAdapterPosition(0).getC
    }

    @Override
    public void navigateToMessageScreen() {
        ((NavBarActivity) getActivity()).getController().navigate(
                CreateChatFragmentDirections.actionCreateChatFragmentToChatFragment2());
    }

    @Override
    public String getInputtedTitle() {
        return titleInput.getText().toString();
    }

    @Override
    public int getInputtedAccess() {
        // 1 for private, 2 for public
        return spinner.getSelectedItemPosition() + 1;
    }
}