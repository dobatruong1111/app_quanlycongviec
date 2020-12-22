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
public class homeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View myParent;

    // Declare tab_layout and viewpaperadapter
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton fab_home;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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
        myParent = inflater.inflate(R.layout.fragment_home, container, false);

        //mapping
        tabLayout = (TabLayout) myParent.findViewById(R.id.tab_layout_home);
        viewPager = (ViewPager) myParent.findViewById(R.id.view_paper_home);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPagerAdapter.addFragment(new pinProjectFragment(), "Pin");
        viewPagerAdapter.addFragment(new favoriteProjectFragment(), "Favorites");
        viewPagerAdapter.addFragment(new recentProjectFragment(), "Recents");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Handle event click FloatingActionButton
        fab_home = myParent.findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment();
            }
        });

        return myParent;
    }

    private void showDialogFragment() {

        dialogAddProject dialogAddProject = new dialogAddProject();

        dialogAddProject.show(getFragmentManager(), "Dialog Add Project");
    }
}