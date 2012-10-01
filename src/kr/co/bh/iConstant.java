package kr.co.bh;

/**
 * 전역 상수 정의 인터페이스
 * @author 남주완
 */
public interface iConstant {
	
	/**
	 * 자동 로그인 여부 환경설정 구분값
	 */
	public final static String AUTO_LOGIN = "auto login allow pref";

	/**
	 * 로그인 아이디 환경설정 구분값
	 */
	public final static String LOGIN_ID = "user login id pref";
	
	/**
	 * 로그인 아이디 환경설정 구분값
	 */
	public final static String LOGIN_PWD = "user login pwd pref";	
	
	public final static int BARCODE_LENGTH = 13;
}
