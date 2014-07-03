package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	private String screenName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		screenName = (String)getIntent().getStringExtra("userScreenName");
		loadProfileInfo();
	}
	
	public String getScreenName() {
		return screenName;
	}

	private void loadProfileInfo() {
		if (screenName != null) {
			TwitterApplication.getRestClient().getOthersInfo(screenName, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					User user = User.fromJSON(json);
					getActionBar().setTitle("@" + user.getScreenName());
					populateProfileHeader(user);
				}
			});		
			
		} else {
		    TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			    @Override
			    public void onSuccess(JSONObject json) {
				    User user = User.fromJSON(json);
				    getActionBar().setTitle("@" + user.getScreenName());
				    populateProfileHeader(user);
			    }
		    });
		}
	}
	
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollower);
		TextView tvFollowings = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(user.getScreenName());
		tvTagLine.setText(user.getTagLine());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowings.setText(user.getFriendsCount() + " Followings");
		// load profile image
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);

	}

}
