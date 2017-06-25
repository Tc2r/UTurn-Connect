package com.tc2r.uturnconnect.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tc2r.uturnconnect.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


	public ContactFragment() {
		// Required empty public constructor
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// Create/get fragment's view
		View view = inflater.inflate(R.layout.fragment_gallery, container, false);


		return view;
	}

}
