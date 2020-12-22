package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Activity.ListCardActivity;
import com.example.myapplication1.Model.Group;
import com.example.myapplication1.Model.ListCard;
import com.example.myapplication1.Model.Personal;
import com.example.myapplication1.Model.Project;
import com.example.myapplication1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;



public class RecyclerViewProjectAdapter extends RecyclerView.Adapter<RecyclerViewProjectAdapter.MyViewHolderProject> {

    private Context mContext;
    private List<Project> mData;

    public RecyclerViewProjectAdapter(Context mContext, List<Project> mData) {
        this.mContext = mContext;
        this.mData = mData;
        setHasStableIds(true);
    }

    public RecyclerViewProjectAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolderProject onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.cardview_list_project, parent, false);
        view.setPadding(20, 20, 20, 20);

        return new MyViewHolderProject(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderProject holder, int position) {
        holder.tv_project_name.setText(mData.get(position).getName());

        if (mData.get(position) instanceof Personal) {
            holder.tv_member.setText("Personal");
        }
        if (mData.get(position) instanceof Group) {
            if ((((Group) mData.get(position)).getIDMember()) != null) {
                holder.tv_member.setText(((Group) mData.get(position)).getIDMember().size() + " Member");
            }
            else{
                holder.tv_member.setText("0 Member");
            }
        }

        //Handle event click more
        holder.imgbtn_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext,holder.imgbtn_popup);
                Menu menu = popupMenu.getMenu();
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_project, menu);

                MenuItem itempin = menu.findItem(R.id.pin_project);
                MenuItem itemfavorite = menu.findItem(R.id.favorite_project);

                FirebaseDatabase.getInstance().getReference("Pin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Project project = snap.getValue(Project.class);
                            if(mData.get(position).getIdKey().equals(project.getIdKey())){
                                itempin.setTitle("Remove Pin");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FirebaseDatabase.getInstance().getReference("Favorite").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Project project = snap.getValue(Project.class);
                            if(mData.get(position).getIdKey().equals(project.getIdKey())){
                                itemfavorite.setTitle("UnFavorite");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.delete_project: {
                                if(mData.get(position) instanceof  Personal) {
                                    FirebaseDatabase.getInstance().getReference("Personal").child(mData.get(position).getIdKey()).removeValue();
                                    notifyDataSetChanged();
                                }
                                else{
                                    FirebaseDatabase.getInstance().getReference("Group").child(mData.get(position).getIdKey()).removeValue();
                                    notifyDataSetChanged();
                                }
                                return true;
                            }
                            case R.id.edit_project:
                                {
                                    holder.edt_name_project.setVisibility(View.VISIBLE);
                                    holder.tv_project_name.setVisibility(View.INVISIBLE);
                                    holder.imgbtn_done_edt_name_project.setVisibility(View.VISIBLE);
                                    holder.tv_member.setVisibility(View.INVISIBLE);

                                    holder.imgbtn_done_edt_name_project.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(holder.edt_name_project.getText().toString().equals("")){
                                                holder.edt_name_project.setError("Name Project is empty!", null);
                                                return;
                                            }

                                            holder.tv_project_name.setText(holder.edt_name_project.getText().toString());

                                            HashMap<String, Object> update = new HashMap<>();
                                            update.put("name", holder.edt_name_project.getText().toString());

                                            holder.tv_project_name.setVisibility(View.VISIBLE);
                                            holder.imgbtn_done_edt_name_project.setVisibility(View.INVISIBLE);
                                            holder.edt_name_project.setVisibility(View.INVISIBLE);
                                            holder.tv_member.setVisibility(View.VISIBLE);

                                            FirebaseDatabase.getInstance().getReference("Personal").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot snap : snapshot.getChildren()){
                                                        Personal ps = snap.getValue(Personal.class);
                                                        if(ps.getIdKey().equals(mData.get(position).getIdKey())){
                                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Personal");
                                                            reference.child(ps.getIdKey()).updateChildren(update);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            FirebaseDatabase.getInstance().getReference("Group").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot snap : snapshot.getChildren()){
                                                        Group gr = snap.getValue(Group.class);
                                                        if(gr.getIdKey().equals(mData.get(position).getIdKey())){
                                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Group");
                                                            reference.child(gr.getIdKey()).updateChildren(update);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            FirebaseDatabase.getInstance().getReference("Pin").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot snap : snapshot.getChildren()){
                                                        Project project = snap.getValue(Group.class);
                                                        if(project.getIdKey().equals(mData.get(position).getIdKey())){
                                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pin");
                                                            reference.child(project.getIdKey()).updateChildren(update);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            FirebaseDatabase.getInstance().getReference("Favorite").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot snap : snapshot.getChildren()){
                                                        Project project = snap.getValue(Group.class);
                                                        if(project.getIdKey().equals(mData.get(position).getIdKey())){
                                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favorite");
                                                            reference.child(project.getIdKey()).updateChildren(update);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }
                                    });
                                }
                                return true;
                            case R.id.pin_project:
                                {
                                    if(item.getTitle().equals("Pin")) {
                                        FirebaseDatabase.getInstance().getReference("Pin").child(mData.get(position).getIdKey()).setValue(mData.get(position));
                                    }
                                    else{
                                        FirebaseDatabase.getInstance().getReference("Pin").child(mData.get(position).getIdKey()).removeValue();
                                    }
                                }
                                return true;
                            case R.id.favorite_project:
                                {
                                    if(item.getTitle().equals("Favorite")) {
                                        FirebaseDatabase.getInstance().getReference("Favorite").child(mData.get(position).getIdKey()).setValue(mData.get(position));
                                    }
                                    else{
                                        FirebaseDatabase.getInstance().getReference("Favorite").child(mData.get(position).getIdKey()).removeValue();
                                    }
                                }
                                return true;


                        }

                        return true;
                    }
                });


                popupMenu.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(mContext.getApplicationContext(), ListCardActivity.class);
                intent.putExtra("Id_key",mData.get(position).getIdKey());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class MyViewHolderProject extends RecyclerView.ViewHolder {

        TextView tv_project_name, tv_member;
        ImageButton imgbtn_popup, imgbtn_done_edt_name_project;
        EditText edt_name_project;

        public MyViewHolderProject(@NonNull View itemView) {
            super(itemView);

            tv_project_name = (TextView) itemView.findViewById(R.id.tv_name_project);
            tv_member = (TextView) itemView.findViewById(R.id.tv_member_count);
            imgbtn_popup = (ImageButton) itemView.findViewById(R.id.imgbtn_popup_project);
            edt_name_project = (EditText) itemView.findViewById(R.id.ed_name_project);
            imgbtn_done_edt_name_project = (ImageButton) itemView.findViewById(R.id.img_btn_done);
        }
    }

}
