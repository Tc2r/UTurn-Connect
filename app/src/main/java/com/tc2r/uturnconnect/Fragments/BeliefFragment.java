package com.tc2r.uturnconnect.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tc2r.uturnconnect.R;

/**
 * Created by Tc2r on 4/25/2017.
 * <p>
 * Description:
 */

public class BeliefFragment extends Fragment implements
				View.OnClickListener{

	Button btnMission, btnVision, btnCode;
	CardView cvDetails;
	TextView tvDetails;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_belief, container, false);
		btnVision = (Button) v.findViewById(R.id.btn_vision);
		btnMission = (Button) v.findViewById(R.id.btn_mission);

		btnCode = (Button) v.findViewById(R.id.btn_code);
		cvDetails = (CardView) v.findViewById(R.id.cv_hidden_details);
		tvDetails = (TextView) v.findViewById(R.id.tv_belief_details);

		btnVision.setOnClickListener(this);
		btnMission.setOnClickListener(this);
		btnCode.setOnClickListener(this);
		tvDetails.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.btn_vision:
				cvDetails.setVisibility(View.VISIBLE);
				tvDetails.setVisibility(View.VISIBLE);
				tvDetails.setText(R.string.beliefs_vision_statement);
				tvDetails.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
				break;
			case R.id.btn_mission:
				cvDetails.setVisibility(View.VISIBLE);
				tvDetails.setVisibility(View.VISIBLE);
				tvDetails.setText(R.string.beliefs_mission_statement);
				tvDetails.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
				break;
			case R.id.btn_code:
				cvDetails.setVisibility(View.VISIBLE);
				tvDetails.setVisibility(View.VISIBLE);
				tvDetails.setText(R.string.beliefs_code_statement);
				tvDetails.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
				break;
			default:
				break;
		}
	}
}
