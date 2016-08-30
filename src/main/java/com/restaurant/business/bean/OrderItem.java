package com.restaurant.business.bean;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class OrderItem implements Serializable{
    private long itemId;

    private long groupId;

    private int status;

    private String itemCode;

    private String itemName;

    private float itemPrice;

    private Timestamp createDate;

    private String itemPicurl;

    private String itemDetails;
    
    private String remark;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getItemPicurl() {
        return itemPicurl;
    }

    public void setItemPicurl(String itemPicurl) {
        this.itemPicurl = itemPicurl;
    }

    public String getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(String itemDetails) {
        this.itemDetails = itemDetails;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}