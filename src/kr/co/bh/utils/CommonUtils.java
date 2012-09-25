package kr.co.bh.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * ���� ��ƿ Ŭ����
 * 
 * @author ���ֿ�
 * 
 */
public class CommonUtils {

	/**
	 * ��Ʈ��ũ ������� üũ�� ��带 �佺Ʈ�� �˸��� true or false ��ȯ
	 * 
	 * @return ��Ʈ��ũ ���� ���� true or false
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
			Toast.makeText(context, "Wi-Fi���� �������Դϴ�.", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(context, "3G���� �������Դϴ�.", Toast.LENGTH_SHORT).show();
		}

		if (!isMobileConn && !isWifiConn) {
			/*
			 * ��Ʈ��ũ ������ ���� ������� ����
			 */
			Toast.makeText(context, "��Ʈ��ũ�� �����Ҽ� �����ϴ�.", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

}
