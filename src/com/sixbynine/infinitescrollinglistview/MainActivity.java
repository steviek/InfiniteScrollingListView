package com.sixbynine.infinitescrollinglistview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Demo Activity to display the InfiniteScrollAdapter
 * @author Steven Kideckel
 */
public class MainActivity extends Activity implements OnScrollListener {

	private ListView listView;
	private ProgressBar progressBar;
	private InfiniteScrollAdapter<String> adapter;
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mHandler = new Handler();
		
		//inflate the progress bar from the footer, it is wrapped in a FrameLayout since
		//ListViews don't shrink in height when a child is set to visibility gone, but
		//a FrameLayout with height of wrap_content will
		View footer = getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
		progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
		
		listView = (ListView) findViewById(R.id.listView);
		listView.addFooterView(footer);
				
		String[] vals = TribonacciCalculator.getTribonacci();
		adapter = new InfiniteScrollAdapter<String>(this, vals, 20, 10);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this); //listen for a scroll movement to the bottom
		progressBar.setVisibility((20 < vals.length)? View.VISIBLE : View.GONE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_reset: 
			adapter.reset(); //reset the adapter to its initial configuration
			listView.setSelection(0); //go to the top
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if(firstVisibleItem + visibleItemCount == totalItemCount && !adapter.endReached() && !hasCallback){ //check if we've reached the bottom
			mHandler.postDelayed(showMore, 300);
			hasCallback = true;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	private boolean hasCallback;
	private Runnable showMore = new Runnable(){
		public void run(){
			boolean noMoreToShow = adapter.showMore(); //show more views and find out if
			progressBar.setVisibility(noMoreToShow? View.GONE : View.VISIBLE);
			hasCallback = false;
		}
	};
	

}
