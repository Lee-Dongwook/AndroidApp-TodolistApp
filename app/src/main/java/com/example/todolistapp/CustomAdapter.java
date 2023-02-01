package com.example.todolistapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<Todoitem> mTodoItems;
    private Context mContext;
    private DBHelper mDBHelper;

    public CustomAdapter(ArrayList<Todoitem> mTodoitems, Context mContext) {
        this.mTodoItems = mTodoitems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_writeDate = itemView.findViewById(R.id.tv_date);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition();
                    Todoitem todoItem = mTodoItems.get(curPos);

                    String[] strChoiceItems = {"수정하기", "삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("원하는 작업을 선택하세요.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if(position == 0)
                            {
                                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.dialog_edit);
                                EditText et_title = dialog.findViewById(R.id.et_title);
                                EditText et_content = dialog.findViewById(R.id.et_content);
                                Button btn_ok = dialog.findViewById(R.id.btn_ok);

                                et_title.setText(todoItem.getTitle());
                                et_content.setText(todoItem.getContent());

                                et_title.setSelection(et_title.getText().length()-1);

                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String title = et_title.getText().toString();
                                        String content = et_content.getText().toString();
                                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                        String beforeTime = todoItem.getWriteDate();

                                        mDBHelper.Update(et_title.getText().toString(), et_content.getText().toString(), currentTime,beforeTime);

                                        todoItem.setTitle(title);
                                        todoItem.setContent(content);
                                        todoItem.setWriteDate(currentTime);
                                        notifyItemChanged(curPos,todoItem);
                                        dialog.dismiss();
                                        Toast.makeText(mContext, "목록 수정이 완료 되었습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                dialog.show();
                            }
                            else if(position == 1)
                            {
                                String beforeTime = todoItem.getWriteDate();
                                mDBHelper.Delete(beforeTime);


                                mTodoItems.remove(curPos);
                                notifyItemRemoved(curPos);
                                Toast.makeText(mContext, "목록이 제거 되었습니다", Toast.LENGTH_SHORT).show();

                            }
                        }


                    });
                    builder.show();
                }

            });
        }
    }

    public void addItem(Todoitem _item)
    {
        mTodoItems.add(0,_item);
        notifyItemInserted(0);

    }


}