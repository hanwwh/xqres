package com.restaurant.business.bean;

import java.util.Date;

public class FoodPackage {
    private Integer foodPackageId;

    private Integer foodId;

    private Float packagePrice;

    private String packageName;

    private Date createDate;

    private String remark;

    public Integer getFoodPackageId() {
        return foodPackageId;
    }

    public void setFoodPackageId(Integer foodPackageId) {
        this.foodPackageId = foodPackageId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Float getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(Float packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}