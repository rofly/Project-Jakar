package com.jakarinc.jakar.RemoteIO;


import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakarinc.jakar.Controller.Galeria.GaleriaFragment;
import com.jakarinc.jakar.Controller.Profile.Estabelecimento_profile;
import com.jakarinc.jakar.Domain.Imagem;
import com.jakarinc.jakar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FetchSalaoData {

    private static ArrayList<Imagem> JSONtoArrayImageList(JSONArray vetorJSON, String salaoId, String salaoNome) throws JSONException {
        ArrayList<Imagem> retorno = new ArrayList<>();
        for (int i = 0; i < vetorJSON.length(); i++) {
            retorno.add(new Imagem(salaoId,
                    salaoNome,
                    vetorJSON.getJSONObject(i).getString("url_Imagem"),
                    vetorJSON.getJSONObject(i).getString("txt_Post")
            ));
        }
        return retorno;
    }

    /**
     * Este método pede os dados do salão informado em salaoId para o servidor
     * e popula os campos do fragmento informado em salaoFragment
     *
     * @param salaoId       a localizacao do salao para recuperar os dados
     * @param salaoFragment o fragmento onde serão exibidos os dados do salão
     */
    public static void fillInformationInto(final Estabelecimento_profile salaoFragment, final String salaoId) {
        Map<String, String> requisicaoStrings = new HashMap<>();
        requisicaoStrings.put("request_type", "fetchAll");
        requisicaoStrings.put("salaoId", salaoId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                "http://digaobeta.herokuapp.com/JakarHost/fetchPosts.php",
                new JSONObject(requisicaoStrings),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("status").equals("ok")) {
                                String nomeSalao = response.getJSONObject("salaoData").getString("nom_Salao");
                                String avaliacao = response.getJSONObject("salaoData").getString("ava_Salao");
                                ArrayList<Imagem> galeriaImagens = JSONtoArrayImageList(response.getJSONArray("post"), salaoId, nomeSalao);
                                ((TextView) salaoFragment.getView().findViewById(R.id.estabelecimento_nome)).
                                        setText(nomeSalao);
                                ((TextView) salaoFragment.getView().findViewById(R.id.avalicao_text)).
                                        setText(avaliacao);
                                ImageView cover = (ImageView) salaoFragment.getView().findViewById(R.id.cover_img);
                                Glide.with(salaoFragment.getContext()).load("http://digaobeta.herokuapp.com/JakarHost/img/"
                                        + salaoId
                                        + "/cover.jpg")
                                        .thumbnail(0.5f)
                                        .placeholder(R.drawable.profile_placeholder)
                                        .error(R.drawable.img_error)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .fitCenter()
                                        .into(cover);
                                FragmentManager manager = salaoFragment.getChildFragmentManager();
                                GaleriaFragment galeriaFragment = GaleriaFragment.newInstance(galeriaImagens);
                                manager.beginTransaction()
                                        .replace(R.id.galeria_salao_holder, galeriaFragment, galeriaFragment.getTag())
                                        .addToBackStack(galeriaFragment.getTag())
                                        .commit();


                            }
                            // TODO popular com dados do Google Maps
                            // else if(salaoDoGoogle){}
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
        RequestQueue fila = Volley.newRequestQueue(salaoFragment.getContext());
        fila.add(request);
    }


}
