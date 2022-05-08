package com.yn_1.novello_app.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.R;
import com.yn_1.novello_app.account.AdultUser;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.chat.privateChat.PrivateChatFragment;
import com.yn_1.novello_app.chat.publicChat.PublicChatFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements ChatContract.View {


    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    private FragmentStateAdapter pagerAdapter;
    private Button createChatButton;

    ChatContract.Presenter presenter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Chat.
     */
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = ((NavBarActivity) getActivity()).getUser();

        presenter = new ChatPresenter(new ChatModel(user), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.chatTabLayout);
        viewPager2 = view.findViewById(R.id.chatViewPager);
        createChatButton = view.findViewById(R.id.navigateCreateChat);
        createChatButton.setOnClickListener(v -> navigateToCreateChatScreen());

        presenter.onFragmentCreated();
    }



    @Override
    public void initializeTabListener() {
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);

        TabLayout.OnTabSelectedListener listener = new TabListener();
        tabLayout.addOnTabSelectedListener(listener);
    }

    @Override
    public void navigateToCreateChatScreen() {
        ((NavBarActivity) getActivity()).getController().navigate(ChatFragmentDirections.
                actionChatFragmentToCreateChatFragment(
                        (AdultUser) ((NavBarActivity) getActivity()).getUser()));
    }

    private class TabListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            /*if (tab.getId() == R.id.privateChatTab) {
                viewPager2.setCurrentItem(0);
            }
            else if (tab.getId() == R.id.publicChatTab) {
                viewPager2.setCurrentItem(1);
            }
            else {
                throw new IllegalArgumentException();
            }*/
            Log.d("Tab Selected", String.valueOf(tab.getPosition()));
            viewPager2.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) { }

        @Override
        public void onTabReselected(TabLayout.Tab tab) { }
    }

    /**
     * A simple pager adapter
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(Fragment fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position)
            {
                case 0:
                    return PrivateChatFragment.newInstance(presenter.transferChatsToView()[0]);

                case 1:
                    return PublicChatFragment.newInstance(presenter.transferChatsToView()[1]);

                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}