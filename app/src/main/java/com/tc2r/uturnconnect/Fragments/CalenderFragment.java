package com.tc2r.uturnconnect.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.tc2r.uturnconnect.Adapters.EventsAdapter;
import com.tc2r.uturnconnect.Model.Calendar;
import com.tc2r.uturnconnect.Model.Event;
import com.tc2r.uturnconnect.R;
import com.tc2r.uturnconnect.Remote.GoogleCalendarAPI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tc2r on 4/25/2017.
 * <p>
 * Description:
 */

public class CalenderFragment extends Fragment {

	private static final String TAG = "MainActivity";
	private com.tc2r.uturnconnect.Model.Calendar gCalendar;


	//private SimpleDateFormat dateDisplayFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
	private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());


	private ArrayList<Event> eventList;
	private RecyclerView mRecyclerView;
	private TextView calendarTitle;
	private ProgressBar progressBar;
	private EventsAdapter mEventsAdapter;
	private Context context;
	private LinearLayoutManager mLayoutManager;
	private String timeMin, timeMax;
	private static final String calendarID = "uturnmediatv@gmail.com";

	private List<String> selectedDayEvents;
	private ListView selectedListView;
	private ArrayAdapter<String> adapter;
	private CompactCalendarView compactCalendarView;
	private ArrayList<com.github.sundeepk.compactcalendarview.domain.Event> calEventsList;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_calender, container, false);
		context = getContext();
		eventList = new ArrayList<>();
		calEventsList = new ArrayList<>();


		// Initialize Compact Calender View

		compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
		compactCalendarView.setVisibility(View.VISIBLE);
		compactCalendarView.setUseThreeLetterAbbreviation(false);
		compactCalendarView.setFirstDayOfWeek(java.util.Calendar.SUNDAY);
		compactCalendarView.setClickable(true);
		compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
		compactCalendarView.displayOtherMonthDays(true);

		initCompactCal(view);
		// Initialize ListView
		initSelListView(view);

		// Initialize RecyclerView
		initRecyclerView(view);


		// Get the date and format it into ISO format so Google API will accept it.

		// - Get phone's current timezone
		TimeZone tz = TimeZone.getDefault();
		// Create the ISO format
		final DateFormat df = new SimpleDateFormat(getString(R.string.calendar_api_sdf), Locale.getDefault());
		// Set the timezone to the format
		df.setTimeZone(tz);

		// initialize timeMin to the current time, in ISO format
		timeMin = df.format(new Date());
		Log.i(getString(R.string.debug_time_min), timeMin);

		// Now we simplify the format so we can send it to the calendar object
		// and add 90 days to the date in order to get timeMax
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.calendar_simplified_sdf), Locale.getDefault());
		String tempDate = timeMin.substring(0, 10);

		java.util.Calendar c = java.util.Calendar.getInstance();
		try {
			c.setTime(simpleDateFormat.parse(tempDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(java.util.Calendar.DAY_OF_MONTH, 90);

		// Reformat time back to ISO format for google api
		timeMax = df.format(c.getTime());

		Call<Calendar> call = GoogleCalendarAPI
						.Factory
						.getInstance()
						.getEventDetails(calendarID, timeMin, timeMax);

		call.enqueue(new Callback<Calendar>() {
			@Override
			public void onResponse(Call<Calendar> call, Response<Calendar> response) {

				// Dismiss Progress Bar
				progressBar.setVisibility(View.GONE);
				Log.d(getString(R.string.debug_label), call.request().toString());

				if (response.isSuccessful()) {

					//Log.i("TEST", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

					// Set Calendar Object with Detailed Items!
					gCalendar = response.body();
					setDataFromResponse(gCalendar);
					compactCalendarView.showCalendarWithAnimation();
				}
			}

			@Override
			public void onFailure(Call<Calendar> call, Throwable t) {
				// Dismiss Progress Bar
				progressBar.setVisibility(View.GONE);
				Log.e(TAG, getString(R.string.debug_failure_text) + t.getMessage());
			}
		});
		setCalendarListener();
		return view;
	}

	private void initCompactCal(View view) {
		calendarTitle = (TextView) view.findViewById(R.id.tv_cal_month);
		calendarTitle.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
	}

	private void initSelListView(View view) {
		selectedDayEvents = new ArrayList<>();
		selectedListView = (ListView) view.findViewById(R.id.lv_calendar);
		adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, selectedDayEvents);
		selectedListView.setAdapter(adapter);
	}

	private void initRecyclerView(View view) {

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
		mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		progressBar.setVisibility(View.VISIBLE);

	}

	private void setCalendarListener() {
		compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
			@Override
			public void onDayClick(Date dateClicked) {
				List<com.github.sundeepk.compactcalendarview.domain.Event>
								thisDayEvents = compactCalendarView.getEvents(dateClicked);

				// Update listview to show events of selected day.

				if (thisDayEvents != null) {
					selectedDayEvents.clear();
					for (com.github.sundeepk.compactcalendarview.domain.Event todayEvent : thisDayEvents) {
						selectedDayEvents.add((String) todayEvent.getData());
					}
					adapter.notifyDataSetChanged();

				}
			}

			@Override
			public void onMonthScroll(Date firstDayOfNewMonth) {
				calendarTitle.setText(dateFormatForMonth.format(firstDayOfNewMonth));
			}
		});
	}


	private void setDataFromResponse(Calendar gCalendar) {

		for (int i = 0; i < gCalendar.getEvents().size(); i++) {
			Event item = gCalendar.getEvents().get(i);


			if (item.getStart().getDateTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.calendar_response_sdf), Locale.getDefault());

				java.util.Calendar cal;
				try {
					Date tempDate  = sdf.parse(item.getStart().getDateTime());
					cal = java.util.Calendar.getInstance();
					cal.setTime(tempDate);

					com.github.sundeepk.compactcalendarview.domain.Event tempEvent =
									new com.github.sundeepk.compactcalendarview.domain.Event(Color.CYAN, cal.getTimeInMillis(), item.getSummary());

					calEventsList.add(tempEvent);

				} catch (ParseException | NullPointerException e) {
					e.printStackTrace();
				}
			}

			eventList.add(item);

		}

		compactCalendarView.addEvents(calEventsList);
		compactCalendarView.setCurrentDate(new Date());

		mEventsAdapter = new EventsAdapter(context, eventList);
		mRecyclerView.setAdapter(mEventsAdapter);

	}


	private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
		private int spanCount;
		private int spacing;
		private boolean includeEdge;

		public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
			this.spanCount = spanCount;
			this.spacing = spacing;
			this.includeEdge = includeEdge;
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			int position = parent.getChildAdapterPosition(view);
			if (includeEdge) {

				if (position < spanCount) { // top edge
					outRect.top = spacing;
				}
				outRect.bottom = spacing; // item bottom
			} else {
				if (position >= spanCount) {
					outRect.top = spacing; // item top
				}
			}
		}
	}

	/**
	 * Converting dp to pixel
	 */
	private int dpToPx(int dp) {
		Resources r = getResources();
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
	}

	@Override
	public void onResume() {
		super.onResume();
		calendarTitle.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
	}
}

