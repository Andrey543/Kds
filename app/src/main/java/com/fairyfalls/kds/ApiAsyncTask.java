package com.fairyfalls.kds;

import android.os.AsyncTask;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ApiAsyncTask extends AsyncTask<Void, Void, Void> {
    private MainActivity mActivity;

    ApiAsyncTask(MainActivity activity){
        this.mActivity = activity;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            mActivity.clearResultsText();
            mActivity.updateResultsText(getDataFromApi());

        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            mActivity.showGooglePlayServicesAvailabilityErrorDialog(
                    availabilityException.getConnectionStatusCode());

        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mActivity.startActivityForResult(
                    userRecoverableException.getIntent(),
                    MainActivity.REQUEST_AUTHORIZATION);

        } catch (IOException e) {
            mActivity.updateStatus("Error: " +
                    e.getMessage());
        }
        return null;
    }
    private ArrayList<ArrayList<String>> getDataFromApi() throws IOException {
        DateTime now = new DateTime(System.currentTimeMillis());
        ArrayList<ArrayList<String>> eventsStrings = new ArrayList<ArrayList<String>>();
        Events events = mActivity.mService.events().list("79dd094ipmpco64t25vmikg4f4@group.calendar.google.com")
                .setMaxResults(50)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        for (Event event : items) {
            DateTime start = event.getStart().getDateTime();
            if (start == null) {
                start = event.getStart().getDate();
            }
            DateTime end = event.getEnd().getDate();
            if (end == null) {
                end = event.getEnd().getDateTime();
            }
            ArrayList<String> data = new ArrayList<String>();
            data.add(event.getSummary());
            data.add(start.toString());
            data.add(end.toString());
            eventsStrings.add(data);
            //eventsStrings.add(new ArrayList<String>(){event.getSummary(), start, end});
//                    String.format("%s", event.getSummary())
            //);
        }
        return eventsStrings;
    }
}
