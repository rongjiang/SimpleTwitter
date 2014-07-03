package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name;
	private long uid;
	private String screenName;
	private String tagLine;
	private long followersCount;
	private long friendsCount;
	private String profileImageUrl;

	public static User fromJSON(JSONObject json) {
		User user = new User();
		try {
			user.name = json.getString("name");
			user.uid = json.getLong("id");
			user.screenName = json.getString("screen_name");
			user.profileImageUrl = json.getString("profile_image_url");
			user.tagLine = json.getString("description");
			user.followersCount = json.getLong("followers_count");
			user.friendsCount = json.getLong("friends_count");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}		
		return user;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public String getTagLine() {
		return tagLine;
	}

	public long getFollowersCount() {
		return followersCount;
	}

	public long getFriendsCount() {
		return friendsCount;
	}

	

}
