package com.yn_1.novello_app.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.R;
import com.yn_1.novello_app.home.HomeFragmentDirections;

import java.util.ArrayList;

/**
 * Profile screen view
 */
public class ProfileView extends Fragment {

    ProfilePresenter presenter;

    Button friendsList;

    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        presenter = new ProfilePresenter(this);

        user = ((NavBarActivity)getActivity()).getUser();
        presenter.setUser(user);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile_view, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        friendsList = view.findViewById(R.id.friendsList);
        friendsList.setOnClickListener(v -> {
            presenter.showFriendsList();
        });

    }

    /**
     * Shows the user's friend list
     * @param friendsArray is the user's friend array (usernames only)
     */
    public void showFriends(String[] friendsArray) {
        ProfileViewDirections.ActionProfileViewToFriendsView action =
                ProfileViewDirections.actionProfileViewToFriendsView();
        action.setFriendsArray(friendsArray);
        ((NavBarActivity)getActivity()).getController().navigate(action);
    }

}
