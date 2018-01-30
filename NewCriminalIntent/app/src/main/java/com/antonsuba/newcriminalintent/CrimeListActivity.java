package com.antonsuba.newcriminalintent;

import android.support.v4.app.Fragment;

/**
 * @author Anton Suba
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
