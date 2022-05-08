package com.yn_1.novello_app.cart;

import androidx.fragment.app.Fragment;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.*;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.book.Book;
import com.yn_1.novello_app.library.LibraryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Cart screen presenter
 */
public class CartPresenter {

    CartView view;
    CartModel model;

    User user = null;
    ArrayList<Book> cart;

    /**
     * Constructor
     * Creates model
     */
    public CartPresenter(CartView view) {
        this.view = view;
        model = new CartModel(this);
    }

    /**
     * Asks Model to retrieve cart books
     */
    public void getCartBooks() {
        model.getCartBooks();
    }

    /**
     * Sends the cart books to the view
     * @param cart: array list of books
     */
    public void sendCart(ArrayList<Book> cart) {
        this.cart = cart;
        view.sendCart(cart);
    }

    /**
     * Sets user for Presenter and Model
     * @param user current user
     */
    public void setUser(User user) {
        this.user = user;
        model.setUser(user);
    }

}
