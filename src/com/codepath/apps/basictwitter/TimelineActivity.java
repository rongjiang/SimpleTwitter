package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private static final int REQUEST_CODE = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		Log.d("debug", "onCreate(): got Twitter client");
		populateTimeline();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	            // Triggered only when new data needs to be appended to the list
	            // Add whatever code is needed to append new items to your AdapterView
	            loadMoreDataFromApi(totalItemsCount); 
		    }
        });
	}

    // Append more data into the adapter
    public void loadMoreDataFromApi(int offset) {
    	// clearView = false;
    	// loadSearchResults(offset);
    	populateTimeline();
    }

	public void populateTimeline() {
		Log.d("debug", "populateTimeline()...");
		client.getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, JSONArray json) {
				Log.d("debug", "displaying tweets...");
				aTweets.addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s);
			}
			
		});
	}	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }
	
	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra("mode", 2); // pass arbitrary data to launched activity
		startActivityForResult(i, REQUEST_CODE);	 	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String tweetBody = data.getExtras().getString("tweetBody");
	     aTweets.clear();
	     populateTimeline();
	     // Toast the name to display temporarily on screen
	     Toast.makeText(this, tweetBody, Toast.LENGTH_LONG).show();
	  }
	} 
}
