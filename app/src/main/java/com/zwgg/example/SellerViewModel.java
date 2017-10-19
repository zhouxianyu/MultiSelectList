package com.zwgg.example;

/**
 * Class: SellerViewModel
 * Author: ZhouWei
 * Date: 2017/10/18
 * Time: 11:05
 */

public class SellerViewModel {

    public SellerViewModel(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
