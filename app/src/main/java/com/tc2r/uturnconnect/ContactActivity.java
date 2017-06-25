package com.tc2r.uturnconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

	private final static String TAG = "ContactActivity";
	// Email Form Variables
	private Button btnSubmit;
	private EditText etEmailName, etEmailAddress, etEmailSubject, etEmailMessage;

	private static final String[] CHURCH_EMAILS = {"info@myuturn.org", "ksanabria@myuturn.org", "ksanabriauturn@gmail.com"};

	// Map Variables
	private WebView webView;
	private String MAP_URL;

	// Social Variables
	private ImageView ivFacebook, ivYoutube;
	private String FB_URL;
	private String YT_URL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);

		initVariables();
		initMapWebview();
		initEmailSubmitListener();
	}

	private void initMapWebview() {
		MAP_URL = getString(R.string.url_church_location);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);

		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {

			}

		});

		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(MAP_URL);
	}

	private void initEmailSubmitListener() {
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				String address = etEmailAddress.getText().toString();
				String message = etEmailMessage.getText().toString();
				String subject = etEmailSubject.getText().toString();
				String eName = etEmailName.getText().toString();

				if(address == null || address.equals("")){
					emailError(getString(R.string.contact_error_address));
					return;
				}

				if(message == null || message.equals("")){
					emailError(getString(R.string.contact_error_message));
					return;
				}

				if(subject == null || subject.equals("")){
					emailError(getString(R.string.contact_error_subject));
					return;
				}

				StringBuilder sb = new StringBuilder("From: ");
				sb.append(eName);
				sb.append("\n");
				sb.append(address);
				sb.append("\n");
				sb.append(message);

				message = sb.toString();


				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setData(Uri.parse("mailto:"));
				emailIntent.setType("message/rfc822");
				emailIntent.putExtra(Intent.EXTRA_EMAIL, CHURCH_EMAILS);
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
				emailIntent.putExtra(Intent.EXTRA_TEXT, message);

				try{
					startActivity(Intent.createChooser(emailIntent, getString(R.string.send_mail_title)));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(ContactActivity.this, R.string.error_no_client, Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	private void emailError(String string) {
		Toast.makeText(this, R.string.error_email_prefix, Toast.LENGTH_SHORT).show();
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();

	}


	private void initVariables() {

		// email variables
		etEmailAddress = (EditText) findViewById(R.id.et_contact_email_email);
		etEmailName = (EditText) findViewById(R.id.et_contact_email_name);
		etEmailSubject = (EditText) findViewById(R.id.et_contact_email_subject);
		etEmailMessage = (EditText) findViewById(R.id.et_contact_email_message);
		btnSubmit = (Button) findViewById(R.id.btn_contact_email_send);

		// Map Webview Variables
		webView = (WebView) findViewById(R.id.webview_contacts);

		// social Variables
		ivFacebook = (ImageView) findViewById(R.id.iv_icon_facebook);
		ivYoutube = (ImageView) findViewById(R.id.iv_icon_youtube);
		FB_URL =  getString(R.string.url_facebook_uturn);
		YT_URL =  getString(R.string.url_youtube_uturn);
		ivFacebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FB_URL));
				startActivity(fbIntent);
			}
		});
		ivYoutube.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YT_URL));
				startActivity(fbIntent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}


