package kr.co.bh.dao;

/**
 * ��Ÿ�� ���� üũ DAO 
 * @author ���ֿ�
 */
public class StyleDAO {
	private String styleCode; // ��Ÿ���ڵ��
	private String originalPrice; // ������
	private String salePrice; // ���ΰ���
	private String discount; // ������
	private String stock; // ���
	private String realStock; // �����
	private String imageUrl; // �̹��� ��ũ

	public String getStyleCode() {
		return styleCode;
	}

	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getRealStock() {
		return realStock;
	}

	public void setRealStock(String realStock) {
		this.realStock = realStock;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
