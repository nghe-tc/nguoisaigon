package com.nguoisaigon.util;

import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;

public class Utils {

	public static Typeface tf;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	/**
	 * Unbind drawables.
	 * 
	 * @param view
	 *            the view
	 */
	public static void unbindDrawables(View view) {
		if (view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}

		if (view instanceof ImageView) {
			ImageView imageView = (ImageView) view;
			imageView.setImageBitmap(null);
		} else if (view instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) view;
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				unbindDrawables(viewGroup.getChildAt(i));
			}
			if (!(view instanceof AdapterView))
				viewGroup.removeAllViews();
		}
	}

	/**
	 * Checks if is facebook login.
	 * 
	 * @return true, if is facebook login
	 */
	public static boolean isFacebookLogin() {
		Session s = Session.getActiveSession();
		if (s != null) {
			return s.getState().isOpened();
		}
		return false;
	}

	/**
	 * Check permissions.
	 * 
	 * @return true, if successful
	 */
	public static boolean checkPermissions() {
		Session s = Session.getActiveSession();
		if (s != null) {
			return s.getPermissions().contains("publish_actions");
		} else {
			return false;
		}
	}

	/**
	 * Request permissions.
	 * 
	 * @param activity
	 *            the activity
	 */
	public static void requestPermissions(Activity activity) {
		Session s = Session.getActiveSession();
		if (s != null) {
			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(activity, PERMISSIONS));
		}
	}

	/**
	 * On session state change.
	 * 
	 * @param session
	 *            the session
	 * @param state
	 *            the state
	 * @param exception
	 *            the exception
	 */
	private static void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			Log.i("Facebook", "Logged in...");
		} else if (state.isClosed()) {
			Log.i("Facebook", "Logged out...");
		}
	}

	/** The status callback. */
	public static Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	/**
	 * Post facebook message.
	 * 
	 * @param activity
	 *            the activity
	 * @param message
	 *            the message
	 */
	public static void postFacebookMessage(final Activity activity, String message) {
		if (checkPermissions()) {
			Request request = Request.newStatusUpdateRequest(Session.getActiveSession(), message,
					new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							if (response.getError() == null)
								Toast.makeText(activity, "Status updated successfully", Toast.LENGTH_LONG).show();
						}
					});
			request.executeAsync();
		} else {
			requestPermissions(activity);
		}
	}
}
