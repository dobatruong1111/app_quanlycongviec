package com.example.myapplication1.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication1.Model.Group;
import com.example.myapplication1.Model.Personal;
import com.example.myapplication1.Model.User;
import com.example.myapplication1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class dialogAddProject extends DialogFragment{

    public static final String TAG = "Dialog Add Project";

    private ImageButton imgbtn;
    private TextInputLayout tip_project_name;
    private AutoCompleteTextView auto_text_fill;
    private MaterialButton btn_create;
    private User userOwn;

    private FirebaseDatabase mData;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_add_project, container, false);

        tip_project_name = (TextInputLayout) view.findViewById(R.id.tip_project_name);
        auto_text_fill = (AutoCompleteTextView) view.findViewById(R.id.drop_menu_distribute);
        imgbtn = (ImageButton) view.findViewById(R.id.imgbtn_x_creat_porject);
        btn_create = (MaterialButton) view.findViewById(R.id.btn_create_project);

        mData = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        String[] distribute = new String[]{"Personal", "Group"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.drop_distribute_items, distribute);

        auto_text_fill.setAdapter(arrayAdapter);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateNameProject() || !validateDistributeProject()) {
                    return;
                }

                String nameProject = tip_project_name.getEditText().getText().toString();
                String type = auto_text_fill.getEditableText().toString();
                String IDuserOwn = mAuth.getCurrentUser().getUid();

                if(type.equals("Personal")){
                    String idKey = mData.getReference("Project").push().getKey();
                    Personal project = new Personal(idKey, nameProject,IDuserOwn , new ArrayList<String>());
                    mData.getReference("Personal").child(idKey).setValue(project);

                }
                else{
                    String idKey = mData.getReference("Project").push().getKey();
                    Group project = new Group(idKey, nameProject, IDuserOwn, new ArrayList<String>(), new ArrayList<String>());
                    mData.getReference("Group").child(idKey).setValue(project);

                }

                getDialog().dismiss();

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    public Boolean validateNameProject() {
        String name_project = tip_project_name.getEditText().getText().toString();

        if (name_project.isEmpty()) {

            tip_project_name.setError("Field cannot be empty!");
            tip_project_name.requestFocus();

            return false;
        } else {

            tip_project_name.setError(null);
            tip_project_name.setErrorEnabled(false);

            return true;
        }
    }

    public Boolean validateDistributeProject() {
        String distribute = auto_text_fill.getEditableText().toString();

        if (distribute.isEmpty()) {
            auto_text_fill.setError("Field cannot be empty!", null);
            auto_text_fill.requestFocus();

            return false;
        } else {
            auto_text_fill.setError(null);

            return true;
        }
    }
}
