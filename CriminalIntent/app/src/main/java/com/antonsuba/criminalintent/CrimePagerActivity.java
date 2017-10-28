package com.antonsuba.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

/**
 * @author Anton Suba
 */

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.antonsuba.criminalintent.crime_id";
    private static final String EXTRA_POSITION_ID = "com.antonsuba.criminalintent.position_id";

    private ViewPager mViewPager;
    private Button mJumpToFirstButton;
    private Button mJumpToLastButton;
    private List<Crime> mCrimeList;

    public static Intent newIntent(Context packageContext, UUID crimeId, int position) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        intent.putExtra(EXTRA_POSITION_ID, position);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        int position = getIntent().getIntExtra(EXTRA_POSITION_ID, -1);

        mViewPager = findViewById(R.id.crime_view_pager);
        mCrimeList = CrimeLab.get(this).getCrimes();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimeList.get(position);
                return CrimeFragment.newInstance(crime.getId(), position);
            }

            @Override
            public int getCount() {
                return mCrimeList.size();
            }
        });

        mJumpToFirstButton = findViewById(R.id.jump_to_first);
        mJumpToFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        mJumpToLastButton = findViewById(R.id.jump_to_last);
        mJumpToLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mCrimeList.size());
            }
        });

        mViewPager.setCurrentItem(position);
    }
}
