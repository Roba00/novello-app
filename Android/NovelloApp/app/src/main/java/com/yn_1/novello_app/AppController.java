package com.yn_1.novello_app;

import android.app.Application;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Network;
import com.android.volley.Cache;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Application class for creating and configuring volley requests.<br>
 * This classes' code will be executed first, before the main activity. <br>
 *
 * @author Roba Abbajabal
 */
public class AppController extends Application {

    // Variable to hold AppController instance
    private static AppController instance;

    // Variable for the request queue
    private RequestQueue requestQueue;

    // Variable for the HTTP network
    private Network network;

    // Variable for setting up the cache of the network
    private Cache cache;

    // Variable for setting up image loader, to be used for image requests
    private ImageLoader imageLoader;

    // Variable for class tag, to be used for requests
    public final String TAG = AppController.class.getSimpleName();

    /**
     * Creates an instance of the Application class.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        createRequestQueue();
        Log.d(TAG, "App Controller Started");
    }

    /**
     * Gets the instance of AppController, without thread interference.
     * @return Instance of AppController class.
     */
    public static synchronized AppController getInstance() {
        return instance;
    }

    /**
     * Creates the cache and the network to be used for volley requests.
     * Also calls createImageLoader, for image requests.
     * Creates the request queue after the cache and the network are instantiated.
     */
    private void createRequestQueue() {
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        network = new BasicNetwork(new HurlStack());
        // requestQueue = new RequestQueue(cache, network);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        createImageLoader();
    }

    /**
     * Starts the request queue.
     */
    public void startRequestQueue() {
        requestQueue.start();
    }

    /**
     * Ends the request queue.
     */
    public void endRequestQueue() {
        requestQueue.stop();
    }

    /**
     * Adds request to queue.
     * @param request Request to add.
     * @param tag The tag of the request.
     * @param <T> The class of request, of wildcard typing.
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        requestQueue.add(request);
    }

    /**
     * Cancels requests from queue.
     * @param tag What type of requests to remove.
     */
    public void cancelAllPendingRequest(Object tag) {
        if (requestQueue != null)
        {
            requestQueue.cancelAll(tag);
        }
    }

    /**
     * Creates and configures the image loader, <br>
     * to be use for loading images obtained from volley requests.
     */
    public void createImageLoader()
    {
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(Const.baseUrl);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    /**
     * Returns the image loader for loading image requests.
     * @return The image loader.
     */
    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }
}
