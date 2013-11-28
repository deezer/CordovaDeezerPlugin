package com.deezer.android.cordovadeezerplugin;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.event.DialogError;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.OAuthException;
import com.deezer.sdk.player.AlbumPlayer;
import com.deezer.sdk.player.PlayerWrapper;
import com.deezer.sdk.player.event.PlayerWrapperListener;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

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

	private PlayerWrapper mPlayerWrapper;

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

		Log.i(LOG_TAG, "onPlayAlbum");

		if (mPlayerWrapper != null) {
			Log.d(LOG_TAG, "delete previous player ");

			mPlayerWrapper.stop();
			mPlayerWrapper.release();
			mPlayerWrapper = null;
		}

		try {

			mPlayerWrapper = new AlbumPlayer(mActivity.getApplication(),
					mConnect, new WifiAndMobileNetworkStateChecker());

			((AlbumPlayer) mPlayerWrapper)
					.addPlayerListener(new PlayerListener());
			Log.d(LOG_TAG, "player created");

			long albumId = Long.valueOf(id);

			Log.d(LOG_TAG, "album id " + albumId);

			((AlbumPlayer) mPlayerWrapper).playAlbum(albumId, index);

			Log.d(LOG_TAG, "album playing ");
		} catch (OAuthException e) {
			Log.e(LOG_TAG, "OAuthException", e);
			callbackContext.error("OAuthException");
		} catch (TooManyPlayersExceptions e) {
			Log.e(LOG_TAG, "TooManyPlayersExceptions", e);
			callbackContext.error("TooManyPlayersExceptions");
		} catch (DeezerError e) {
			Log.e(LOG_TAG, "DeezerError", e);
			callbackContext.error("DeezerError");
		} catch (NumberFormatException e) {
			Log.e(LOG_TAG, "NumberFormatException", e);
			callbackContext.error("NumberFormatException");
		}

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
		Log.i(LOG_TAG, "onPlay");

		if (mPlayerWrapper != null) {
			mPlayerWrapper.play();
		}

	}

	@Override
	public void onPause(CallbackContext callbackContext) {
		Log.i(LOG_TAG, "onPause");

		if (mPlayerWrapper != null) {
			mPlayerWrapper.pause();
		}

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
			mContext.error("cancel");
		}

		@Override
		public void onDeezerError(DeezerError e) {
			Log.e(LOG_TAG, "onDeezerError", e);
			mContext.error("DeezerError");
		}

		@Override
		public void onError(DialogError e) {
			Log.e(LOG_TAG, "onError", e);
			mContext.error("Error");
		}

		@Override
		public void onOAuthException(OAuthException e) {
			Log.e(LOG_TAG, "onOAuthException", e);
			mContext.error("OAuthException");
		}
	}

	private class PlayerListener implements PlayerWrapperListener {

		@Override
		public void onAllTracksEnded() {
			Log.i(LOG_TAG, "onAllTracksEnded");
		}

		@Override
		public void onPlayTrack(Track track) {
			Log.i(LOG_TAG, "onPlayTrack " + track.getTitle());
		}

		@Override
		public void onRequestDeezerError(DeezerError e, Object request) {
			Log.e(LOG_TAG, "onRequestDeezerError", e);
		}

		@Override
		public void onRequestIOException(IOException e, Object request) {
			Log.e(LOG_TAG, "onRequestIOException", e);
		}

		@Override
		public void onRequestJSONException(JSONException e, Object request) {
			Log.e(LOG_TAG, "onRequestJSONException", e);
		}

		@Override
		public void onRequestMalformedURLException(MalformedURLException e,
				Object request) {
			Log.e(LOG_TAG, "onRequestMalformedURLException", e);
		}

		@Override
		public void onRequestOAuthException(OAuthException e, Object request) {
			Log.e(LOG_TAG, "onRequestMalformedURLException", e);
		}

		@Override
		public void onTrackEnded(Track arg0) {
			Log.i(LOG_TAG, "onTrackEnded");
		}

	}
}
