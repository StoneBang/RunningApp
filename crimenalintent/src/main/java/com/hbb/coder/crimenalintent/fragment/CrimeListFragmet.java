package com.hbb.coder.crimenalintent.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb.coder.crimenalintent.R;
import com.hbb.coder.crimenalintent.activity.CrimeActivity;
import com.hbb.coder.crimenalintent.activity.CrimePageActivity;
import com.hbb.coder.crimenalintent.adapter.CrimeListAdapter;
import com.hbb.coder.crimenalintent.model.Crime;
import com.hbb.coder.crimenalintent.model.CrimeLabel;
import com.hbb.coder.crimenalintent.util.StatusUtils;

import java.util.List;

import static com.hbb.coder.crimenalintent.activity.CrimeActivity.CRIME_UUID;


/**
 * Created by hongbang on 2018/3/18.
 * 陋习列表的fragment
 */

public class CrimeListFragmet extends Fragment  implements  CrimeListAdapter.OnItemClickListener{


    private Crime mCrime;
    private RecyclerView mRecycleView;
    private CrimeListAdapter mCrimeListAdapter;
    private boolean mSubtitleVisible;
    private int mSize;
    public  static  final String CRIMECOUNT="crime_count";
    private Toolbar mToolBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        StatusUtils.setStatusBarLightMode(getActivity(), Color.WHITE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mToolBar = inflate.findViewById(R.id.toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();



        activity.setSupportActionBar(mToolBar);

        mRecycleView = inflate.findViewById(R.id.fragment_crime_list_rc);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUi();

        return inflate;
    }

    /**
     * 填充recycleview适配器
     */
    private void updateUi() {
        CrimeLabel crimeLabel = CrimeLabel.getCrimeLabel(getActivity());

        List<Crime> crimeList = crimeLabel.getCrimeList();

        if(mCrimeListAdapter==null){
            mCrimeListAdapter = new CrimeListAdapter(getActivity(),this,crimeList);
            mRecycleView.setAdapter(mCrimeListAdapter);
            mCrimeListAdapter.setOnItemClickListener(this);
        }else{
            mCrimeListAdapter.setCrimeList(crimeList);
            mCrimeListAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CrimeListFragmet","CrimeListFragmet+onActivityResult");
        if(data!=null){
            Log.e("CrimeListFragmet","CrimeListFragmet+"+data.getIntExtra("ssss",
                    -1));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_crime_list,menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_item_add:
                Crime crime = new Crime();
                CrimeLabel.getCrimeLabel(getActivity()).addCrime(crime);
                Intent intent = new Intent(getActivity(), CrimeActivity.class);
                intent.putExtra(CRIME_UUID,crime.getUUID());
                startActivity(intent);

                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible=!mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return  true;

            default:
                return super.onOptionsItemSelected(item);


        }


    }



    private void updateSubtitle(){
        CrimeLabel crimeLabel=CrimeLabel.getCrimeLabel(getActivity());
        mSize = crimeLabel.getCrimeList().size();
        @SuppressLint("StringFormatMatches")
        String string = getString(R.string.subtitle_format, mSize);
        if(!mSubtitleVisible){
            string=null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(string);
    }

    @Override
    public void itemClick(Crime crime) {
        Intent intent=new Intent(getActivity(), CrimePageActivity.class);
        intent.putExtra(CRIME_UUID,crime.getUUID());
        intent.putExtra(CRIMECOUNT,mSize);
        getActivity().startActivityForResult(intent,100);
    }
}
