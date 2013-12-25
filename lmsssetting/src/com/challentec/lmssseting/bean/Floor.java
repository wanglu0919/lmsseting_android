package com.challentec.lmssseting.bean;

public class Floor {

	private String itemName;
	private String itemValue;
	private boolean isHightLinght;//是否高亮显示
	public boolean isHightLinght() {
		return isHightLinght;
	}
	public void setHightLinght(boolean isHightLinght) {
		this.isHightLinght = isHightLinght;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	
}
