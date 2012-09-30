package kr.co.bh.dao;

/**
 * 스타일 수량 체크 DAO 
 * @author 남주완
 */
public class StyleDAO {
	
	
/*	mjcd	varchar(6)	매장코드
	corn	varchar(1)	매장코너
	styl	varchar(8)	스타일코드
	stcd	varchar(5)	컬러/사이즈
	sobi	numeric(8,0)	소비자가
	sbps_s	numeric(8,0)	실판가
	dcrp_b	numeric(5,4)	할인율
	mjlq	numeric(7,0)	재고
	sjlq	numeric(7,0)	실재고*/
	
	private String styl; // 스타일코드명
	private String sobi; // 원가격
	private String sbps_s; // 할인가격
	private String dcrp_b; // 할인율
	private String mjlq; // 재고
	private String sjlq; // 실재고
	private String imageUrl; // 이미지 링크


	public String getStyl() {
		return styl;
	}

	public void setStyl(String styl) {
		this.styl = styl;
	}

	public String getSobi() {
		return sobi;
	}

	public void setSobi(String sobi) {
		this.sobi = sobi;
	}

	public String getSbps_s() {
		return sbps_s;
	}

	public void setSbps_s(String sbps_s) {
		this.sbps_s = sbps_s;
	}

	public String getDcrp_b() {
		return dcrp_b;
	}

	public void setDcrp_b(String dcrp_b) {
		this.dcrp_b = dcrp_b;
	}

	public String getMjlq() {
		return mjlq;
	}

	public void setMjlq(String mjlq) {
		this.mjlq = mjlq;
	}

	public String getSjlq() {
		return sjlq;
	}

	public void setSjlq(String sjlq) {
		this.sjlq = sjlq;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
