package com.jakarinc.jakar.RemoteIO;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jakarinc.jakar.Domain.Lugar;
import com.jakarinc.jakar.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Conta Única on 04/11/2016.
 */

public class getPlaces implements IgetPlaces {

    @Override
    public void fetchInRadius(int radius, double lat, double lon, Context c, final ArrayList<Lugar> listaObservavel, final callback a) {
        Map<String, String> requisicaoStrings = new HashMap<>();
        requisicaoStrings.put("radius", "" + radius);
        requisicaoStrings.put("lat", "" + lat);
        requisicaoStrings.put("long", "" + lon);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                Constants.ServerAddres + "/getPlaces.php",
                new JSONObject(requisicaoStrings),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Lugar> retorno = JSONArrayToLugarList(response.getJSONArray("locais"));
                            listaObservavel.addAll(retorno);
                            a.callbackFunction();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        );
        RequestQueue fila = Volley.newRequestQueue(c);
        fila.add(request);

    }

    public List<Lugar> JSONArrayToLugarList(JSONArray array) throws JSONException {
        ArrayList<Lugar> retorno = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            retorno.add(new Lugar(
                    array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                    array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"),
                    array.getJSONObject(i).getString("place_id")

            ));
        }

        return retorno;
    }

    public interface callback {
        public void callbackFunction();
    }
}
