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

    private ArrayList<Transport> transports = new ArrayList<>();
    private OnTransportClickListener onTransportClickListener;

    interface OnTransportClickListener {
        void onTransportClick(int position);
    }

    public void setOnTransportClickListener(OnTransportClickListener onTransportClickListener) {
        this.onTransportClickListener = onTransportClickListener;
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
        holder.textViewRegplate.setText(tr.getRegistrationplate());
        holder.textViewModel.setText(tr.getModel());
        holder.textViewAutocloumnDepart.setText(tr.getAtautocol() + " - " + tr.getAtdepartment());
        holder.textViewAtinvnom.setText(tr.getAtinvnom());
    }

    @Override
    public int getItemCount() {
        return transports.size();
    }


    class TransportsViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewRegplate;
        private TextView textViewModel;
        private TextView textViewAutocloumnDepart;
        private TextView textViewAtinvnom;


        public TransportsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewRegplate = itemView.findViewById(R.id.textViewRegPlate);
            textViewModel = itemView.findViewById(R.id.textViewModel);
            textViewAutocloumnDepart = itemView.findViewById(R.id.textViewAutocloumnDepart);
            textViewAtinvnom = itemView.findViewById(R.id.textViewAtinvnom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTransportClickListener != null) {
                        onTransportClickListener.onTransportClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setTransports(ArrayList<Transport> trArr) {
        this.transports = trArr;
        notifyDataSetChanged();
    }
}
