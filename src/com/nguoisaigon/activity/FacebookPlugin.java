package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

public class FacebookPlugin extends Activity {
	Context context;

	String description;
	String link;
	String caption;

	private UiLifecycleHelper uiHelper;

	public FacebookPlugin(Context context) {
		this.context = context;
	}
	
	public FacebookPlugin() {
	}

	public FacebookPlugin(String desc, String appLink, String cap) {
		description = desc;
		link = appLink;
		caption = cap;
	}

	public void showDialog(String desc, String appLink, String cap) {
		description = desc;
		link = appLink;
		caption = cap;	
	}
	
	public void postToWall() 
	{
		if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					this).setCaption(caption).setDescription(description).setLink(link)
					.build();
			uiHelper.trackPendingDialogCall(shareDialog.present());

		} else {
			// Fallback. For example, publish the post using the Feed Dialog
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		
		description = bundle.getString("description");
		link = bundle.getString("link");
		caption = bundle.getString("caption");
		
		this.postToWall();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
}
