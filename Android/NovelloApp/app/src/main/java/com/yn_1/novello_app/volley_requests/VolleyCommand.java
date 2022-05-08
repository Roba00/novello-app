package com.yn_1.novello_app.volley_requests;

import com.android.volley.VolleyError;

/**
 * Command structure for volley requests. <br>
 * Determines what code to execute upon a response.
 * @param <E> The data type of the request
 *
 * @author Roba Abbajabal
 */
public interface VolleyCommand<E> {
    /**
     * Command execute method.
     * @param data The data response of the request
     */
    void execute(E data);
    /**
     * Command error method.
     * @param error The error received
     */
    void onError(VolleyError error);
}
