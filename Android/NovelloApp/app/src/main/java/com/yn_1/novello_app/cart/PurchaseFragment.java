package com.yn_1.novello_app.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.*;
import com.yn_1.novello_app.book.Book;
import com.yn_1.novello_app.library.LibraryFragment;
import com.yn_1.novello_app.volley_requests.JsonObjectRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PurchaseFragment extends Fragment {

    Button finish;
    EditText creditCardInput;
    String creditCardNumber;
    TextView priceText;
    int userID;
    int[] cartIDs;
    float[] cartPrices;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userID = PurchaseFragmentArgs.fromBundle(getArguments()).getUserID();
        cartIDs = PurchaseFragmentArgs.fromBundle(getArguments()).getCartIDs();
        cartPrices = PurchaseFragmentArgs.fromBundle(getArguments()).getCartPrices();
        //todo: show list of book titles/authors/prices in vertical scroll view

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("Purchase", "Purchase entered on create view.");

        return inflater.inflate(R.layout.fragment_purchase, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finish = view.findViewById(R.id.finishPurchase);
        creditCardInput = view.findViewById(R.id.creditCardInput);
        priceText = view.findViewById(R.id.price);

        double price = 0;
        for (int i = 0; i < cartPrices.length; i++) {
            price += cartPrices[i];
        }
        priceText.setText("Price = $" + price);

        finish.setOnClickListener(v -> {
            creditCardNumber = creditCardInput.getText().toString();
            if (canChargeCard(creditCardNumber)) {
                JsonObjectRequester purchaseRequester = new JsonObjectRequester();
                JsonObjectCommand command = new JsonObjectCommand();
                for (int i = 0; i < cartIDs.length; i++) {
                    int bookID = cartIDs[i];
                    JSONObject bookJson = new JSONObject();
                    try {
                        bookJson.put("userId", userID);
                        bookJson.put("bookId", bookID);
                        //todo: 3 represents unread. Set that in an enum.
                        bookJson.put("category", 3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    purchaseRequester.putRequest("bookData",
                            bookJson, command, null, null);
                }
            }
            else {
                //todo: card cannot be charged, do something about it
            }
        });

    }

    /**
     * @param creditCardNumber credit card number
     * @return true if the card can be charged
     */
    private boolean canChargeCard(String creditCardNumber) {
        //todo: move to user or other more appropriate location
        return true;
    }

    /**
     * @param creditCardNumber credit card number
     * @return true if the card was charged successfully
     */
    private boolean chargeCard(String creditCardNumber) {
        //todo: move to user or other more appropriate location
        return true;
    }

    /**
     * Goes to dashboard upon successful purchase completion
     * @param succeeded is true if the purchase was successful
     */
    private void purchaseResult(boolean succeeded) {
        if (chargeCard(creditCardNumber)) {

            ((NavBarActivity)getActivity()).getController().navigate(R.id.homeFragment);
        }
        else {
            //todo: card could not be charged, do something about it
        }
    }

    /**
     * Class used to get result of request
     */
    private class JsonObjectCommand implements VolleyCommand<JSONObject> {

        @Override
        public void execute(JSONObject data) {
            purchaseResult(data != null);
        }

        @Override
        public void onError(VolleyError error) {

        }

    }

}
