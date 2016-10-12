package com.jakarinc.jakar.Controller.Profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakarinc.jakar.Controller.Galeria.GaleriaFragment;
import com.jakarinc.jakar.Domain.Imagem;
import com.jakarinc.jakar.R;
import com.jakarinc.jakar.RemoteIO.FetchSalaoData;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Estabelecimento_profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Estabelecimento_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Estabelecimento_profile extends Fragment {

    private static final String ESTABELECIMENTO_ID = "estabelecimento_id";
    GaleriaFragment galeriaFragment;
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
        fragment.Estabelecimento_ID = estabelecimentoId;
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
        List<Imagem> images = new ArrayList<>();
        v = inflater.inflate(R.layout.fragment_estabelecimento_profile, container, false);
        FetchSalaoData.fillInformationInto(this, Estabelecimento_ID);
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
