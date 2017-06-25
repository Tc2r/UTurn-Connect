package com.tc2r.uturnconnect.Remote;

import com.tc2r.uturnconnect.Model.Calendar;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Tc2r on 4/26/2017.
 * <p>
 * Description:
 * // this is a raw url to access json data of a calendar Events in google
 * // https://www.googleapis.com/calendar/v3/calendars/pe1ms2n1ai0mf73aoukqq0d550@group.calendar.google.com/events?&maxResults=25&timeMax=2017-05-30T00:00:00Z&timeMin=2017-04-27T00:00:00Z&key=%20AIzaSyB2XyHSm_CFWEnJyaTy1qaewJTfDK9s1JY
 */

public interface GoogleCalendarAPI {


	static final String BASE_URL = "https://www.googleapis.com/";
	static final String CALENDAR_ID = "join09ft4lddq233igtqrmg638@group.calendar.google.com";
	static final String APP_KEY = "20AIzaSyB2XyHSm_CFWEnJyaTy1qaewJTfDK9s1JY";

	@GET("calendar/v3/calendars/{calenderid}" + "/events?&singleEvents=true&orderBy=startTime&maxResults=50&key=%" + APP_KEY)
	Call<Calendar> getEventDetails(@Path("calenderid") String CALENDAR_ID, @Query("timeMin") String timeMin, @Query("timeMax") String timeMax);

	class Factory{
		// Creates a singleton
		private static GoogleCalendarAPI service;
		public static GoogleCalendarAPI getInstance(){
			if(service == null) {
				// Retrofit Reference
				Retrofit retrofit = new Retrofit.Builder()
								.addConverterFactory(GsonConverterFactory.create())
								.baseUrl(BASE_URL)
								.build();

				// End Points
				service = retrofit.create(GoogleCalendarAPI.class);
				return service;
			}else{
				return service;
			}
		}

	}

}
