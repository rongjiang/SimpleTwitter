package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.apps.basictwitter.ComposeActivity;
import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;

public abstract class TweetListFragment extends Fragment {
	TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		client = TwitterApplication.getRestClient();
		Log.d("debug", "onCreate(): got Twitter client");
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate the layout view
		View v = inflater.inflate(R.layout.fragment_tweet_list,  container, false);
		
		// assign our view references
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);

		setupScrollListener();
		
		return v;
	}

	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public void clear() {
		aTweets.clear();
	}
	
	public void setupScrollListener() {
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
    	// loadSearchResults(offset);
    	populateTimeline();
    }
    
    public abstract void populateTimeline();

}
