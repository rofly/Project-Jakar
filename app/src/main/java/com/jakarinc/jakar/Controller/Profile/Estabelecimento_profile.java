package com.jakarinc.jakar.Controller.Profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakarinc.jakar.Controller.Galeria.GaleriaFragment;
import com.jakarinc.jakar.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Estabelecimento_profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Estabelecimento_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Estabelecimento_profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ESTABELECIMENTO_ID = "estabelecimento_id";


    // TODO: Rename and change types of parameters
    private String Estabelecimento_ID;
    private View v;

    private OnFragmentInteractionListener mListener;

    public Estabelecimento_profile() {
        // Required empty public constructor
    }

    /**
     * @param estabelecimentoId O identificador do estabelecimento que será exibido.
     * @return A new instance of fragment Estabelecimento_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Estabelecimento_profile newInstance(String estabelecimentoId) {
        Estabelecimento_profile fragment = new Estabelecimento_profile();
        Bundle args = new Bundle();
        args.putString(ESTABELECIMENTO_ID, estabelecimentoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Estabelecimento_ID = getArguments().getString(ESTABELECIMENTO_ID);

        }
        setRetainInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_estabelecimento_profile, container, false);
        fillInformation(v, null);
        FragmentManager manager = getChildFragmentManager();
        GaleriaFragment galeriaFragment = GaleriaFragment.newInstance();
        manager.beginTransaction()
                .replace(R.id.galeria_salao_holder, galeriaFragment, galeriaFragment.getTag())
                .addToBackStack(galeriaFragment.getTag())
                .commit();
        return v;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        System.gc();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        v = null;
    }

    private void fillInformation(View v, String salaoId) {

        //TODO implementar a tela q mostra o salao

        TextView id = (TextView) v.findViewById(R.id.estabelecimento_nome);

        if (Estabelecimento_ID != null)
            id.setText(Estabelecimento_ID);

        ImageView cover = (ImageView) v.findViewById(R.id.cover_img);


        Glide.with(getContext()).load("http://digaobeta.herokuapp.com/JakarHost/img/carla.jpg")
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cover);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
