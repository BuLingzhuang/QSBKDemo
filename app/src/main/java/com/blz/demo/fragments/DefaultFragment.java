package com.blz.demo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blz.demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultFragment extends Fragment {
    public static final String TEXT = "text";

    public DefaultFragment() {
        // Required empty public constructor
    }

    public static DefaultFragment newInstance(String text){
        Bundle args = new Bundle();
        DefaultFragment fragment = new DefaultFragment();
        args.putString(TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_default, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.default_fragment_textView);
        String textStr = getArguments().getString(TEXT);
        textView.setText(textStr);
    }
}
