package com.deezer.android.cordovadeezerplugin;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.event.DialogError;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.OAuthException;

/**
 * 
 * @author Xavier
 * 
 */
public class DeezerSDKController implements DeezerJSListener {

	private final static String LOG_TAG = "DeezerSDKController";

	/** Permissions requested on Deezer accounts. */
	private final static String[] PERMISSIONS = new String[] { "basic_access", };

	private Activity mActivity;
	private DeezerConnect mConnect;

	/**
	 * 
	 * @param activity
	 */
	public DeezerSDKController(Activity activity) {
		mActivity = activity;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// DeezerJSListener Implementation
	// /////////////////////////////////////////////////////////////////////////////

	@Override
	public void init(CallbackContext callbackContext, String appId) {
		mConnect = new DeezerConnect(mActivity, appId);
		callbackContext.success();
	}

	@Override
	public void login(CallbackContext callbackContext) {
		final AuthListener listener = new AuthListener(callbackContext);

		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mConnect.authorize(mActivity, PERMISSIONS, listener);
			}
		});
	}

	@Override
	public void onPlayTracks(CallbackContext callbackContext, String ids,
			int index, int offset, boolean autoPlay, boolean addToQueue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayAlbum(CallbackContext callbackContext, String id,
			int index, int offset, boolean autoPlay, boolean addToQueue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayPlaylist(CallbackContext callbackContext, String id,
			int index, int offset, boolean autoPlay, boolean addToQueue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayRadio(CallbackContext callbackContext, String id,
			int index, int offset, boolean autoPlay, boolean addToQueue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayArtistRadio(CallbackContext callbackContext, String id,
			int index, int offset, boolean autoPlay, boolean addToQueue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlay(CallbackContext callbackContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause(CallbackContext callbackContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNext(CallbackContext callbackContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPrev(CallbackContext callbackContext) {
		// TODO Auto-generated method stub

	}

	// /////////////////////////////////////////////////////////////////////////////
	// DeezerJSListener Implementation
	// /////////////////////////////////////////////////////////////////////////////

	private class AuthListener implements DialogListener {

		private CallbackContext mContext;

		public AuthListener(CallbackContext context) {
			mContext = context;
		}

		@Override
		public void onComplete(Bundle bundle) {
			Log.i(LOG_TAG, "Logged In!");

			JSONObject dict = new JSONObject();
			for (String key : bundle.keySet()) {
				Log.d(LOG_TAG, key + " -> " + bundle.getString(key));

				try {
					dict.put(key, bundle.getString(key));
				} catch (JSONException e) {
					Log.e(LOG_TAG, "Fuck");
				}
			}

			mContext.success(dict);
		}

		@Override
		public void onCancel() {
			Log.d(LOG_TAG, "onCancel");
		}

		@Override
		public void onDeezerError(DeezerError e) {
			Log.e(LOG_TAG, "onDeezerError", e);
		}

		@Override
		public void onError(DialogError e) {
			Log.e(LOG_TAG, "onError", e);
		}

		@Override
		public void onOAuthException(OAuthException e) {
			Log.e(LOG_TAG, "onOAuthException", e);
		}
	}

}
