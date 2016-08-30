package com.restaurant.web.bean;

import java.util.List;

/**
 * Created by YUHB on 2016/8/29.
 */
public class PrintOrderBean {

    private String itemName;

    private float itemPrice;

    private String itemRemark;

    private String productNo;

    private List<Foods> foods;

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

    public String getItemRemark() {
        return itemRemark;
    }

    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark;
    }

    public List<Foods> getFoods() {
        return foods;
    }

    public void setFoods(List<Foods> foods) {
        this.foods = foods;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public static class Foods {

        private long foodId;

        private String foodName;

        private float foodPrice;

        private int foodNum;

        public long getFoodId() {
            return foodId;
        }

        public void setFoodId(long foodId) {
            this.foodId = foodId;
        }

        public int getFoodNum() {
            return foodNum;
        }

        public void setFoodNum(int foodNum) {
            this.foodNum = foodNum;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public float getFoodPrice() {
            return foodPrice;
        }

        public void setFoodPrice(float foodPrice) {
            this.foodPrice = foodPrice;
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("itemName: ").append(this.getItemName())
              .append(", itemPrice: ").append(this.getItemPrice())
              .append(", itemRemark: ").append(this.getItemRemark())
              .append(", productNo: ").append(this.getProductNo())
              .append(", foods: [");
        for(Foods food : this.getFoods()){
            buffer.append(food.getFoodId()).append(", ")
                    .append(food.getFoodName()).append(", ")
                    .append(food.getFoodPrice()).append(", ")
                    .append(food.getFoodNum()).append("; ");
        }

        buffer.append("].");

        return buffer.toString();
    }
}
