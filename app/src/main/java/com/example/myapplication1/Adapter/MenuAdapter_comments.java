package com.example.myapplication1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Item_comments;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class MenuAdapter_comments extends RecyclerView.Adapter<MenuAdapter_comments.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mImageHero;
        private TextView mTextName;
        private ImageButton img_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageHero = itemView.findViewById(R.id.name_member_comments);
            mTextName = itemView.findViewById(R.id.edt_comments);
            img_button = itemView.findViewById(R.id.delete_comments);
        }
    }
    private Context mContext;
    private ArrayList<Item_comments> mHeros;

    public MenuAdapter_comments(Context mContext, ArrayList<Item_comments> mHeros) {
        this.mContext = mContext;
        this.mHeros = mHeros;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.dong_item_comments, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item_comments hero = mHeros.get(position);
        holder.mImageHero.setText(hero.getName_members());
        holder.mTextName.setText(hero.getComments());

        holder.img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Bạn có muốn xóa bình luận không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHeros.remove(position);
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
    }

    @Override
    public int getItemCount() {
        return mHeros.size();
    }
}
