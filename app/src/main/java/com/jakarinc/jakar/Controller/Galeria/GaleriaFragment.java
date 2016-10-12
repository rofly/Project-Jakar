package com.jakarinc.jakar.Controller.Galeria;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakarinc.jakar.Controller.Galeria.Adaptador.AdaptadorGaleria;
import com.jakarinc.jakar.Domain.Imagem;
import com.jakarinc.jakar.R;

import java.util.ArrayList;


public class GaleriaFragment extends Fragment implements AdaptadorGaleria.ClickListener {

    private String TAG = getTag();
    //private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private ArrayList<Imagem> images;
    private AdaptadorGaleria mAdapter;
    private RecyclerView recyclerView;


    public static GaleriaFragment newInstance() {
        GaleriaFragment fragment = new GaleriaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View retorno = inflater.inflate(R.layout.galeria, container, false);
        recyclerView = (RecyclerView) retorno.findViewById(R.id.recycler_view_galeria);
        images = new ArrayList<>();
        mAdapter = new AdaptadorGaleria(getContext(), images);


        images.add(new Imagem("c01", "Carlosa", "http://1.bp.blogspot.com/-BN4yCmhg61g/UMsCARcOfUI/AAAAAAAAEkM/oXZRoyDBu2Q/s1600/14.jpg"));
        images.add(new Imagem("c01", "Carlosa", "https://pbs.twimg.com/media/ChySsFaUUAA6jik.jpg"));
        images.add(new Imagem("c01", "Carlosa",
                "https://scontent-grt.xx.fbcdn.net/v/t34.0-12/14642586_1062856430500441_234982620_n.jpg?oh=5137e3768d5965ec73f10fa386d0129b&oe=57FFEAD2"));

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new
                AdaptadorGaleria.RecyclerTouchListener(getContext(), recyclerView, new AdaptadorGaleria.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                SlideShowFragment newFragment = SlideShowFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return retorno;
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }


}
