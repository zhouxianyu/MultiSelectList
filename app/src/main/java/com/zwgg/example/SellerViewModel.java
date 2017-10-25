package com.zwgg.example;

import com.zwgg.multiselect.node.MultiSelectNode;

/**
 * Class: SellerViewModel
 * Author: ZhouWei
 * Date: 2017/10/18
 * Time: 11:05
 */

public class SellerViewModel extends MultiSelectNode<SellerViewModel>{

    public SellerViewModel(String text,int viewType) {
        super(viewType);
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
