package com.yn_1.novello_app.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.R;
import com.yn_1.novello_app.home.HomeFragmentDirections;

/**
 * Friends screen view
 */
public class FriendsView extends Fragment {

    FriendsPresenter presenter;

    LinearLayout linearLayout;
    EditText addFriendSearch;
    Button addFriendButton;

    final int Text_Width = 500;
    final int Text_Height = 300;

    String[] friendsArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        presenter = new FriendsPresenter(this);
        friendsArray = FriendsViewArgs.fromBundle(getArguments()).getFriendsArray();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_friends_view, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout = view.findViewById(R.id.friendsLinearLayout);
        addFriendSearch = view.findViewById(R.id.addFriendSearch);
        addFriendButton = view.findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(v -> {
            addFriendRequest();
        });
        showFriends();

    }

    /**
     * Adds the searched friend.
     */
    private void addFriendRequest() {

        //todo: get friends on friend fragment rather than profile (no need to send, can refresh easily upon adding friend)
        String friend = addFriendSearch.getText().toString();
        presenter.addFriendRequest(friend);

    }

    /**
     * Alerts the user of the result of adding the friend
     * @param success is true if the friend was added
     */
    public void friendAdded(boolean success) {
        String text;
        if (success) {
            text = "Friend added successfully.";
        }
        else {
            text = "Friend not added successfully.";
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());
        alert.setMessage(text);
        alert.show();
        showFriends();
    }

    /**
     * Shows the list of friends.
     */
    private void showFriends() {

        //todo: friends view not entered via profile friends view button which is executing in full: need to add navigation in nav bar activity
        //todo: show friends (usernames) in recycler view rather than a scroll view of text views

        for (int i = 0; i< friendsArray.length; i++) {
            TextView textView = new TextView(this.getContext());
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(Text_Width, Text_Height);
            textParams.setMargins(5, 5, 5, 5);
            textView.setLayoutParams(textParams);
            textView.setText(friendsArray[i]);
            linearLayout.addView(textView);
        }

    }

}