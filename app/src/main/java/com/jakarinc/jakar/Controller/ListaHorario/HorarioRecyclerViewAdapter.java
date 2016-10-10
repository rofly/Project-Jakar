package com.jakarinc.jakar.Controller.ListaHorario;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakarinc.jakar.Domain.Horario;
import com.jakarinc.jakar.R;

import java.util.List;


public class HorarioRecyclerViewAdapter extends RecyclerView.Adapter<HorarioRecyclerViewAdapter.ViewHolder> {


    private final List<Horario> hList;
    private final HorarioFragment.OnListFragmentInteractionListener mListener;

    public HorarioRecyclerViewAdapter(List<Horario> items, HorarioFragment.OnListFragmentInteractionListener listener) {
        hList = items;
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_horario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.h = hList.get(position);
        holder.mIdView.setText(String.valueOf(hList.get(position).getToken()));
        holder.mContentView.setText(String.valueOf(hList.get(position).getHorasTimeStamp()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.h);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Horario h;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

       /* @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }
}
