package com.tc2r.uturnconnect.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tc2r.uturnconnect.R;

/**
 * Created by Tc2r on 4/25/2017.
 * <p>
 * Description:
 */

public class GalleryFragment extends Fragment{

	private WebView webView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// Create/get fragment's view
		View view = inflater.inflate(R.layout.fragment_gallery, container, false);
		initVariables(view);
		initMapWebview();
		return view;
	}

	private void initVariables(View view) {
		// Webview Variables
		webView = (WebView) view.findViewById(R.id.webview_media);
	}

	private void initMapWebview() {
		String PLAYLIST_URL = getString(R.string.url_utv_playlist);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
			}
		});

		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(PLAYLIST_URL);
	}
}
