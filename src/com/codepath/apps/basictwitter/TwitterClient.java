package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = org.scribe.builder.api.TwitterApi.class; 
    public static final String REST_URL = "https://api.twitter.com/1.1"; 
    public static final String REST_CONSUMER_KEY = "BM0jeSr8V5AuLCa6OWYsfpega";       
    public static final String REST_CONSUMER_SECRET = "U3bD6nylMOrVSAIm2SwcE60Q0KFWCbuQhCtqXSM91wWlTSeqdI";
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("statuses/home_timeline.json");
    	RequestParams params = new RequestParams();
    	params.put("since_id",  "1");
    	client.get(apiUrl, params,  handler);
    }
    
    public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("statuses/mentions_timeline.json");
    	// RequestParams params = new RequestParams();
    	// params.put("max_id",  "1");
    	client.get(apiUrl, null,  handler);
    }

    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("statuses/user_timeline.json");
    	RequestParams params = null;
    	if (screenName != null) {
    		params = new RequestParams();
        	params.put("screen_name",  screenName);    		
    	}
    	client.get(apiUrl, params,  handler);
    }
    
    public void getUserTimeline(AsyncHttpResponseHandler handler) {
    	getUserTimeline(null, handler);
    }
    
    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams();
    	params.put("status", tweet);
    	client.post(apiUrl,  params,  handler);;
    }

    public void getMyInfo(AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("account/verify_credentials.json");
    	client.get(apiUrl, null,  handler);
    }

    public void getOthersInfo(String screenName, AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("users/show.json");
    	RequestParams params = null;
    	if (screenName != null) {
    		params = new RequestParams();
    		params.put("screen_name",  screenName);
    	}    	
    	client.get(apiUrl, params,  handler);
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    /* public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    } */
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}