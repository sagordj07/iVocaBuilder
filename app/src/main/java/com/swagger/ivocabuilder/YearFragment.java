package com.swagger.ivocabuilder;


import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class YearFragment extends Fragment  implements WordListAdapter.OnDeleteClickListener{

    private WordsViewModel mWordViewModel;
    WordListAdapter yearAdapter;

    public YearFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_year, container, false);
        // Inflate the layout for this fragment
        RecyclerView recyclerView = view.findViewById(R.id.year_recycler);
        yearAdapter = new WordListAdapter(getActivity(),this);
        recyclerView.setAdapter(yearAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        // Get a new or existing ViewModel from the ViewModelProvider.
        mWordViewModel = ViewModelProviders.of(this).get(WordsViewModel.class);
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getAllNotes().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                yearAdapter.setWords(data);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here

        MenuItem searchItem = menu.findItem(R.id.all_users);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               yearAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               yearAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void OnDeleteClickListener(Data data) {
        mWordViewModel = ViewModelProviders.of(this).get(WordsViewModel.class);
        mWordViewModel.delete(data);
    }
}
