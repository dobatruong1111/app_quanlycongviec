package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;


import com.example.myapplication1.Model.ListCard;
import com.example.myapplication1.Adapter.ListCard_Adapter;
import com.example.myapplication1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ListCardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ListCard_Adapter adapter;
    ArrayList<ListCard> listcard = new ArrayList<>();
    FloatingActionButton arc;
    ImageButton search, menu, back, back_search;
    EditText editText_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        String Id_key = getIntent().getStringExtra("Id_key");
        ReadListCard(Id_key);
        ShowToolbar();
        searchAction();
        menuAction();
        arcAction(Id_key);
        Back();
    }
    public void ShowToolbar(){
        back_search.setVisibility(View.GONE);
        editText_search.setVisibility(View.GONE);
    }
    public void Back(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void searchAction(){
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.GONE);
                menu.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                back_search.setVisibility(View.VISIBLE);
                editText_search.setVisibility(View.VISIBLE);
            }
        });
        back_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.VISIBLE);
                menu.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                back_search.setVisibility(View.GONE);
                editText_search.setVisibility(View.GONE);
                editText_search.setText("");
            }
        });
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });
    }

    public void menuAction(){
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ListCardActivity.this,menu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_bang,popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

    public void arcAction(String Id_key){
        arc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ListCardActivity.this);
                dialog.setContentView(R.layout.arcmenu_dialog);
                final EditText text_inputarcmenu = (EditText) dialog.findViewById(R.id.edittext_arcmenu);

                Button buttonarcmenu_ok = (Button) dialog.findViewById(R.id.buttonarc_ok);
                buttonarcmenu_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Name = text_inputarcmenu.getText().toString();
                        writeListCard(Name,Id_key);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }


    public void writeListCard(String Name,String Id_key) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String Key_ListCard = databaseReference.push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Name", Name);
        hashMap.put("ID_listcard",Key_ListCard);
        hashMap.put("Id_key",Id_key);

        databaseReference.child("ListCard").child(Key_ListCard).setValue(hashMap);
    }

    public void ReadListCard(String Id_key) {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ListCard");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listcard.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ListCard listCard = dataSnapshot.getValue(ListCard.class);
                    if (listCard.getId_key().equals(Id_key))
                        listcard.add(listCard);
                }
                adapter = new ListCard_Adapter(listcard, ListCardActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Anhxa(){
        recyclerView = findViewById(R.id.rvList);
        search = findViewById(R.id.id_search);
        menu = findViewById(R.id.menu_listcard);
        arc = findViewById(R.id.f1);
        editText_search  = findViewById(R.id.search_edittext);
        back = findViewById(R.id.back);
        back_search = findViewById(R.id.back_search);
    }
}