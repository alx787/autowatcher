package ru.ath.athautowatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.ath.athautowatcher.data.Transport;

public class TransportsAdapter extends RecyclerView.Adapter<TransportsAdapter.TransportsViewHolder> {

    private ArrayList<Transport> transports;

    public TransportsAdapter(ArrayList<Transport> transports) {
        this.transports = transports;
    }

    // привязка макета элемента к адаптеру
    @NonNull
    @Override
    public TransportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transport_item, parent, false);
        return new TransportsViewHolder(view);
    }

    // заполнение полей макета элемента
    @Override
    public void onBindViewHolder(@NonNull TransportsViewHolder holder, int position) {
        Transport tr = transports.get(position);
        holder.textViewNm.setText(tr.getWlnnm());
        holder.textViewModel.setText(tr.getModel());
        holder.textViewAtlocation.setText(tr.getAtlocation());
        holder.textViewAtinvnom.setText(tr.getAtinvnom());
    }

    @Override
    public int getItemCount() {
        return transports.size();
    }

    class TransportsViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNm;
        private TextView textViewModel;
        private TextView textViewAtlocation;
        private TextView textViewAtinvnom;


        public TransportsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNm = itemView.findViewById(R.id.textViewNm);
            textViewModel = itemView.findViewById(R.id.textViewModel);
            textViewAtlocation = itemView.findViewById(R.id.textViewAtlocation);
            textViewAtinvnom = itemView.findViewById(R.id.textViewAtinvnom);
        }
    }
}
