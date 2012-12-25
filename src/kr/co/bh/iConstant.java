package kr.co.bh;

/**
 * 전역 상수 정의 인터페이스
 * 
 * @author 남주완
 */
public interface iConstant {

	/**
	 * 자동 로그인 여부 환경설정 구분값
	 */
	public final static String AUTO_LOGIN = "auto_login_allow";

	/**
	 * 로그인 아이디 환경설정 구분값
	 */
	public final static String LOGIN_ID = "user login id pref";

	/**
	 * 로그인 아이디 환경설정 구분값
	 */
	public final static String LOGIN_PWD = "user login pwd pref";

	/**
	 * bar code length
	 */
	public final static int BARCODE_LENGTH = 13;

	/**
	 * 문의 전화 번호
	 */
	public final static String BH_TEL = "1544-0000";

	public final static String HOME_PAGE = "http://www.basichouse.co.kr/";

	/**
	 * 로그인 정상 결과값
	 */
	public final static String LOGIN_RESULT_OK = "OK";

	/**
	 * 매장 이름
	 */
	public final static String MEJANG_NAME = "mjnm";

	/**
	 * 매장 코너
	 */
	public final static String CORNER = "corn";

	public final static String GRAS = "gras";

	public final static String SERVER_BASE_URL = "http://wiseroh.vps.phps.kr/";
}
