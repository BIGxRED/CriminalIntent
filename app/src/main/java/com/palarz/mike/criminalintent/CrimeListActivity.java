package com.palarz.mike.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by QNP684 on 9/3/2016.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
