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
    private ArrayList<Imagem> images;
    private AdaptadorGaleria mAdapter;
    private RecyclerView recyclerView;


    public static GaleriaFragment newInstance(ArrayList<Imagem> images) {
        GaleriaFragment fragment = new GaleriaFragment();
        fragment.setRetainInstance(false);
        fragment.images = images;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setImages(ArrayList<Imagem> images) {
        this.images = images;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View retorno = inflater.inflate(R.layout.galeria, container, false);
        recyclerView = (RecyclerView) retorno.findViewById(R.id.recycler_view_galeria);
        mAdapter = new AdaptadorGaleria(getContext(), images);

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
