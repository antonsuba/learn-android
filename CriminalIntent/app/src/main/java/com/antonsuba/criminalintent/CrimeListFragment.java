package com.antonsuba.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author Anton Suba
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mCrimeAdapter = new CrimeAdapter(crimes);
        mRecyclerView.setAdapter(mCrimeAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;

        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
            super(inflater.inflate(layoutId, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private int NORMAL_VIEW_TYPE = 0;
        private int SERIOUS_VIEW_TYPE = 1;

        private List<Crime> mCrimeList;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimeList = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            if (viewType == NORMAL_VIEW_TYPE) {
                return new CrimeHolder(layoutInflater, parent, R.layout.list_item_crime);
            } else if (viewType == SERIOUS_VIEW_TYPE) {
                return new CrimeHolder(layoutInflater, parent, R.layout.list_item_serious_crime);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimeList.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mCrimeList.get(position).isRequiresPolice() ? 1 : 0;
        }
    }
}
