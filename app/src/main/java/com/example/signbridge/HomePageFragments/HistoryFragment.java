package com.example.signbridge.HomePageFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.signbridge.DBHelper;
import com.example.signbridge.HistoryAdapter;
import com.example.signbridge.HistoryModel;
import com.example.signbridge.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

     RecyclerView recyclerView;
     HistoryAdapter adapter;
     List<HistoryModel> dataList = new ArrayList<>();
     DBHelper dbHelper;


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new HistoryAdapter(requireContext(),dataList );
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((position, view1) -> {
            HistoryModel data = dataList.get(position);
            dbHelper.deleteData(data.getId());
            dataList.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show();
        });

        fetchDataFromSQLite();

        return rootView;
    }

    private void fetchDataFromSQLite() {
        dataList.clear();
        dataList.addAll(dbHelper.getAllData());
        adapter.notifyDataSetChanged();
    }
}