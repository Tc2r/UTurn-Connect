package com.tc2r.uturnconnect.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tc2r.uturnconnect.AboutActivity;
import com.tc2r.uturnconnect.ContactActivity;
import com.tc2r.uturnconnect.R;

/**
 * Created by Tc2r on 4/25/2017.
 * <p>
 * Description:
 */

public class HomeFragment extends Fragment implements
				View.OnClickListener{

	Button btnAboutUs, btnGive, btnFaceBook, btnContactUs, btnWebsite, btnTvLive;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main, container, false);

		btnAboutUs = (Button) view.findViewById(R.id.btn_aboutus);
		btnContactUs = (Button) view.findViewById(R.id.main_button_contact);
		btnFaceBook = (Button) view.findViewById(R.id.main_button_facebook);
		btnGive = (Button) view.findViewById(R.id.main_button_offering);
		btnWebsite = (Button) view.findViewById(R.id.main_button_website);
		btnTvLive = (Button) view.findViewById(R.id.main_button_live);

		btnAboutUs.setOnClickListener(this);
		btnGive.setOnClickListener(this);
		btnFaceBook.setOnClickListener(this);
		btnContactUs.setOnClickListener(this);
		btnWebsite.setOnClickListener(this);
		btnTvLive.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		Intent linkActivity;
		switch (id) {
			case R.id.btn_aboutus:
				linkActivity = new Intent(this.getContext(), AboutActivity.class);
				startActivity(linkActivity);
				break;
			case R.id.main_button_offering:
				linkActivity = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_offering)));
				startActivity(linkActivity);
				break;
			case R.id.main_button_website:
				linkActivity = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_uturn_website)));
				startActivity(linkActivity);
				break;
			case R.id.main_button_contact:
				linkActivity = new Intent(this.getContext(), ContactActivity.class);
				startActivity(linkActivity);
				break;
			case R.id.main_button_facebook:
				linkActivity = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_facebook_uturn)));
				startActivity(linkActivity);
				//TODO add facebook api
				break;

		}
	}
}
