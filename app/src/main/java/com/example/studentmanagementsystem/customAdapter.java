package com.example.studentmanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class customAdapter extends RecyclerView.Adapter<customAdapter.ViewHolder> {
    ArrayList<model> list=new ArrayList<>();
    public customAdapter(ArrayList<model> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_draw,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       int rescource=list.get(position).getImg();
       String name=list.get(position).getTitle();
       
       holder.setData(rescource,name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView classn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.classimg);
            classn=itemView.findViewById(R.id.classname);

        }

        public void setData(int rescource, String name) {
            imageView.setImageResource(rescource);
            classn.setText(name);
        }
    }
}
