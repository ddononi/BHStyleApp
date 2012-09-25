package kr.co.bh.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * 공통 유틸 클래스
 * 
 * @author 남주완
 * 
 */
public class CommonUtils {

	/**
	 * 네트워크 연결상태 체크후 모드를 토스트를 알린후 true or false 반환
	 * 
	 * @return 네트워크 연결 여부 true or false
	 */
	public static boolean checkNetWork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		// boolean isWifiAvail = ni.isAvailable();
		boolean isWifiConn = ni.isConnectedOrConnecting();
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		// boolean isMobileAvail = ni.isAvailable();
		boolean isMobileConn = ni.isConnectedOrConnecting();
		if (isWifiConn) {
			Toast.makeText(context, "Wi-Fi망에 접속중입니다.", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(context, "3G망에 접속중입니다.", Toast.LENGTH_SHORT).show();
		}

		if (!isMobileConn && !isWifiConn) {
			/*
			 * 네트워크 연결이 되지 않을경우 종료
			 */
			Toast.makeText(context, "네트워크에 연결할수 없습니다.", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

}
