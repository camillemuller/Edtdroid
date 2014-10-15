package org.mullercamille.edtdroid.dao;


import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class DAOWITHOUTDB {


	public DAOWITHOUTDB()
	{

	}

	public void getPage()
	{

		WebView myWebView = new WebView(null);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		myWebView.loadUrl("http://ent.univ-tours.fr/");




	}

}
