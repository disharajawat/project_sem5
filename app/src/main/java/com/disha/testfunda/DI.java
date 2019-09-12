package com.disha.testfunda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DI extends Fragment {

    Button set1,set2,set3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.dilr,container,false);
        Toast.makeText(getActivity().getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
        set1=v.findViewById(R.id.set1);
        set1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),d_set1.class));
            }
        });

        return v;
    }
}