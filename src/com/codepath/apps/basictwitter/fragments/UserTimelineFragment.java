package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.basictwitter.ProfileActivity;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		Log.d("debug", "onCreate(): got Twitter client");
		
		//populateTimeline();
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {		
		super.onActivityCreated(savedInstanceState);
		populateTimeline();
	}

	@Override
	public void populateTimeline() {
		String screenName = null;
		Activity activity = getActivity();
		if (activity instanceof ProfileActivity) {
			screenName = ((ProfileActivity) activity).getScreenName();
		}
		Log.d("debug", "UserTimelineFragment.populateTimeline()..." + screenName + "activity: " + activity);
		client.getUserTimeline(screenName, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, JSONArray json) {
				Log.d("debug", "displaying tweets...");
				addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s);
			}
			
		});
	}	
	

}
