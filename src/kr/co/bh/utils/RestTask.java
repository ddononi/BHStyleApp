package kr.co.bh.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * 기본 http 요청 클래스
 * 
 * @author 남주완
 * 
 */
public class RestTask extends AsyncTask<HttpUriRequest, Void, String> {

	public static final String HTTP_RESPONSE = "httpResponse";

	private Context mContext;
	private HttpClient mClient;
	private String mAction;

	/**
	 * 기본 Http
	 * 
	 * @param context
	 * @param action
	 */
	public RestTask(Context context, String action) {
		this.mContext = context;
		this.mAction = action;
		this.mClient = new DefaultHttpClient();
	}

	/**
	 * Http 파라미터를 사용
	 * 
	 * @param context
	 * @param client
	 * @param action
	 */
	public RestTask(Context context, HttpClient client, String action) {
		this.mContext = context;
		this.mClient = client;
		this.mAction = action;
	}

	@Override
	protected String doInBackground(HttpUriRequest... params) {
		try {
			HttpUriRequest request = params[0];
			HttpResponse serverResponse = mClient.execute(request);

			BasicResponseHandler handler = new BasicResponseHandler();
			String response = handler.handleResponse(serverResponse);
			return response;
		} catch (HttpResponseException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * response result
	 */
	@Override
	protected void onPostExecute(String result) {
		Intent intent = new Intent(mAction);
		intent.putExtra(HTTP_RESPONSE, result);
		// 
		mContext.sendBroadcast(intent);
	}
	
	
	

}
