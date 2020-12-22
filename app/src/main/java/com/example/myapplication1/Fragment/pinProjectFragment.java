package com.example.myapplication1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Adapter.RecyclerViewProjectAdapter;
import com.example.myapplication1.Model.Group;
import com.example.myapplication1.Model.Project;
import com.example.myapplication1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pinProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pinProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Project> pinProjects = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pinProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment allProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static pinProjectFragment newInstance(String param1, String param2) {
        pinProjectFragment fragment = new pinProjectFragment();
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
        View view = inflater.inflate(R.layout.fragment_pin_project, container, false);

        String IDuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("Pin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pinProjects.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Project pinprojects = snap.getValue(Group.class);
                    if (pinprojects.getIDUserOwn().equals(IDuser)) {
                        pinProjects.add(pinprojects);
                    }
                }

                RecyclerView myrcv = (RecyclerView) view.findViewById(R.id.recycle_view_show_pin_project);

                RecyclerViewProjectAdapter myProjectAdapter = new RecyclerViewProjectAdapter(getContext(), pinProjects);
                myrcv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                myrcv.setAdapter(myProjectAdapter);
                myrcv.setHasFixedSize(true);
                myProjectAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}