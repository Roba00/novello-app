package com.yn_1.novello_app.volley_requests;

import android.util.Log;
import androidx.annotation.Nullable;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.yn_1.novello_app.AppController;
import com.yn_1.novello_app.Const;

import org.json.JSONArray;

import java.util.Map;

/**
 * Volley JSON array request class.
 *
 * @author Roba Abbajabal
 */
public class JsonArrayRequester implements Requester<JSONArray> {

    // Request tag for debugging.
    public static final String TAG="json_array_req";

    @Override
    public void getRequest(String path, JSONArray get, VolleyCommand command,
                                Map<String, String> headers, Map<String, String> params) {
        JsonArrayRequest getJsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET, Const.baseUrl+path, get,
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
        AppController.getInstance().addToRequestQueue(getJsonArrayRequest, TAG);
    }

    @Override
    public void postRequest(String path, JSONArray post, VolleyCommand command,
                            Map<String, String> headers, Map<String, String> params) {
        JsonArrayRequest postJsonArrayRequest = new JsonArrayRequest(
            Request.Method.POST, Const.baseUrl+path, post,
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
        AppController.getInstance().addToRequestQueue(postJsonArrayRequest, TAG);
    }

    @Override
    public void putRequest(String path, JSONArray put, VolleyCommand command,
                           Map<String, String> headers, Map<String, String> params) {
        JsonArrayRequest putJsonArrayRequest = new JsonArrayRequest(
            Request.Method.PUT, Const.baseUrl+path, put,
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
        AppController.getInstance().addToRequestQueue(putJsonArrayRequest, TAG);
    }

    @Override
    public void deleteRequest(String path, JSONArray delete, VolleyCommand command,
                              Map<String, String> headers, Map<String, String> params) {
        JsonArrayRequest deleteJsonArrayRequest = new JsonArrayRequest(
            Request.Method.DELETE, Const.baseUrl+path, delete,
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
        AppController.getInstance().addToRequestQueue(deleteJsonArrayRequest, TAG);
    }
}
