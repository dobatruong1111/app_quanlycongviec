package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication1.Adapter.MenuAdapter_attachments;
import com.example.myapplication1.Adapter.MenuAdapter_list_job;
//import com.example.myapplication1.Adapter.MenuAdapter_members;
import com.example.myapplication1.Model.Card;
import com.example.myapplication1.Model.Item_Date;
import com.example.myapplication1.Model.Item_attachments;
import com.example.myapplication1.Model.Item_comments;
import com.example.myapplication1.Model.Item_labels;
import com.example.myapplication1.Model.Item_list_job;
import com.example.myapplication1.Model.Item_members;
import com.example.myapplication1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CardActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView title_toolbar;
    private ImageButton more_vert;
    private ImageButton arrow_back;
    private ImageButton close_toolbar_edit_the_name_of_job,close_toolbar_labels,close_comments;
    private ImageButton check_toolbar, check_comments;
    private TextView the_name_of_job;
    private TextView list_name;
    private EditText edit_the_name_of_job, edit_the_name_of_job1;
    private TextView labels;
    private TextView members;
    private TextView end_date;
    private TextView list_job;
    private TextView attachments;
    private ImageView move_focus;
    private EditText comments;
    private LinearLayout linearLayout_labels;
    private CheckBox checkbox_mau_vang,checkbox_mau_cam,checkbox_mau_tim,checkbox_mau_than,checkbox_mau_xam;
    private EditText mau_vang;
    private EditText mau_tim;
    private EditText mau_than;
    private EditText mau_cam;
    private EditText mau_xam;
    private EditText mau_vang1;
    private ImageButton but_mau_vang,but_mau_tim,but_mau_than,but_mau_cam,but_mau_xam;
    private LinearLayout linearLayout_1,linearLayout_2,linearLayout_3,linearLayout_4,linearLayout_5;
    private String date,day,time;


    ListView listView_list_job;
    ArrayList<Item_list_job> arrayList_list_job;
    MenuAdapter_list_job adapter_list_job;

    View edit_list_job;
    ImageButton close_list_job;
    ImageButton check_list_job;
    ImageButton more_vert_list_job;
    LinearLayout linearLayout_list_job;
    EditText ten_cong_viec_list_job;

    ListView listView_attachments;
    ArrayList<Item_attachments> arrayList_attachments;
    MenuAdapter_attachments adapter_attachments;

    View edit_list_attachments;
    ImageButton close_list_attachments;
    ImageButton check_list_attachments;
    ImageButton more_vert_list_attachments;
    LinearLayout linearLayout_list_attachments;
    Intent intent;
    EditText tep_dinh_kem_list_attachments;

    GridView gridView_labels;
    com.example.myapplication1.Adapter.CustomerGridAdapter gridAdapter;
    ArrayList<Item_labels> arrayList_labels;

    View view_body,view_comments;
    LinearLayout linearLayout_parent;
    LinearLayout linearLayout_body;

    ArrayList<Item_comments> arrayList_comments;
    RecyclerView mRecyclerView_comments;
    com.example.myapplication1.Adapter.MenuAdapter_comments adapter_comments;
    Card card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        AnhXa();
        intent = getIntent();
        String card_id = intent.getStringExtra("Card_id");
        linearLayout_labels.setVisibility(View.GONE);
        actionToolbar();//toolbar
        actionLabels();//labels
        action_popup_menu_toolbar();//more_vert
        action_end_date_time(card_id);//end_date
        action_edit_the_name_of_job();//edit_the_name_of_job;
        action_arrow_back();
        action_members();
        action_attachments();
        action_list_job();
        action_comments();
        readDate(card_id);

        arrayList_labels = new ArrayList<>();
        gridAdapter = new com.example.myapplication1.Adapter.CustomerGridAdapter(arrayList_labels,this);
        gridView_labels.setAdapter(gridAdapter);

        arrayList_comments = new ArrayList<>();
        adapter_comments = new com.example.myapplication1.Adapter.MenuAdapter_comments(this,arrayList_comments);
        mRecyclerView_comments.setAdapter(adapter_comments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,true);
        linearLayoutManager.scrollToPosition(1);
        mRecyclerView_comments.setLayoutManager(linearLayoutManager);

        /*arrayList = new ArrayList<>();
        arrayList.add(new Item_members(R.drawable.ic_baseline_attach_file_24,"nguyentuan",
                "nguyentuan@gmail.com"));
        arrayList.add(new Item_members(R.drawable.ic_baseline_close_24,"hello","777"));
        adapter = new MenuAdapter_members(MainActivity.this,R.layout.dong_item_members,arrayList);
        listView.setAdapter(adapter);*/

    }

    private void action_comments(){
        comments.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    title_toolbar.setText("Bình luận");
                    arrow_back.setVisibility(View.GONE);
                    more_vert.setVisibility(View.GONE);
                    close_comments.setVisibility(View.VISIBLE);
                    check_comments.setVisibility(View.VISIBLE);
                }
            }
        });
        close_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_toolbar.setText("");
                arrow_back.setVisibility(View.VISIBLE);
                more_vert.setVisibility(View.VISIBLE);
                close_comments.setVisibility(View.GONE);
                check_comments.setVisibility(View.GONE);
                comments.setText("");
            }
        });
        check_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_comments.add(new Item_comments("Tuấn",comments.getText().toString()));
                adapter_comments.notifyDataSetChanged();
                title_toolbar.setText("");
                arrow_back.setVisibility(View.VISIBLE);
                more_vert.setVisibility(View.VISIBLE);
                close_comments.setVisibility(View.GONE);
                check_comments.setVisibility(View.GONE);
                linearLayout_parent.requestFocus();
                hideSoftInputFromWindow(comments);
                comments.setText("");
            }
        });

        if(arrayList_comments!=null &&arrayList_comments.size()!=0){
            view_comments.setVisibility(View.VISIBLE);
        }else if(arrayList_comments !=null && arrayList_comments.size() ==0){
            view_comments.setVisibility(View.GONE);
        }
    }

    private void action_list_job() {
        arrayList_list_job = new ArrayList<>();
        adapter_list_job = new MenuAdapter_list_job(CardActivity.this,R.layout.dong_item_list_job,arrayList_list_job);
        listView_list_job.setAdapter(adapter_list_job);

        linearLayout_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_parent.requestFocus();
                edit_list_job.setVisibility(View.VISIBLE);
                linearLayout_list_job.setVisibility(View.VISIBLE);
                check_list_job.setVisibility(View.VISIBLE);
                close_list_job.setVisibility(View.VISIBLE);
                ten_cong_viec_list_job.requestFocus();
                showSoftInput(ten_cong_viec_list_job);
            }
        });
        if (ten_cong_viec_list_job.getText().length() != 0) {
            check_list_job.setEnabled(true);
        }

        close_list_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ten_cong_viec_list_job.getText().length() ==0){
                    ten_cong_viec_list_job.setHint("Tên công việc");
                }
                linearLayout_list_job.setVisibility(View.GONE);
                ten_cong_viec_list_job.setText("");
                hideSoftInputFromWindow(ten_cong_viec_list_job);
            }
        });
        check_list_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ten_cong_viec_list_job.getText().length() !=0){
                    arrayList_list_job.add(new Item_list_job(ten_cong_viec_list_job.getText().toString()));
                    adapter_list_job.notifyDataSetChanged();
                }
                ten_cong_viec_list_job.setText("");
            }
        });
        more_vert_list_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CardActivity.this,more_vert_list_job);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_more_vert_list_job,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.an_more_vert_list_job:
                                close_list_job.setVisibility(View.INVISIBLE);
                                check_list_job.setVisibility(View.INVISIBLE);
                                linearLayout_list_job.setVisibility(View.GONE);
                                ten_cong_viec_list_job.setText("");
                                hideSoftInputFromWindow(ten_cong_viec_list_job);
                                break;
                            case R.id.xoa_more_vert_list_job:
                                arrayList_list_job.clear();
                                adapter_list_job.notifyDataSetChanged();
                                edit_list_job.setVisibility(View.GONE);
                                ten_cong_viec_list_job.setText("");
                                hideSoftInputFromWindow(ten_cong_viec_list_job);
                                break;
                            default:break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }
    private void action_attachments() {
        arrayList_attachments = new ArrayList<>();
        adapter_attachments = new MenuAdapter_attachments(CardActivity.this,R.layout.dong_item_attachments,arrayList_attachments);
        listView_attachments.setAdapter(adapter_attachments);
        linearLayout_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_parent.requestFocus();
                edit_list_attachments.setVisibility(View.VISIBLE);
                linearLayout_list_attachments.setVisibility(View.VISIBLE);
                close_list_attachments.setVisibility(View.VISIBLE);
                check_list_attachments.setVisibility(View.VISIBLE);
                tep_dinh_kem_list_attachments.requestFocus();
                showSoftInput(tep_dinh_kem_list_attachments);
            }
        });
        if (tep_dinh_kem_list_attachments.getText().length() != 0) {
            check_list_attachments.setEnabled(true);
        }
        close_list_attachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tep_dinh_kem_list_attachments.getText().length() ==0){
                    tep_dinh_kem_list_attachments.setHint("Tên công việc");
                }
                linearLayout_list_attachments.setVisibility(View.GONE);
                tep_dinh_kem_list_attachments.setText("");
                hideSoftInputFromWindow(tep_dinh_kem_list_attachments);
            }
        });
        check_list_attachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tep_dinh_kem_list_attachments.getText().length() !=0){
                    arrayList_attachments.add(new Item_attachments(tep_dinh_kem_list_attachments.getText().toString()));
                    adapter_attachments.notifyDataSetChanged();
                }
                tep_dinh_kem_list_attachments.setText("");
            }
        });
        more_vert_list_attachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CardActivity.this,more_vert_list_attachments);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_more_vert_list_attachments,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.an_more_vert_list_attachments:
                                close_list_attachments.setVisibility(View.INVISIBLE);
                                check_list_attachments.setVisibility(View.INVISIBLE);
                                linearLayout_list_attachments.setVisibility(View.GONE);
                                tep_dinh_kem_list_attachments.setText("");
                                hideSoftInputFromWindow(tep_dinh_kem_list_attachments);
                                break;
                            case R.id.xoa_more_vert_list_attachments:
                                arrayList_attachments.clear();
                                adapter_attachments.notifyDataSetChanged();
                                edit_list_attachments.setVisibility(View.GONE);
                                tep_dinh_kem_list_attachments.setText("");
                                hideSoftInputFromWindow(tep_dinh_kem_list_attachments);
                                break;
                            default:break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void action_members() {
        linearLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_parent.requestFocus();
                Dialog dialog = new Dialog(CardActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customer_dialog_members);
                dialog.show();
            }
        });
    }

    private void action_arrow_back() {
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                hideSoftInputFromWindow(edit_the_name_of_job);
                hideSoftInputFromWindow(comments);
            }
        });
    }

    private void action_edit_the_name_of_job() {
        edit_the_name_of_job.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    arrow_back.setVisibility(View.GONE);
                    more_vert.setVisibility(View.GONE);
                    check_toolbar.setVisibility(View.VISIBLE);
                    close_toolbar_edit_the_name_of_job.setVisibility(View.VISIBLE);
                    title_toolbar.setText("Chỉnh sửa miêu tả");
                    view_body.setEnabled(false);
                }
            }
        });
        close_toolbar_edit_the_name_of_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back.setVisibility(View.VISIBLE);
                more_vert.setVisibility(View.VISIBLE);
                check_toolbar.setVisibility(View.GONE);
                close_toolbar_edit_the_name_of_job.setVisibility(View.GONE);
                title_toolbar.setText("");
                if(edit_the_name_of_job.getText().toString().equals("")){
                    edit_the_name_of_job.setText("");
                }
                linearLayout_parent.requestFocus();
                hideSoftInputFromWindow(edit_the_name_of_job);
            }
        });
        check_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back.setVisibility(View.VISIBLE);
                more_vert.setVisibility(View.VISIBLE);
                check_toolbar.setVisibility(View.GONE);
                close_toolbar_edit_the_name_of_job.setVisibility(View.GONE);
                title_toolbar.setText("");
                edit_the_name_of_job.setText(edit_the_name_of_job1.getText().toString());
                hideSoftInputFromWindow(edit_the_name_of_job);
            }
        });
    }

    private void action_popup_menu_toolbar() {
        more_vert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CardActivity.this,more_vert);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_toolbar,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.chia_se_lien_ket:
                                Toast.makeText(CardActivity.this,"Chia sẻ liên kết",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.xem:
                                Toast.makeText(CardActivity.this,"Xem",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.di_chuyen_the:
                                Toast.makeText(CardActivity.this,"Di chuyển thẻ",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.sao_chep_the:
                                Toast.makeText(CardActivity.this,"Sao chép thẻ",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.luu_tru:
                                Toast.makeText(CardActivity.this,"Lưu trữ",Toast.LENGTH_SHORT).show();
                                break;
                            default:break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void action_end_date_time(String card_id){
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeTime( card_id);
            }
        });
    }

    public void takeTime(String card_id){
        Calendar calendar = Calendar.getInstance();
        int year_now = calendar.get(Calendar.YEAR);
        int month_now = calendar.get(Calendar.MONTH);
        int day_now = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CardActivity.this, new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                day = simpleDateFormat.format(calendar.getTime());
                date = time +" " + date;
                writeDate(date,card_id);
            }

        },year_now , month_now, day_now);
        datePickerDialog.show();

        final int hour_now = calendar.get(Calendar.HOUR_OF_DAY);
        int minute_now = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CardActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                calendar.set(0,0,0,hourOfDay,minute);
                end_date.setText(simpleDateFormat.format(calendar.getTime()));
                time =  simpleDateFormat.format(calendar.getTime());

            }
        } ,hour_now, minute_now,false);
        timePickerDialog.show();
    }


    public void writeDate(String date, String id_card){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> Date = new HashMap<>();
        Date.put("date", date);
        Date.put("id_card", id_card);
        reference.child("Date").child(id_card).setValue(Date);
    }

    public void readDate(String id_card){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Date");
        reference.child(id_card).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Item_Date item_date =  snapshot.getValue(Item_Date.class);
                end_date.setText(item_date.getDate());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void actionLabels() {
        linearLayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_parent.requestFocus();
                linearLayout_labels.setVisibility(View.VISIBLE);
                arrow_back.setVisibility(View.GONE);
                close_toolbar_labels.setVisibility(View.VISIBLE);
                title_toolbar.setText("Chỉnh sửa nhãn");
                but_mau(but_mau_vang,mau_vang);
                but_mau(but_mau_cam,mau_cam);
                but_mau(but_mau_tim,mau_tim);
                but_mau(but_mau_than,mau_than);
                but_mau(but_mau_xam,mau_xam);
                close_toolbar_labels.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayout_labels.setVisibility(View.GONE);
                        arrow_back.setVisibility(View.VISIBLE);
                        close_toolbar_labels.setVisibility(View.GONE);
                        title_toolbar.setText("");
                        hideSoftInputFromWindow(mau_vang);
                        checkbox_mau(checkbox_mau_vang,mau_vang);
                        checkbox_mau(checkbox_mau_cam,mau_cam);
                        checkbox_mau(checkbox_mau_tim,mau_tim);
                        checkbox_mau(checkbox_mau_than,mau_than);
                        checkbox_mau(checkbox_mau_xam,mau_xam);
                    }
                });

            }
        });
    }
    private void checkbox_mau(CheckBox checkBox,EditText editText){
        if(checkBox.isChecked()){
            Item_labels a = new Item_labels(editText.getBackground(),editText.getText().toString());
            boolean count = false;
            for(int i=0; i<arrayList_labels.size(); i++){
                if(arrayList_labels.get(i).getColor().equals(a.getColor())
                        && arrayList_labels.get(i).getLabels_name().equals(a.getLabels_name())){
                    count = true;
                }
            }
            if(!count){
                arrayList_labels.add(a);
                gridAdapter.notifyDataSetChanged();
            }
            for(int i=0; i<arrayList_labels.size(); i++){
                if(arrayList_labels.get(i).getColor().equals(a.getColor())
                        && !arrayList_labels.get(i).getLabels_name().equals(a.getLabels_name())){
                    arrayList_labels.get(i).setLabels_name(editText.getText().toString());
                    arrayList_labels.remove(i);
                    gridAdapter.notifyDataSetChanged();
                }
            }

            editText.setEnabled(false);
        }
        else{
            Item_labels aa = new Item_labels(editText.getBackground(),editText.getText().toString());
            for(int i=0; i<arrayList_labels.size(); i++){
                if(arrayList_labels.get(i).getColor() == aa.getColor()
                        && arrayList_labels.get(i).getLabels_name().equals(aa.getLabels_name())){
                    arrayList_labels.remove(i);
                    gridAdapter.notifyDataSetChanged();
                }
            }
        }
        if(arrayList_labels.size() == 0){
            labels.setVisibility(View.VISIBLE);
        }else{
            labels.setVisibility(View.GONE);
        }
    }
    private void but_mau(ImageButton imageButton, EditText editText){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setEnabled(true);
                editText.requestFocus();
                showSoftInput(editText);
            }
        });
    }
    private void hideSoftInputFromWindow(EditText hide){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(hide.getWindowToken(), 0);
    }
    private void showSoftInput(EditText show){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(show,InputMethodManager.SHOW_FORCED);
    }
    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        //getSupportActionBar().setTitle("Hello");
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        more_vert = findViewById(R.id.more_vert);
        arrow_back = findViewById(R.id.arrow_back);
        title_toolbar = findViewById(R.id.title_toolbar);
        close_toolbar_edit_the_name_of_job = findViewById(R.id.close_toolbar_edit_the_name_of_job);
        close_toolbar_labels = findViewById(R.id.close_toolbar_labels);
        close_comments = findViewById(R.id.close_comments);
        check_toolbar = findViewById(R.id.check_toolbar);
        check_comments = findViewById(R.id.check_comments);
        the_name_of_job = findViewById(R.id.the_name_of_job);
        list_name = findViewById(R.id.list_name);
        edit_the_name_of_job = findViewById(R.id.edit_the_name_of_job);
        labels = findViewById(R.id.labels);
        members = findViewById(R.id.members);
        end_date = findViewById(R.id.end_date);
        list_job = findViewById(R.id.list_job);
        attachments = findViewById(R.id.attachments);
        move_focus = findViewById(R.id.move_focus);
        comments = findViewById(R.id.comments);

        linearLayout_labels = findViewById(R.id.linearLayout_labels);

        checkbox_mau_cam = findViewById(R.id.checkbox_mau_cam);
        checkbox_mau_vang = findViewById(R.id.checkbox_mau_vang);
        checkbox_mau_tim = findViewById(R.id.checkbox_mau_tim);
        checkbox_mau_xam = findViewById(R.id.checkbox_mau_xam);
        checkbox_mau_than = findViewById(R.id.checkbox_mau_than);
        mau_vang = findViewById(R.id.mau_vang);
        mau_cam = findViewById(R.id.mau_cam);
        mau_than = findViewById(R.id.mau_than);
        mau_xam = findViewById(R.id.mau_xam);
        mau_tim = findViewById(R.id.mau_tim);
        but_mau_vang = findViewById(R.id.but_mau_vang);
        but_mau_cam = findViewById(R.id.but_mau_cam);
        but_mau_tim = findViewById(R.id.but_mau_tim);
        but_mau_than = findViewById(R.id.but_mau_than);
        but_mau_xam = findViewById(R.id.but_mau_xam);


        //listView_members = findViewById(R.id.listview_members);

        edit_list_job = findViewById(R.id.edit_list_job);
        close_list_job = findViewById(R.id.close_list_job);
        check_list_job = findViewById(R.id.check_list_job);
        more_vert_list_job = findViewById(R.id.more_vert_list_job);
        linearLayout_list_job = findViewById(R.id.linearLayout_list_job);
        ten_cong_viec_list_job = findViewById(R.id.ten_cong_viec_list_job);

        listView_list_job = findViewById(R.id.listview_list_job);

        edit_list_attachments = findViewById(R.id.edit_list_attachments);
        close_list_attachments = findViewById(R.id.close_list_attachments);
        check_list_attachments = findViewById(R.id.check_list_attachments);
        more_vert_list_attachments = findViewById(R.id.more_vert_list_attachments);
        linearLayout_list_attachments = findViewById(R.id.linearLayout_list_attachments);
        tep_dinh_kem_list_attachments = findViewById(R.id.tep_dinh_kem_list_attachments);
        listView_attachments = findViewById(R.id.listview_list_attachments);

        linearLayout_1 = findViewById(R.id.linearLayout_1);
        linearLayout_2= findViewById(R.id.linearLayout_2);
        linearLayout_3 = findViewById(R.id.linearLayout_3);
        linearLayout_4 = findViewById(R.id.linearLayout_4);
        linearLayout_5 = findViewById(R.id.linearLayout_5);

        linearLayout_parent = findViewById(R.id.linearLayout_parent);
        linearLayout_body = findViewById(R.id.linearLayout_body);
        mRecyclerView_comments = findViewById(R.id.recyclerView_comments);

        gridView_labels = findViewById(R.id.gridview_labels);

        view_body = findViewById(R.id.view_body);
        view_comments = findViewById(R.id.view_comments);
        mau_vang1 = findViewById(R.id.mau_vang1);
    }
}