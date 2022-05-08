package com.yn_1.novello_app.volley_requests;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.yn_1.novello_app.AppController;
import com.yn_1.novello_app.Const;

import java.util.Map;

/**
 * Volley image request class.
 *
 * @author Roba Abbajabal
 */
public class ImageRequester implements Requester<Bitmap> {

    // Request tag for debugging.
    public static final String TAG="image_req";

    @Override
    public void getRequest(String path, Bitmap get, VolleyCommand command,
                            Map<String, String> headers, Map<String, String> params) {
        ImageRequest getImageRequest = new ImageRequest(path,
            bitmap -> {
                Log.d(TAG, bitmap.toString());
                command.execute(bitmap);
            }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null,
            error -> {
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
        AppController.getInstance().addToRequestQueue(getImageRequest, TAG);
    }

    @Override
    public void postRequest(String path, Bitmap post, VolleyCommand command,
                            Map<String, String> headers, Map<String, String> params) {
        // Keep this empty
    }

    @Override
    public void putRequest(String path, Bitmap put, VolleyCommand command,
                           Map<String, String> headers, Map<String, String> params) {
        // Keep this empty
    }

    @Override
    public void deleteRequest(String path, Bitmap delete, VolleyCommand command,
                              Map<String, String> headers, Map<String, String> params) {
        // Keep this empty
    }
}
