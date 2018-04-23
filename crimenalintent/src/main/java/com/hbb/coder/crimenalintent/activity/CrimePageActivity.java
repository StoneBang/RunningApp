package com.hbb.coder.crimenalintent.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hbb.coder.crimenalintent.R;
import com.hbb.coder.crimenalintent.fragment.CrimeFragmet;
import com.hbb.coder.crimenalintent.model.Crime;
import com.hbb.coder.crimenalintent.model.CrimeLabel;

import java.io.Serializable;
import java.util.List;

import static com.hbb.coder.crimenalintent.activity.CrimeActivity.CRIME_UUID;
import static com.hbb.coder.crimenalintent.fragment.CrimeListFragmet.CRIMECOUNT;

/**
 * 陋习滑动浏览页面
 */
public class CrimePageActivity extends FragmentActivity {

    private ViewPager mCrimePage;
    private List<Crime> mCrimeList;
    private FragmentManager mSupportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_page);
        mCrimePage = findViewById(R.id.crime_page_view_pager);

        mCrimeList = CrimeLabel.getCrimeLabel(CrimePageActivity.this).getCrimeList();

        mSupportFragmentManager = getSupportFragmentManager();

        mCrimePage.setAdapter(new FragmentPagerAdapter(mSupportFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return CrimeFragmet.newInstantce(mCrimeList.get(position).getUUID());
            }

            @Override
            public int getCount() {
                return mCrimeList.size();
            }
        });

        for (int i=0;i<mCrimeList.size();i++){

            Crime crime = mCrimeList.get(i);

            if(crime.getUUID().equals(getIntent().getSerializableExtra(CRIME_UUID))){

                mCrimePage.setCurrentItem(i);

            }

        }
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        Intent intent = getIntent();
        int  size = (int) intent.getIntExtra(CRIMECOUNT,-1);
        Intent newIntent = new Intent();
        newIntent.putExtra(CRIMECOUNT,size);
        return super.getParentActivityIntent();
    }
}
