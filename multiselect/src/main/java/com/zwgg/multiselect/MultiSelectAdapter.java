package com.zwgg.multiselect;

import android.support.v7.widget.RecyclerView;

import com.zwgg.multiselect.node.MultiSelectNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: MultiSelectAdapter
 * Author: ZhouWei
 * Date: 2017/10/19
 * Time: 15:44
 */

public abstract class MultiSelectAdapter<T> extends RecyclerView.Adapter {

    private final List<MultiSelectNode<T>> groups = new ArrayList<>();

    public void setData(MultiSelectNode<T> parent) {
        groups.clear();
        if (parent != null) {
            groups.add(parent);
        }
        notifyDataSetChanged();
    }

    public void setData(List<MultiSelectNode<T>> list) {
        groups.clear();
        if (list != null) {
            groups.addAll(list);
        }
        notifyDataSetChanged();
    }

    public MultiSelectNode<T> getItem(int position) {
        int[] cur = {position};
        return getNode(groups, cur);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getHierarchy();
    }

    @Override
    public int getItemCount() {
        return getTreeSize(groups);
    }


    public int getTreeSize(List<MultiSelectNode<T>> nodes) {
        int size = 0;
        for (MultiSelectNode<T> node : nodes) {
            size++;
            if (node.isExpand()) {
                size += getTreeSize(node.getChildren());
            }
        }
        return size;
    }


    public MultiSelectNode<T> getNode(List<MultiSelectNode<T>> nodes, int[] position) {
        for (MultiSelectNode<T> node : nodes) {
            if (position[0] == 0) {
                return node;
            }
            position[0]--;
            if (node.isExpand() && node.getChildren().size() > 0) {
                MultiSelectNode<T> finalNode = getNode(node.getChildren(), position);
                if (finalNode != null) {
                    return finalNode;
                }
            }
        }
        return null;
    }


}
