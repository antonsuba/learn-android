package com.antonsuba.criminalintent;

import android.support.v4.app.Fragment;

/**
 * @author Anton Suba
 */

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
