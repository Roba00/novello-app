package com.yn_1.novello_app.discovery;

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
import com.yn_1.novello_app.databinding.DiscoveryItemBinding;
import com.yn_1.novello_app.volley_requests.ImageRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import java.util.List;

public class DiscoveryRecyclerViewAdapter extends RecyclerView.Adapter<DiscoveryRecyclerViewAdapter.ViewHolder> {

    interface OnButtonClickListener {
        void onButtonClick(Integer bookId);
    }

    /**
     *  Contains the fields for the book size.
     */
    int[] BOOK_SIZE = {175*2, 280*2};

    private final List<Book> books;

    private final OnButtonClickListener onViewButtonClick;
    private final OnButtonClickListener onWishlistButtonClick;

    public DiscoveryRecyclerViewAdapter(List<Book> books,
            OnButtonClickListener onViewButtonClick, OnButtonClickListener onWishlistButtonClick) {
        this.books = books;
        this.onViewButtonClick = onViewButtonClick;
        this.onWishlistButtonClick = onWishlistButtonClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiscoveryRecyclerViewAdapter.ViewHolder(DiscoveryItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        fetchImage(books.get(position).getImageURL(), holder.bookCover);
        holder.title.setText(books.get(position).getTitle());
        holder.author.setText(books.get(position).getAuthor());
        holder.genre.setText(books.get(position).getGenre());
        holder.rating.setText("(" + books.get(position).getRating() + ")");
        holder.ratingBar.setRating((float) books.get(position).getRating());

        holder.viewButton.setOnClickListener(v ->
                onViewButtonClick.onButtonClick(books.get(holder.getBindingAdapterPosition()).getBookID()));

        holder.wishlistButton.setOnClickListener(v ->
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
        public final TextView genre;
        public final TextView rating;
        public final RatingBar ratingBar;
        public final Button viewButton;
        public final Button wishlistButton;

        public ViewHolder(DiscoveryItemBinding binding) {
            super(binding.getRoot());
            bookCover = binding.discoveryItemImage;
            title = binding.discoveryItemTitle;
            author = binding.discoveryItemAuthor;
            genre = binding.discoveryItemGenre;
            rating = binding.discoveryItemRatingText;
            ratingBar = binding.discoveryItemRatingBar;
            viewButton = binding.discoveryItemViewButton;
            wishlistButton = binding.discoveryItemWishlistButton;
        }

        public void setOnClickListener(View.OnClickListener viewClickListener,
                                       View.OnClickListener wishlistButtonListener) {
            viewButton.setOnClickListener(viewClickListener);
            wishlistButton.setOnClickListener(wishlistButtonListener);
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
