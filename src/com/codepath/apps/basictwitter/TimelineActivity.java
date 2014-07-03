package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {
	
	private static final int REQUEST_CODE = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
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
	     
	     HomeTimelineFragment fragment = 
	    		 (HomeTimelineFragment)getSupportFragmentManager().findFragmentByTag("home");
	     fragment.clear();
	     fragment.populateTimeline();
	     // Toast the name to display temporarily on screen
	     Toast.makeText(this, tweetBody, Toast.LENGTH_LONG).show();
	  }
	} 
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
		
	}
	
    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab tab1 = actionBar
            .newTab()
            .setText("Home")
            .setIcon(R.drawable.ic_home)
            .setTag("HomeTimelineFragment")
            .setTabListener(
                new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
                                HomeTimelineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        Tab tab2 = actionBar
            .newTab()
            .setText("Mentions")
            .setIcon(R.drawable.ic_mentions)
            .setTag("MentionsTimelineFragment")
            .setTabListener(
                new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
                                MentionsTimelineFragment.class));
        actionBar.addTab(tab2);
    }
    
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                onComposeAction(item);
                return true;
            case R.id.miProfile:
                onProfileView(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }    	
    }*/
}
