package com.jakarinc.jakar.RemoteIO;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jakarinc.jakar.Controller.ConfirmaID.EsperandoMensagemActivity;
import com.jakarinc.jakar.Controller.Main.MainActivity;
import com.jakarinc.jakar.LocalIO.Impl.Auth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser {

    public static void registrar(String telefone, final Context c) {
        Map<String, String> requisicaoStrings = new HashMap<>();
        requisicaoStrings.put("request_type", "register");
        requisicaoStrings.put("telefone", telefone);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                "http://digaobeta.herokuapp.com/JakarHost/register.php",
                new JSONObject(requisicaoStrings),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("status").equals("atenticacao iniciada")) {
                                Toast.makeText(c, "Msg enviada", Toast.LENGTH_SHORT).show();
                            }
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

    public static void confirmar(final String telefone, String codigo, final EsperandoMensagemActivity c) {
        Map<String, String> requisicaoStrings = new HashMap<>();
        requisicaoStrings.put("request_type", "confirma");
        requisicaoStrings.put("telefone", telefone);
        requisicaoStrings.put("codigo", codigo);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                "http://digaobeta.herokuapp.com/JakarHost/register.php",
                new JSONObject(requisicaoStrings),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("STATUSSS" + response.get("status"));
                            if (response.get("status").equals("ok")) {
                                c.getTimer().cancel();
                                Auth autenticadorLocal = new Auth(c);
                                autenticadorLocal.logIn(telefone);
                                Intent intent = new Intent(c, MainActivity.class);
                                c.startActivity(intent);
                            } else {
                                Toast.makeText(c, "ERRROOU", Toast.LENGTH_SHORT).show();
                            }

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
}
