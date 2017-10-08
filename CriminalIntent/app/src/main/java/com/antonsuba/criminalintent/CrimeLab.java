package com.antonsuba.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Anton Suba
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimeList;
    private Map<UUID, Crime> mCrimeMap;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimeList = new ArrayList<>();
        mCrimeMap = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(Math.round(Math.random()) == 0);
            mCrimeList.add(crime);
            mCrimeMap.put(crime.getId(), crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimeList;
    }

    public Crime getCrime(UUID id) {
        return mCrimeMap.get(id);
    }
}
