package com.ups.pandorasbox.tensorflowclient.nonactivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ups.pandorasbox.tensorflowclient.Boxinator;

import com.ups.pandorasbox.tensorflowclient.nonactivity.Callback.VolleyCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hgg0qfv on 6/29/2017.
 */

public class ServerRequest
{

    public void postImage(final String imageString, final VolleyCallback volleyCallback)
    {
        RequestQueue postRQ = Volley.newRequestQueue(Boxinator.getAppContext());
        String getURL  = "http://ups-tensorflow.eastus2.cloudapp.azure.com";
        String postURL = "http://ups-tensorflow.eastus2.cloudapp.azure.com/postImage";
        //String postURL = "http://192.168.43.19/postImage";
        //final String imageString = encodeImageToBase64(imagePath);
        //decodeImageFromBase64(imageString, imagePath + ".jpg");

        StringRequest stringRequest = new StringRequest(
            Request.Method.POST, postURL,
                // Listen for a response
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        volleyCallback.onSuccess(response);
                    }
                },
                // Listen for an error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                }
            )
        {
            // Add parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                System.out.println(imageString);
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("sampleFile", imageString);
                return parameters;
            }
        };

        // Add the request to the RequestQueue
        postRQ.add(stringRequest);
    }
}
