package com.example.tl01e10004.configuracion;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactosViewHolder> {
    @NonNull
    @Override
    public ContactosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     //   View view= LayoutInflater.from(parent.getContext()).inflate(androidx.core.R.layout.Activitylist)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactosViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ContactosViewHolder extends RecyclerView.ViewHolder {
        public ContactosViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
