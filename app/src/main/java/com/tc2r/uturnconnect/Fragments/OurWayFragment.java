package com.tc2r.uturnconnect.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tc2r.uturnconnect.R;

/**
 * Created by Tc2r on 4/25/2017.
 * <p>
 * Description:
 */

public class OurWayFragment extends Fragment{


	public OurWayFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ourway, container, false);
		return view;
	}
}
