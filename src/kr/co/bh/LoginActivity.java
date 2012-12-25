/**
 *
 */
package kr.co.bh;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import kr.co.bh.utils.CommonUtils;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * <h1>로그인 엑티비티</h1> 자동로그인 체크, 로그인 인증처리
 * 
 */
public class LoginActivity extends BaseActivity {
	// context
	private Context mContext;
	/**
	 * 자동로그인 정보를 가져오기 위한 환경설정
	 */
	private SharedPreferences mPref;

	private CheckBox checkBtn; // 자동 로그인 체크박스
	// 자동로그인 체크 플레그
	private boolean checkFlag = false;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		mContext = this;

		// 네트워크에 연결이 안되면 앱을 종료 합니다.
		if (CommonUtils.checkNetWork(this) == false) {
			Toast.makeText(this, "네트워크연결이 되지 않습니다.\n" + "네트워크 수신상태를 확인하세요.",
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		mPref = PreferenceManager.getDefaultSharedPreferences(this);
		// 자동로그인 체크
		// 체크상태는 환경설정에 바로 저장
		checkBtn = (CheckBox) findViewById(R.id.auto_login_btn);
		checkBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				checkFlag = !checkFlag;
				SharedPreferences.Editor editor = mPref.edit();
				editor.putBoolean(AUTO_LOGIN, checkFlag);
				editor.commit();
			}
		});

		EditText userIdEt = (EditText) findViewById(R.id.user_id);
		EditText userPwdEt = (EditText) findViewById(R.id.user_pwd);
		userIdEt.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					Toast.makeText(LoginActivity.this, "wefwefwef",
							Toast.LENGTH_SHORT).show();
					showSoftKeyboard(v);
				} else {
					hideSoftKeyboard(v);
				}
			}
		});

		getLoginInfo();
	}

	/**
	 * 자동로그인 허용이면 이전 로그인 정보를 환경설정에서 가져오기
	 */
	private void getLoginInfo() {
		CharSequence userId = mPref.getString(LOGIN_ID, "");
		CharSequence userPwd = mPref.getString(LOGIN_PWD, "");
		if (!TextUtils.isEmpty(userId)) {
			((EditText) findViewById(R.id.user_id)).setText(userId);
			// setting 정보에서 자동로그인 정보를 가져온다.
			if (mPref.getBoolean(AUTO_LOGIN, false)) { // 자동로그인시 비밀번호를 넣어주고
				// checkBtn.setBackgroundResource(R.drawable.checkboxall_s);
				((EditText) findViewById(R.id.user_pwd)).setText(userPwd);
				// 로그인 트리거
				((ImageButton) findViewById(R.id.login_submit)).performClick();
			}
		}
	}

	/**
	 * 로그인 체크후 id,password 정보 저장
	 */
	private void doLogin() {
		EditText userIdEt = (EditText) findViewById(R.id.user_id);
		EditText userPwdEt = (EditText) findViewById(R.id.user_pwd);

		CharSequence userId = userIdEt.getText();
		CharSequence userPwd = userPwdEt.getText();

		if (TextUtils.isEmpty(userId)) {
			Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(userPwd)) {
			Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
			return;
		}

		new AsyncLoginTask().execute(userId.toString(), userPwd.toString());
	}

	/**
	 * 자동 로그인여부 처리
	 */
	private void checkAutoLoginSave(CharSequence userId, CharSequence userPwd) {
		if (checkFlag) {
			SharedPreferences.Editor editor = mPref.edit();
			editor.putString(LOGIN_ID, userId.toString());
			editor.putString(LOGIN_PWD, userPwd.toString());
			editor.commit();
		}
	}

	/**
	 * submit button 처리
	 * 
	 * @param v
	 */
	public void mOnclick(final View v) {
		doLogin();
	}

	/**
	 * 로그인 처리 쓰레드 task
	 */
	private class AsyncLoginTask extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog progressDialog;

		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = false;
			// http 로 보낼 이름 값 쌍 컬랙션
			Vector<NameValuePair> vars = new Vector<NameValuePair>();
			// HTTP post 방식을 이용하여 로그인 처리
			vars.add(new BasicNameValuePair("id", params[0])); // 아이디
			vars.add(new BasicNameValuePair("pw", params[1])); // 비밀번호
			String url = SERVER_BASE_URL + "loginCheck.php";
			HttpPost request = new HttpPost(url);
			try {
				request.setEntity(new UrlEncodedFormEntity(vars, "UTF-8"));
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				HttpClient client = new DefaultHttpClient();
				String responseBody = client.execute(request, responseHandler); // 전송
				// 정상 로그인일 경우
				String[] arr = responseBody.trim().split(":");
				if (arr[0].contains(LOGIN_RESULT_OK)) {
					SharedPreferences.Editor editor = mPref.edit();
					editor.putString(LOGIN_ID, arr[2].trim());
					editor.putString(MEJANG_NAME, arr[1].trim());
					editor.putString(CORNER, arr[3].trim());
					editor.commit();
					result = true;
					// 자동 로그인 여부 확인
					checkAutoLoginSave(params[0], params[1]);
				} else {
					Log.i("bh", "responseBody error : " + responseBody);
				}

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				Log.i("bh", e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			// 로그인이 정상이면 아이디 정보를 공유설정값에 저장한
			if (result) {
				// 서버에 로그인인증 처리 수행
				Intent intent = new Intent(mContext, MenuActivity.class);
				startActivity(intent);
				finish();
			} else {
				Toast.makeText(LoginActivity.this, "로그인 정보를 확인하세요",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this, "로그인",
					"잠시만 기다려 주세요..", true);
		}

	}

}
