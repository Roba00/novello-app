package com.yn_1.novello_app.cart;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.book.Book;
import com.yn_1.novello_app.databinding.CartItemBinding;
import com.yn_1.novello_app.volley_requests.ImageRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    interface OnButtonClickListener {
        void onButtonClick(Integer bookId);
    }

    /**
     *  Contains the fields for the book size.
     */
    int[] BOOK_SIZE = {175*2, 280*2};

    private final List<Book> books;

    private final CartRecyclerViewAdapter.OnButtonClickListener onViewButtonClick;
    private final CartRecyclerViewAdapter.OnButtonClickListener onWishlistButtonClick;

    public CartRecyclerViewAdapter(List<Book> books,
                                        CartRecyclerViewAdapter.OnButtonClickListener onViewButtonClick, CartRecyclerViewAdapter.OnButtonClickListener onWishlistButtonClick) {
        this.books = books;
        this.onViewButtonClick = onViewButtonClick;
        this.onWishlistButtonClick = onWishlistButtonClick;
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartRecyclerViewAdapter.ViewHolder(CartItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.ViewHolder holder, int position) {
        fetchImage(books.get(position).getImageURL(), holder.bookCover);
        holder.title.setText(books.get(position).getTitle());
        holder.author.setText(books.get(position).getAuthor());
        holder.price.setText("Price: $" + books.get(position).getPrice());
        holder.rating.setText("(" + books.get(position).getRating() + ")");
        holder.ratingBar.setRating((float) books.get(position).getRating());

        holder.viewButton.setOnClickListener(v ->
                onViewButtonClick.onButtonClick(books.get(holder.getBindingAdapterPosition()).getBookID()));

        holder.removeButton.setOnClickListener(v ->
                onWishlistButtonClick.onButtonClick(books.get(holder.getBindingAdapterPosition()).getBookID()));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView bookCover;
        public final TextView title;
        public final TextView author;
        public final TextView price;
        public final TextView rating;
        public final RatingBar ratingBar;
        public final Button viewButton;
        public final Button removeButton;

        public ViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            bookCover = binding.cartItemImage;
            title = binding.cartItemTitle;
            author = binding.cartItemAuthor;
            price = binding.cartItemPrice;
            rating = binding.cartItemRatingText;
            ratingBar = binding.cartItemRatingBar;
            viewButton = binding.cartItemViewButton;
            removeButton = binding.cartItemWishlistButton;
        }

        public void setOnClickListener(View.OnClickListener viewClickListener,
                                       View.OnClickListener wishlistButtonListener) {
            viewButton.setOnClickListener(viewClickListener);
            removeButton.setOnClickListener(wishlistButtonListener);
        }
    }

    public void fetchImage(String imageURL, ImageView view) {
        ImageRequester req = new ImageRequester();
        req.getRequest(imageURL, null, new VolleyCommand<Bitmap>() {
            @Override
            public void execute(Bitmap image) {
                Bitmap currentCover = Bitmap.createScaledBitmap(image, BOOK_SIZE[0], BOOK_SIZE[1], true);
                view.setImageBitmap(currentCover);
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("Image Load Error", imageURL);
            }
        }, null, null);
    }
}
