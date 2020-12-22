package com.example.myapplication1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication1.Adapter.ViewPagerAdapter;
import com.example.myapplication1.Dialog.dialogAddProject;
import com.example.myapplication1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;




/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class projectFragment extends Fragment {

    private View viewParent;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab_project;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public projectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment projectPragment.
     */
    // TODO: Rename and change types and number of parameters
    public static projectFragment newInstance(String param1, String param2) {
        projectFragment fragment = new projectFragment();
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
        viewParent = inflater.inflate(R.layout.fragment_project, container, false);

        tabLayout = (TabLayout) viewParent.findViewById(R.id.tab_layout_project);
        viewPager = (ViewPager) viewParent.findViewById(R.id.view_paper_project);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPagerAdapter.addFragment(new personalFragment(), "Personal");
        viewPagerAdapter.addFragment(new groupFragment(), "Group");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        fab_project = viewParent.findViewById(R.id.fab_project);
        fab_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment();
            }
        });

        return viewParent;
    }

    private void showDialogFragment() {

        dialogAddProject dialogAddProject = new dialogAddProject();

        dialogAddProject.show(getFragmentManager(), "Dialog Add Project");
    }
}