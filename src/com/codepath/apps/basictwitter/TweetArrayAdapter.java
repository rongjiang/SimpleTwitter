package com.codepath.apps.basictwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.TweetListFragment;
import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		Tweet tweet = getItem(position);
		
		View v;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}

		// find items in the template
		ImageView ivImageProfile = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserScreenName = (TextView) v.findViewById(R.id.tvUserScreenName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvCreatedAt = (TextView) v.findViewById(R.id.tvCreatedAt);
		ivImageProfile.setImageResource(android.R.color.transparent);
		// load image from url
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivImageProfile);
		tvUserScreenName.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvCreatedAt.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		
		ivImageProfile.setTag(tweet.getUser().getScreenName());
		ivImageProfile.setOnClickListener(new OnClickListener() {
			
			@Override
 			public void onClick(View v) {
				Log.d("debug", "onClick(): parent = " + parent.toString());
				Intent i = new Intent(getContext(), ProfileActivity.class);
				i.putExtra("userScreenName", v.getTag().toString());
				Log.d("debug", "onClick(): userName = " + v.getTag().toString());
				getContext().startActivity(i);				
			}
		});
		return v;
	}
	
	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(
					dateMillis,
					System.currentTimeMillis(), 
					DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
}
