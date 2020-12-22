package com.example.myapplication1.Adapter;

//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.myapplication1.Object.Item_members;
//import com.example.myapplication1.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MenuAdapter_members extends BaseAdapter {
//
//    private Context context;
//    private int layout;
//    private List<Item_members> list;
//
//    public MenuAdapter_members(Context context, int layout, List<Item_members> list) {
//        this.context = context;
//        this.layout = layout;
//        this.list = list;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    private class ViewHolder{
//        ImageView avatar;
//        TextView name_members;
//        TextView email_members;
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        if(convertView == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(layout,null);
//            viewHolder = new ViewHolder();
//            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar_members);
//            viewHolder.name_members = (TextView) convertView.findViewById(R.id.name_members);
//            viewHolder.email_members = (TextView) convertView.findViewById(R.id.email_members);
//
//            convertView.setTag(viewHolder);
//        }
//        else{
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.avatar.setImageResource(list.get(position).getAvatar_members());
//        viewHolder.name_members.setText(list.get(position).getName_members());
//        viewHolder.email_members.setText(list.get(position).getEmail_members());
//
//        return convertView;
//    }
//}
