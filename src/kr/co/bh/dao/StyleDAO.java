package kr.co.bh.dao;

/**
 * 스타일 수량 체크 DAO 
 * @author 남주완
 */
public class StyleDAO {
	private String styleCode; // 스타일코드명
	private String originalPrice; // 원가격
	private String salePrice; // 할인가격
	private String discount; // 할인율
	private String stock; // 재고
	private String realStock; // 실재고
	private String imageUrl; // 이미지 링크

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
