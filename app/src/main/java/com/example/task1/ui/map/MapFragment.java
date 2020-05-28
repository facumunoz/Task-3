package com.example.task1.ui.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.task1.R;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        final Button btnMap = root.findViewById(R.id.btnMap);
        final EditText etAddress = root.findViewById(R.id.etAddress);
        mapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()


        {
            @Override
            public void onChanged(@Nullable String s) {

            }

        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + etAddress.getText().toString().trim()));
                startActivity(intent);
            }
        });
        return root;
    }
}
