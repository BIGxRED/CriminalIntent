package com.palarz.mike.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by QNP684 on 9/3/2016.
 */
public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {

    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResID(){
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime){
        if(findViewById(R.id.detail_fragment_container) == null){
            Intent intent = CrimePagerActivity.newIntent(this, crime.getID(),
                    CrimeListFragment.mSubtitleVisible);
            startActivity(intent);
        }
        else{
            Fragment newDetail = CrimeFragment.newInstance(crime.getID());
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }

    public void onCrimeUpdated(Crime crime){
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
