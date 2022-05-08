package com.yn_1.novello_app.volley_requests;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.yn_1.novello_app.AppController;
import com.yn_1.novello_app.Const;

import java.util.Map;

/**
 * Volley string request class.
 *
 * @author Maxim Popov
 */
public class StringRequester implements Requester<String> {

    // Request tag for debugging.
    public static final String TAG="string_req";

    @Override
    public void getRequest(String path, String get, VolleyCommand command,
                             Map<String, String> headers, Map<String, String> params) {
        StringRequest getStringRequest = new StringRequest(
            Request.Method.GET, Const.baseUrl+path,
            response -> {
                Log.d(TAG, response.toString());
                command.execute(response);
            }, error -> {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "Error: " + error.getMessage());
                command.onError(error);
            })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers == null) {
                    return super.getHeaders();
                }
                else {
                    return headers;
                }
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (headers == null) {
                    return super.getParams();
                }
                else {
                    return headers;
                }
            }
        };
        AppController.getInstance().addToRequestQueue(getStringRequest, TAG);
    }

    @Override
    public void postRequest(String path, String post, VolleyCommand command,
                            Map<String, String> headers, Map<String, String> params) {
        StringRequest postStringRequest = new StringRequest(
            Request.Method.POST, Const.baseUrl+path,
            response -> {
                Log.d(TAG, response.toString());
                command.execute(response);
            }, error -> {
                command.onError(error);
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "Error: " + error.getMessage());
            })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers == null)
                    return super.getHeaders();
                else
                    return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (headers == null)
                    return super.getParams();
                else
                    return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(postStringRequest, TAG);
    }

    @Override
    public void putRequest(String path, String put, VolleyCommand command,
                           Map<String, String> headers, Map<String, String> params) {
        StringRequest putStringRequest = new StringRequest(
            Request.Method.PUT, Const.baseUrl+path,
            response -> {
                Log.d(TAG, response.toString());
                command.execute(response);
            }, error -> {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "Error: " + error.getMessage());
                command.onError(error);
            })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers == null)
                    return super.getHeaders();
                else
                    return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (headers == null)
                    return super.getParams();
                else
                    return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(putStringRequest, TAG);
    }

    @Override
    public void deleteRequest(String path, String delete, VolleyCommand command,
                              Map<String, String> headers, Map<String, String> params) {
        StringRequest deleteStringRequest = new StringRequest(
            Request.Method.DELETE, Const.baseUrl+path,
            response -> {
                Log.d(TAG, response.toString());
                command.execute(response);
            }, error -> {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "Error: " + error.getMessage());
                command.onError(error);
            })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers == null)
                    return super.getHeaders();
                else
                    return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (headers == null)
                    return super.getParams();
                else
                    return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(deleteStringRequest, TAG);
    }

}
