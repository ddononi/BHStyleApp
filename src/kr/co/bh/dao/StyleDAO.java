package kr.co.bh.dao;

/**
 * ��Ÿ�� ���� üũ DAO 
 * @author ���ֿ�
 */
public class StyleDAO {
	
	
/*	mjcd	varchar(6)	�����ڵ�
	corn	varchar(1)	�����ڳ�
	styl	varchar(8)	��Ÿ���ڵ�
	stcd	varchar(5)	�÷�/������
	sobi	numeric(8,0)	�Һ��ڰ�
	sbps_s	numeric(8,0)	���ǰ�
	dcrp_b	numeric(5,4)	������
	mjlq	numeric(7,0)	���
	sjlq	numeric(7,0)	�����*/
	
	private String styl; // ��Ÿ���ڵ��
	private String sobi; // ������
	private String sbps_s; // ���ΰ���
	private String dcrp_b; // ������
	private String mjlq; // ���
	private String sjlq; // �����
	private String imageUrl; // �̹��� ��ũ


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
