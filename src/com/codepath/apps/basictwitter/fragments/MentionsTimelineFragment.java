package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		Log.d("debug", "onCreate(): got Twitter client");
		
		populateTimeline();
	}

	@Override
	public void populateTimeline() {
		Log.d("debug", "MentionsTimelineFragment.populateTimeline()...");
		client.getMentionsTimeline(new JsonHttpResponseHandler() {

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
