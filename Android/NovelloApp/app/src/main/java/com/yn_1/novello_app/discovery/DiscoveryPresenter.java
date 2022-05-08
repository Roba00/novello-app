package com.yn_1.novello_app.discovery;

import android.util.Pair;

import com.yn_1.novello_app.book.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Presenter for the discovery screen
 */
public class DiscoveryPresenter {

    DiscoveryView view;
    DiscoveryModel model;

    int userID;
    ArrayList<Book> allBooks;
    ArrayList<Book> userBooks;

    /**
     * Constructor
     * @param view discovery view
     * @param userID current user's id
     */
    public DiscoveryPresenter(DiscoveryView view, int userID) {
        this.view = view;
        this.userID = userID;
        this.model = new DiscoveryModel(this, userID);
        model.getAllBooks();
        model.getUserBooks();
    }

    /**
     * Receives all books
     * @param allBooks all books available in the app
     */
    public void sendAllBooks(ArrayList<Book> allBooks) {
        this.allBooks = allBooks;
        recommendationAlgorithmRunner();
    }

    /**
     * Receives user's books
     * @param userBooks all books in the user's library
     */
    public void sendUserBooks(ArrayList<Book> userBooks) {
        this.userBooks = userBooks;
        recommendationAlgorithmRunner();
    }

    /**
     * Runs recommendation algorithm if it can be run at this time
     */
    private boolean recommendationAlgorithmRunner() {

        if(this.allBooks != null && this.userBooks != null) {
            runRecommendationAlgorithm();
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Recommendation algorithm
     */
    private void runRecommendationAlgorithm() {

        //constants
        final int MIN_RECOMMENDED_RATING = 3;
        final int AUTHOR_WEIGHT = 3;
        final int GENRE_WEIGHT = 1;

        //author and genre count
        HashMap<String, Integer> authorHashMap = new HashMap<String, Integer>();
        HashMap<String, Integer> genreHashMap = new HashMap<String, Integer>();
        for (Book book : userBooks) {
            String author = book.getAuthor();
            String genre = book.getGenre();
            if (authorHashMap.get(author) == null) {
                authorHashMap.put(author, 1);
            }
            else {
                authorHashMap.put(author, authorHashMap.get(author) + 1);
            }
            if (genreHashMap.get(genre) == null) {
                genreHashMap.put(genre, 1);
            }
            else {
                genreHashMap.put(genre, genreHashMap.get(genre) + 1);
            }
        }

        //book recommendation rating set
        //todo: dont recommend books user already has
        ArrayList<Pair<Book, Double>> recommendations = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getRating() >= MIN_RECOMMENDED_RATING) {
                Integer numAuthor = authorHashMap.get(book.getAuthor());
                if (numAuthor == null) {
                    numAuthor = 0;
                }
                Integer numGenre = genreHashMap.get(book.getGenre());
                if (numGenre == null) {
                    numGenre = 0;
                }
                double recommendationRating =
                        book.getRating() * Math.max(AUTHOR_WEIGHT * numAuthor, GENRE_WEIGHT * numGenre);
                recommendations.add(new Pair<>(book, recommendationRating));
            }
        }

        sortRecommendations(recommendations);
        view.showRecommendedBooks(recommendations);

    }

    /**
     * Sorts recommendations highest recommendation rating to lowest
     * @param recommendations list of books to recommend
     */
    private void sortRecommendations(ArrayList<Pair<Book, Double>> recommendations) {

        mergeSort(recommendations);

    }

    /**
     * Recursive merge sort over recommendations
     * @param recommendations list of books to recommend
     */
    private static void mergeSort(ArrayList<Pair<Book, Double>> recommendations) {

        if (recommendations.size() < 2) {
            return;
        }
        int mid = recommendations.size() / 2;
        ArrayList<Pair<Book, Double>> left = new ArrayList<>(mid);
        ArrayList<Pair<Book, Double>> right = new ArrayList<>(recommendations.size() - mid);
        for (int i = 0; i < mid; i++) {
            left.add(recommendations.get(i));
        }
        for (int i = mid; i < recommendations.size(); i++) {
            right.add(recommendations.get(i));
        }
        mergeSort(left);
        mergeSort(right);
        merge(recommendations, left, right, mid, recommendations.size() - mid);

    }

    /**
     * Merge function for merge sort
     * @param recommendations list of books to recommend
     * @param leftArray left half of the array
     * @param rightArray right half of the array
     * @param left left index
     * @param right right index
     */
    public static void merge(ArrayList<Pair<Book, Double>> recommendations, ArrayList<Pair<Book, Double>> leftArray,
                             ArrayList<Pair<Book, Double>> rightArray, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (leftArray.get(i).second >= rightArray.get(j).second) {
                recommendations.set(k++, leftArray.get(i++));
            }
            else {
                recommendations.set(k++, rightArray.get(j++));
            }
        }
        while (i < left) {
            recommendations.set(k++, leftArray.get(i++));
        }
        while (j < right) {
            recommendations.set(k++, rightArray.get(j++));
        }

    }

}
