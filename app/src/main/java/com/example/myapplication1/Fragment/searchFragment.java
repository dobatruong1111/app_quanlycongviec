package com.example.myapplication1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication1.Adapter.ViewPagerAdapter;
import com.example.myapplication1.Model.Project;
import com.example.myapplication1.Model.User;
import com.example.myapplication1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;




/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class searchFragment extends Fragment {

    public static ArrayList<Project> projects = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();


    private Boolean check = true;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton imgbtn_search1, imgbtn_search2;
    private EditText edt_search;
    private TextView tv_search, tv_result_for, tv_result;
    private ProgressBar prg;

    public searchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchFragment newInstance(String param1, String param2) {
        searchFragment fragment = new searchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        tv_search = view.findViewById(R.id.tv_search);
        tv_result_for = view.findViewById(R.id.tv_result_for);
        tv_result = view.findViewById(R.id.tv_result);
        edt_search = view.findViewById(R.id.edt_search);
        prg = view.findViewById(R.id.loading_finding);
        imgbtn_search1 = view.findViewById(R.id.imgbtn_search1);
        imgbtn_search2 = view.findViewById(R.id.imgbtn_search2);

        imgbtn_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtn_search1.setVisibility(View.INVISIBLE);
                imgbtn_search2.setVisibility(View.VISIBLE);
                tv_search.setVisibility(View.INVISIBLE);
                edt_search.setVisibility(View.VISIBLE);
            }
        });

        imgbtn_search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_search.getText().equals("")) {
                    edt_search.setError("Please enter your ID User or Name Project!", null);
                    edt_search.requestFocus();
                    return;
                }

                prg.setVisibility(View.VISIBLE);
                imgbtn_search1.setVisibility(View.VISIBLE);
                imgbtn_search2.setVisibility(View.INVISIBLE);
                edt_search.setVisibility(View.INVISIBLE);
                tv_search.setVisibility(View.VISIBLE);

                tv_result_for.setVisibility(View.VISIBLE);
                tv_result.setText(edt_search.getEditableText().toString());
                tv_result.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}