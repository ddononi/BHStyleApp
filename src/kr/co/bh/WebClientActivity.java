package kr.co.bh;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;

/**
 * ���� ��Ƽ��Ƽ
 */
public class WebClientActivity extends BaseActivity {
	private WebView mWebView; 					 // ����
	private ImageView loadingImageView; 		 // �ε��̹���
	private String currentUrl;							 // ���� url ����
	private ProgressDialog progressDialog;		 // �ε� ���̾�α�
	private DatePickerDialog dateDialog;
	// base url
	//public final static String ASSETS_URL = "file:///android_asset/basicHouse/html/";
	public final static String ASSETS_URL ="http://wiseroh.vps.phps.kr/basicHouse/html/";
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view_layout);
		initLayout(); // ���̾ƿ� �ʱ�ȭ
		//setLoadingAni(); // �ε��̹��� �ʱ�ȭ
		mWebView.loadUrl(ASSETS_URL + getIntent().getStringExtra("filename"));
		progressDialog = new ProgressDialog(this);
		progressDialog.setMax(10000);
	}

	/**
	 * ���̾ƿ� ������Ʈ ��ŷ �� �̺�Ʈ ó��
	 */
	private void initLayout() {
		// ������Ʈ ��ŷ
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setWebViewClient(new MyViewClient());
		WebSettings set = mWebView.getSettings(); // ���� ����
		set.setJavaScriptEnabled(true); // �ڹٽ�ũ��Ʈ Ȱ��
		// �ڹٽ�ũ��Ʈ ���� �������̽�
		mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "basicHouse");
		// alert â�� ���� ���� WebChromeClient ����
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(final WebView view, final String url,
					final String message, final android.webkit.JsResult result) {
				new AlertDialog.Builder(WebClientActivity.this)
						.setTitle("�˸�")
						.setMessage(message)
						.setPositiveButton(android.R.string.ok,
								new AlertDialog.OnClickListener() {
									@Override
									public void onClick(
											final DialogInterface dialog,
											final int which) {
										result.confirm();
									}
								}).setCancelable(false).create().show();

				return true;
			}
		});


	}
	
	/**
	 * �ڹٽ�ũ��Ʈ �������̽�
	 */
	final class MyJavaScriptInterface {
		
		/**
		 * �ε��� �����ֱ�
		 */
	    public void onLodingBarShow() {
	    	progressDialog = ProgressDialog.show(WebClientActivity.this, "�˻���", "��ø� ��ٷ� �ּ���..", true);
	    }
	    
	    /**
	     *	�ε����̾�α� ����� 
	     */
	    public void onLodingBarHide() {
	    	if(progressDialog != null && progressDialog.isShowing()){
	    		progressDialog.dismiss();
	    	}
	    }
	    
	    /**
	     *	����Ʈ ��Ŀ  
	     */
	    public void onDatePickerShow() {
			// ���� ���ý� �ڹٽ�ũ��Ʈ �޼ҵ�� ������ ��¥�� ��ȯó��
			final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
						String yyyymmdd = String.format("%04d-%02d-%02d", year, monthOfYear+1, dayOfMonth);
						Log.i("certificate", "alarm Date -> " + yyyymmdd);
						mWebView.loadUrl("javascript:onDatePick('" + yyyymmdd + "')");
				}
		
			};			
			Calendar cal = Calendar.getInstance();
			// ���ڼ��� �˾��� ���ϴ�!
			 new DatePickerDialog(WebClientActivity.this, dateSetListener, 
					cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH)
			).show();
	    }	    
	    
	    public void onStyleImageShow(String imgUrl){
			Intent intent = new Intent(WebClientActivity.this, StyleImageViewActivity.class);
			intent.putExtra("imageUrl", "http://www.basichouse.co.kr/_data/attach/201209/19/85560d431e3117053440e9fabac7aacf.jpg" );
			startActivity(intent);
	    }

	}   	

	/**
	 * ���� ���� �ε��� �ε��並 �����ְ� �ε��� ������ �ε��並 �ݾ��ش�.
	 */
	private class MyViewClient extends WebViewClient {
		private ProgressDialog progressDialog;
		@Override
		public boolean shouldOverrideUrlLoading(final WebView view,
				final String url) {
			currentUrl = url;
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(final WebView view, final String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
	    	if(progressDialog != null && progressDialog.isShowing()){
	    		progressDialog.dismiss();
	    	}
	    	
	    	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(WebClientActivity.this);
	    	// ������ �ε��� �Ϸ�Ǹ� ���� �ڹٽ�ũ��Ʈ�� ���� �����ڵ尪�� ������.
	    	view.loadUrl("javascript:onShopCode('" + pref.getString(LOGIN_ID, "") + "')");
		}

		@Override
		public void onPageStarted(final WebView view, final String url,
				final Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			progressDialog = ProgressDialog.show(WebClientActivity.this, "�ε���", "��ø� ��ٷ� �ּ���..", true);
		}
	}
}
