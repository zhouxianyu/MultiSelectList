package com.zwgg.example;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwgg.multiselect.MultiSelectAdapter;
import com.zwgg.multiselect.node.MultiSelectNode;

/**
 * Class: SellerMultiSelectAdapter
 * Author: ZhouWei
 * Date: 2017/10/18
 * Time: 10:22
 */

public class SellerMultiSelectAdapter extends MultiSelectAdapter<SellerViewModel> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0 || viewType == 1) {
            return new TitleViewHolder(parent);
        } else if (viewType == 2) {
            return new ItemViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SellerViewModel item = getItem(position);
        if (holder instanceof ItemViewBinder) {
            ((ItemViewBinder) holder).bindView(item);
        }
    }

    public interface ItemViewBinder {

        void bindView(SellerViewModel itemData);

    }

    private class TitleViewHolder extends RecyclerView.ViewHolder implements ItemViewBinder {
        private TextView textView;
        private CheckBox checkBox;
        private ImageView imageView;


        TitleViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false));
            textView = (TextView) itemView.findViewById(R.id.text);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        @Override
        public void bindView(final SellerViewModel itemData) {
            textView.setText(itemData.getText());
            if (itemData.getHierarchy() == 0 || itemData.getChildren().size() == 0) {
                imageView.setVisibility(View.INVISIBLE);
            }

            checkBox.setChecked(itemData.isSelected());
            imageView.setSelected(itemData.isExpand());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isExpand = !imageView.isSelected();
                    imageView.setSelected(isExpand);
                    itemData.setExpand(isExpand);
                    if (isExpand) {
                        itemData.setOtherGroupsExpand(false);
                    }
                    notifyDataSetChanged();
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemData.setSelected(checkBox.isChecked());
                    itemData.setChildrenSelected(checkBox.isChecked());
                    itemData.setParentRecheckSelected();
                    notifyDataSetChanged();
                }
            });
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements ItemViewBinder {
        private TextView textView;
        private CheckBox checkBox;


        ItemViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false));
            textView = (TextView) itemView.findViewById(R.id.text);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

        @Override
        public void bindView(final SellerViewModel itemData) {
            textView.setText(itemData.getText());
            checkBox.setChecked(itemData.isSelected());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemData.setSelected(checkBox.isChecked());
                    itemData.setChildrenSelected(checkBox.isChecked());
                    itemData.setParentRecheckSelected();
                    notifyDataSetChanged();
                }
            });
        }
    }

}
