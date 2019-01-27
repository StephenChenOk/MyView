package com.chen.fy.dropdownbox;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> list;

    private ItemClickLister itemClickLister;

    public MyAdapter(List<String> list){
        this.list = list;
    }

    public void setItemClickLister(ItemClickLister itemClickLister){
        this.itemClickLister = itemClickLister;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //反射每行的子布局,并把view传入viewHolder中,以便获取控件对象
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_box, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final String msg = list.get(i);
        viewHolder.textView.setText(msg);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(msg);
                notifyDataSetChanged();
            }
        });

        if(itemClickLister != null){
            //点击
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickLister.onItemClick(i);
                }
            });
            //长按
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickLister.onLongClick(i);
                    return true;   //消化事件
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.delete);
        }
    }
}
