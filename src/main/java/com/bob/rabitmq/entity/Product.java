package com.bob.rabitmq.entity;


/**
 * @ClassName:Product
 * @Author: Bob Simon
 * @Date: 2018/5/11 0011 上午 8:46
 * @Description:产品
 * @Version:1.0
 */

public class Product {

    public String prodId;
    public String prodName;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }


}
