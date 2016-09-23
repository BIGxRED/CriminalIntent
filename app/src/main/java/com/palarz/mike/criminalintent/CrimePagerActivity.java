package com.palarz.mike.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by QNP684 on 9/10/2016.
 */
public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "com.palarz.mike.criminalintent.crime_id";


    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private boolean mSubtitleVisible;

    public static Intent newIntent(Context packageContext, UUID crimeID, boolean showSubtitle){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        intent.putExtra(CrimeListFragment.EXTRA_SUBTITLE_VISIBLE, showSubtitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mSubtitleVisible = getIntent().
                getBooleanExtra(CrimeListFragment.EXTRA_SUBTITLE_VISIBLE,false);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getID());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for(int i = 0; i < mCrimes.size(); i++){
            if (mCrimes.get(i).getID().equals(crimeID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public Intent getParentActivityIntent (){
        super.getParentActivityIntent();
        Intent intent = new Intent(this, CrimeListActivity.class);
        intent.putExtra(CrimeListFragment.EXTRA_SUBTITLE_VISIBLE, mSubtitleVisible);
        return intent;
    }
}
