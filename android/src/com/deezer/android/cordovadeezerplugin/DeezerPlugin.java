package com.deezer.android.cordovadeezerplugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author Xavier
 * 
 */
public class DeezerPlugin extends CordovaPlugin {

	private CordovaInterface mInterface;
	private CordovaWebView mWebView;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);

		Log.i("DeezerPlugin", "initialize");
		mInterface = cordova;
		mWebView = webView;
	}

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {

		Log.i("DeezerPlugin", "execute : " + action);
		
		Toast.makeText(mInterface.getActivity(), action, Toast.LENGTH_LONG)
				.show();

		return true;
	}
}
