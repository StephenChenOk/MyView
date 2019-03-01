package com.chen.fy.contactinterface;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Persons> lists;

    MyAdapter(ArrayList<Persons> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_main, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = lists.get(i).getName();
        String word = lists.get(i).getWord();
        viewHolder.tv_word.setText(word);
        viewHolder.tv_name.setText(name);
        if (0 == i) {
            viewHolder.tv_word.setVisibility(View.VISIBLE);
        } else {
            if (word.equals(lists.get(i - 1).getWord())) {
                viewHolder.tv_word.setVisibility(View.GONE);
            } else {
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_word;
        private TextView tv_name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name);
            tv_word = itemView.findViewById(R.id.word);
        }
    }

}
