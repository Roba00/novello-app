package com.yn_1.novello_app.volley_requests;

import java.util.Map;

/**
 * Volley requests interface
 *
 * @author Roba Abbajabal
 */
public interface Requester<E> {

    /**
     * Volley DELETE Method: <br>
     * For requesting a value to delete from the server.
     *
     * @param path URL path to the request
     * @param get Object to get
     * @param command Command to run upon a response
     * @param headers Headers for the request
     * @param params Parameters for the request
     */
    void getRequest(String path, E get, VolleyCommand command,
                 Map<String, String> headers, Map<String, String> params);

    /**
     * Volley POST Method: <br>
     * For requesting a value to modify in the server.
     *
     * @param path URL path to the request
     * @param post Object to post
     * @param command Command to run upon a response
     * @param headers Headers for the request
     * @param params Parameters for the request
     */
    void postRequest(String path, E post, VolleyCommand command,
                     Map<String, String> headers, Map<String, String> params);

    /**
     * Volley PUT Method: <br>
     * For requesting a value to add to the server.
     *
     * @param path URL path to the request
     * @param put Object to put
     * @param command Command to run upon a response
     * @param headers Headers for the request
     * @param params Parameters for the request
     */
    void putRequest(String path, E put, VolleyCommand command,
                    Map<String, String> headers, Map<String, String> params);

    /**
     * Volley DELETE Method: <br>
     * For requesting a value to delete from the server.
     *
     * @param path URL path to the request
     * @param delete Object to delete
     * @param command Command to run upon a response
     * @param headers Headers for the request
     * @param params Parameters for the request
     */
    void deleteRequest(String path, E delete, VolleyCommand command,
                       Map<String, String> headers, Map<String, String> params);
}
