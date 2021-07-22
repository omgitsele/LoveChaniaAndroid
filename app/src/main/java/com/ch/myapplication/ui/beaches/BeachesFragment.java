package com.ch.myapplication.ui.beaches;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ch.myapplication.R;

import java.util.Objects;

public class BeachesFragment extends ListFragment implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayAdapter adapter;

    private BeachesViewModel beachesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        beachesViewModel =
                ViewModelProviders.of(this).get(BeachesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_beaches, container, false);
        listView = root.findViewById(R.id.myListView);

        final TextView textView = root.findViewById(R.id.text_beaches);
        beachesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}