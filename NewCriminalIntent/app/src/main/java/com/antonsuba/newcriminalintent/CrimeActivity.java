package com.antonsuba.newcriminalintent;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
