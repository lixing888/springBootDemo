package com.springboot.demo.vo;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class Car {

    @NonNull
    private String nbr;//车牌号
    private BigDecimal price;//价格

    public String getNbr() {
        return nbr;
    }

    public void setNbr(String nbr) {
        this.nbr = nbr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
