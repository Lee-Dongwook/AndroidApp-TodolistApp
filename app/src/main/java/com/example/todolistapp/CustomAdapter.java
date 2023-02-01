package com.example.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    private ArrayList<Todoitem> mTodoItems;
    private Context mContext;
    private DBHelper mDBHelper;

    public CustomAdapter(ArrayList<Todoitem> mTodoitems, Context mContext, DBHelper mDBHelper)
    {
        this.mTodoItems = mTodoitems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

        holder.tv_title.setText(mTodoItems.get(position).getTitle());
        holder.tv_content.setText(mTodoItems.get(position).getContent());
        holder.tv_writeDate.setText(mTodoItems.get(position).getWriteDate());


    }

    @Override
    public int getItemCount() {
        return mTodoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_writeDate;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_writeDate = itemView.findViewById(R.id.tv_date);
        }

    }
}
