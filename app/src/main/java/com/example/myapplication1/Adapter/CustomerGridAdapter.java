package com.example.myapplication1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication1.Model.Item_labels;
import com.example.myapplication1.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CustomerGridAdapter extends BaseAdapter {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    private List<Item_labels> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomerGridAdapter(ArrayList<Item_labels> list, Context acontext) {
        this.list = list;
        this.layoutInflater = layoutInflater.from(acontext);
        this.context = acontext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.customer_grid_layout_labels,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.item_grid_name);
            viewHolder.imageView = convertView.findViewById(R.id.item_grid_color);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getLabels_name());
        viewHolder.textView.setBackground(list.get(position).getColor());
        viewHolder.imageView.setImageDrawable(list.get(position).getColor());
        return convertView;
    }
}
