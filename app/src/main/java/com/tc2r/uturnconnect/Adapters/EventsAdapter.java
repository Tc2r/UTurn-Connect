package com.tc2r.uturnconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tc2r.uturnconnect.Model.Event;
import com.tc2r.uturnconnect.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Tc2r on 4/27/2017.
 * <p>
 * Description:
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
	private static final String TAG = "EventsDataAdapter";
	private Context context;
	private ArrayList<Event> itemsList;
	private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
	public EventsAdapter(Context context, ArrayList<Event> itemsList) {
		this.context = context;
		this.itemsList = itemsList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row_layout, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final Event currentObject = itemsList.get(position);



		holder.title.setText(currentObject.getSummary());
		holder.desc.setText(currentObject.getDescription());


		String currentTime = currentObject.getStart().getDateTime();

		if (currentTime != null && currentTime != "") {
			try {
				Date formattedDate = dFormat.parse(currentTime);
				String dayTime = dateFormat.format(formattedDate);
				String hourTime = timeFormat.format(formattedDate);
				holder.day.setText(dayTime);
				holder.time.setText(hourTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}


		}
	}

	@Override
	public int getItemCount() {
		return itemsList.size();
	}


	class ViewHolder extends RecyclerView.ViewHolder {

		TextView title, day, time, desc;
		ImageView logo;

		public ViewHolder(View view) {
			super(view);

			// Get the reference of each object in inflated view:

			this.title = (TextView) view.findViewById(R.id.event_title_tv);
			this.day = (TextView) view.findViewById(R.id.event_date_tv);
			this.time = (TextView) view.findViewById(R.id.event_time_tv);
			this.desc = (TextView) view.findViewById(R.id.event_desc_tv);

		}
	}
}
