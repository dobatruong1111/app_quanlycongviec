package com.example.myapplication1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication1.Model.Item_list_job;
import com.example.myapplication1.R;

import java.util.List;

public class MenuAdapter_list_job extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Item_list_job> list;

    public MenuAdapter_list_job(Context context, int layout, List<Item_list_job> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private  class  ViewHolder{
        CheckBox checkBox;
        TextView hoan_thanh;
        ImageButton imageButton;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = convertView.findViewById(R.id.checkbox_ten_cong_viec);
            viewHolder.hoan_thanh = convertView.findViewById(R.id.hoan_thanh);
            viewHolder.imageButton = convertView.findViewById(R.id.delete_ten_cong_viec);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.checkBox.setText(list.get(position).getTen_cong_viec());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.checkBox.isChecked()){
                    viewHolder.hoan_thanh.setVisibility(View.VISIBLE);
                }
                else {
                    viewHolder.hoan_thanh.setVisibility(View.INVISIBLE);
                }
            }
        });
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });
        return convertView;
    }
}
