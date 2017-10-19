package com.zwgg.example;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
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
        final MultiSelectNode<SellerViewModel> item = getItem(position);
        if (holder instanceof ItemViewBinder) {
            ((ItemViewBinder) holder).bindView(item);
        }
    }

    public interface ItemViewBinder {

        void bindView(MultiSelectNode<SellerViewModel> itemData);

    }

    private class TitleViewHolder extends RecyclerView.ViewHolder implements ItemViewBinder {
        private TextView textView;
        private Switch seekBarSelected;
        private Switch seekBarVisible;


        TitleViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false));
            textView = (TextView) itemView.findViewById(R.id.text);
            seekBarSelected = (Switch) itemView.findViewById(R.id.seekBar_selected);
            seekBarVisible = (Switch) itemView.findViewById(R.id.seekBar_visible);
        }

        @Override
        public void bindView(final MultiSelectNode<SellerViewModel> itemData) {
            textView.setText(itemData.getViewModel().getText());
            seekBarSelected.setChecked(itemData.isSelected());
            seekBarVisible.setChecked(itemData.isExpand());
            seekBarVisible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemData.setExpand(seekBarVisible.isChecked());
//                    if (seekBarVisible.isChecked()) {
//                        itemData.setOtherGroupsExpand(false);
//                    }
                    notifyDataSetChanged();
                }
            });

            seekBarSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemData.setSelected(seekBarSelected.isChecked());
                    itemData.setChildrenSelected(seekBarSelected.isChecked());
                    itemData.setParentRecheckSelected();
                    notifyDataSetChanged();
                }
            });
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements ItemViewBinder {
        private TextView textView;
        private Switch seekBarSelected;
        private Switch seekBarVisible;

        ItemViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false));
            textView = (TextView) itemView.findViewById(R.id.text);
            seekBarSelected = (Switch) itemView.findViewById(R.id.seekBar_selected);
            seekBarVisible = (Switch) itemView.findViewById(R.id.seekBar_visible);
            seekBarVisible.setVisibility(View.GONE);
        }

        @Override
        public void bindView(final MultiSelectNode<SellerViewModel> itemData) {
            textView.setText(itemData.getViewModel().getText());
            seekBarSelected.setChecked(itemData.isSelected());
            seekBarVisible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemData.setExpand(seekBarVisible.isChecked());
                    if (seekBarVisible.isChecked()) {
                        itemData.setOtherGroupsExpand(false);
                    }
//                    itemData.setChildrenExpand(seekBarVisible.isChecked());
                    notifyDataSetChanged();
                }
            });

            seekBarSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemData.setSelected(seekBarSelected.isChecked());
                    itemData.setChildrenSelected(seekBarSelected.isChecked());
                    itemData.setParentRecheckSelected();
                    notifyDataSetChanged();
                }
            });
        }
    }

}
