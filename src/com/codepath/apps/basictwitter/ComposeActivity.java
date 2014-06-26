package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class ComposeActivity extends Activity {
	
	private TwitterClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		client = TwitterApplication.getRestClient();
	}
	
	public void onSave(View v) {
		Intent i = new Intent();
		EditText etTweet = (EditText) findViewById(R.id.etCompose);
		i.putExtra("tweetBody",  etTweet.getText().toString());
		postTweet(etTweet.getText().toString(), i, this);
	}
	
	public void postTweet(final String body, final Intent i, final Activity act) {
		client.postTweet(body, new AsyncHttpResponseHandler() {

			@Override
			public void onFinish() {
				// Log.d("debug", "onSuccess is called.......");
				Toast.makeText(act, "Tweet created: " + body, Toast.LENGTH_LONG).show();
				act.setResult(RESULT_OK, i);
				act.finish();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(ComposeActivity.this, "Tweet failed: " + body, Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
				Log.d("debug", s);
				
			}
			
		});
	}	

}
