package kr.co.bh;

import java.util.Calendar;

import kr.co.bh.utils.MyProgressDialog;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;

/**
 * 웹뷰 액티비티
 */
public class WebClientActivity extends BaseActivity {
	private WebView mWebView; // 웹뷰
	private MyProgressDialog progressDialog; // 로딩 다이얼로그
	// base url
	// public final static String ASSETS_URL =
	// "file:///android_asset/basicHouse/html/";
	public final static String ASSETS_URL = "http://wiseroh.vps.phps.kr/basicHouse/html/";

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view_layout);
		initLayout(); // 레이아웃 초기화
		// setLoadingAni(); // 로딩이미지 초기화
		mWebView.loadUrl(ASSETS_URL + getIntent().getStringExtra("filename"));
		initLoadingBar();
	}

	private void initLoadingBar() {
		progressDialog = new MyProgressDialog(this);
		progressDialog.setTitle("로딩중");
		progressDialog.setMessage("잠시만 기다려 주세요..");
		progressDialog.setMax(10000);
	}

	/**
	 * 레이아웃 엘리먼트 후킹 및 이벤트 처리
	 */
	private void initLayout() {
		// 엘리먼트 후킹
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setWebViewClient(new MyViewClient());
		WebSettings set = mWebView.getSettings(); // 웹뷰 설정
		set.setJavaScriptEnabled(true); // 자바스크립트 활성
		// 자바스크립트 연결 인터페이스
		mWebView.addJavascriptInterface(new MyJavaScriptInterface(),
				"basicHouse");
		// alert 창을 띄우기 위해 WebChromeClient 설정
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(final WebView view, final String url,
					final String message, final android.webkit.JsResult result) {
				new AlertDialog.Builder(WebClientActivity.this)
						.setTitle("알림")
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
	 * 자바스크립트 인터페이스
	 */
	final class MyJavaScriptInterface {

		/**
		 * 로딩바 보여주기
		 */
		public void onLodingBarShow() {
			WebClientActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressDialog.show();
				}
			});
		}

		/**
		 * 로딩다이얼로그 숨기기
		 */
		public void onLodingBarHide() {
			WebClientActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
				}
			});
		}

		/**
		 * 데이트 피커
		 */
		public void onDatePickerShow() {
			// 날자 선택시 자바스크립트 메소드로 선택한 날짜값 반환처리
			final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(final DatePicker view, final int year,
						final int monthOfYear, final int dayOfMonth) {
					String yyyymmdd = String.format("%04d-%02d-%02d", year,
							monthOfYear + 1, dayOfMonth);
					Log.i("certificate", "alarm Date -> " + yyyymmdd);
					mWebView.loadUrl("javascript:onDatePick('" + yyyymmdd
							+ "')");
				}

			};
			Calendar cal = Calendar.getInstance();
			// 날자선택 팝업을 엽니다!
			new DatePickerDialog(WebClientActivity.this, dateSetListener,
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH)).show();
		}

		public void onStyleImageShow(String imgUrl) {
			Intent intent = new Intent(WebClientActivity.this,
					StyleImageViewActivity.class);
			intent.putExtra(
					"imageUrl",
					"http://www.basichouse.co.kr/_data/attach/201209/19/85560d431e3117053440e9fabac7aacf.jpg");
			startActivity(intent);
		}
	}

	/**
	 * 웹뷰 설정 로딩시 로딩뷰를 보여주고 로딩이 끝나면 로딩뷰를 닫아준다.
	 */
	private class MyViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(final WebView view,
				final String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(final WebView view, final String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}

			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(WebClientActivity.this);
			// 웹뷰 자바스크립트를 통해 매장코드값, 매장명, 코너를 넘긴다.
			view.loadUrl("javascript:onMejangCode('"
					+ pref.getString(LOGIN_ID, "") + "', '"
					+ pref.getString(MEJANG_NAME, "") + "', '"
					+ pref.getString(CORNER, "") + "')");
		}

		@Override
		public void onPageStarted(final WebView view, final String url,
				final Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			progressDialog.show();
		}
	}
}
