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
 * <h1>�α��� ��Ƽ��Ƽ</h1> �ڵ��α��� üũ, �α��� ����ó��
 * 
 */
public class LoginActivity extends BaseActivity {
	// context
	private Context mContext;
	/**
	 * �ڵ��α��� ������ �������� ���� ȯ�漳��
	 */
	private SharedPreferences mPref;

	private CheckBox checkBtn; // �ڵ� �α��� üũ�ڽ�
	// �ڵ��α��� üũ �÷���
	private boolean checkFlag = false;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		mContext = this;

		// ��Ʈ��ũ�� ������ �ȵǸ� ���� ���� �մϴ�.
		if (CommonUtils.checkNetWork(this) == false) {
			Toast.makeText(this, "��Ʈ��ũ������ ���� �ʽ��ϴ�.\n" + "��Ʈ��ũ ���Ż��¸� Ȯ���ϼ���.",
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		mPref = PreferenceManager.getDefaultSharedPreferences(this);
		// �ڵ��α��� üũ
		// üũ���´� ȯ�漳���� �ٷ� ����
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
	 * �ڵ��α��� ����̸� ���� �α��� ������ ȯ�漳������ ��������
	 */
	private void getLoginInfo() {
		CharSequence userId = mPref.getString(LOGIN_ID, "");
		CharSequence userPwd = mPref.getString(LOGIN_PWD, "");
		if (!TextUtils.isEmpty(userId)) {
			((EditText) findViewById(R.id.user_id)).setText(userId);
			// setting �������� �ڵ��α��� ������ �����´�.
			if (mPref.getBoolean(AUTO_LOGIN, false)) { // �ڵ��α��ν� ��й�ȣ�� �־��ְ�
				// checkBtn.setBackgroundResource(R.drawable.checkboxall_s);
				((EditText) findViewById(R.id.user_pwd)).setText(userPwd);
				// �α��� Ʈ����
				((ImageButton) findViewById(R.id.login_submit)).performClick();
			}
		}
	}

	/**
	 * �α��� üũ�� id,password ���� ����
	 */
	private void doLogin() {
		EditText userIdEt = (EditText) findViewById(R.id.user_id);
		EditText userPwdEt = (EditText) findViewById(R.id.user_pwd);

		CharSequence userId = userIdEt.getText();
		CharSequence userPwd = userPwdEt.getText();

		if (TextUtils.isEmpty(userId)) {
			Toast.makeText(this, "���̵� �Է��ϼ���", Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(userPwd)) {
			Toast.makeText(this, "��й�ȣ�� �Է��ϼ���", Toast.LENGTH_SHORT).show();
			return;
		}

		new AsyncLoginTask().execute(userId.toString(), userPwd.toString());
	}

	/**
	 * �ڵ� �α��ο��� ó��
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
	 * submit button ó��
	 * 
	 * @param v
	 */
	public void mOnclick(final View v) {
		doLogin();
	}

	/**
	 * �α��� ó�� ������ task
	 */
	private class AsyncLoginTask extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog progressDialog;

		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = false;
			// http �� ���� �̸� �� �� �÷���
			Vector<NameValuePair> vars = new Vector<NameValuePair>();
			// HTTP post ����� �̿��Ͽ� �α��� ó��
			vars.add(new BasicNameValuePair("id", params[0])); // ���̵�
			vars.add(new BasicNameValuePair("pw", params[1])); // ��й�ȣ
			String url = SERVER_BASE_URL + "loginCheck.php";
			HttpPost request = new HttpPost(url);
			try {
				request.setEntity(new UrlEncodedFormEntity(vars, "UTF-8"));
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				HttpClient client = new DefaultHttpClient();
				String responseBody = client.execute(request, responseHandler); // ����
				// ���� �α����� ���
				String[] arr = responseBody.trim().split(":");
				if (arr[0].contains(LOGIN_RESULT_OK)) {
					SharedPreferences.Editor editor = mPref.edit();
					editor.putString(LOGIN_ID, arr[2].trim());
					editor.putString(MEJANG_NAME, arr[1].trim());
					editor.putString(CORNER, arr[3].trim());
					editor.commit();
					result = true;
					// �ڵ� �α��� ���� Ȯ��
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
			// �α����� �����̸� ���̵� ������ ������������ ������
			if (result) {
				// ������ �α������� ó�� ����
				Intent intent = new Intent(mContext, MenuActivity.class);
				startActivity(intent);
				finish();
			} else {
				Toast.makeText(LoginActivity.this, "�α��� ������ Ȯ���ϼ���",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this, "�α���",
					"��ø� ��ٷ� �ּ���..", true);
		}

	}

}
