package com.antonsuba.criminalintent;

import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * @author Anton Suba
 */

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.antonsuba.criminalintent.crime_id";
    private static final String EXTRA_CRIME_POSITION = "com.antonsuba.criminalintent.crime_position";

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        int position = getIntent().getIntExtra(EXTRA_CRIME_POSITION, -1);
        return CrimeFragment.newInstance(crimeId, position);
    }
}
