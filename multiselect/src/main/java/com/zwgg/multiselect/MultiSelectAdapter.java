package com.zwgg.multiselect;

import android.support.v7.widget.RecyclerView;

import com.zwgg.multiselect.node.MultiSelectedNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: MultiSelectAdapter
 * Author: ZhouWei
 * Date: 2017/10/19
 * Time: 15:44
 */

public abstract class MultiSelectAdapter<T> extends RecyclerView.Adapter {

    private final List<MultiSelectedNode<T>> groups = new ArrayList<>();

    public void setData(MultiSelectedNode<T> parent) {
        groups.clear();
        if (parent != null) {
            groups.add(parent);
        }
        notifyDataSetChanged();
    }

    public void setData(List<MultiSelectedNode<T>> list) {
        groups.clear();
        if (list != null) {
            groups.addAll(list);
        }
        notifyDataSetChanged();
    }

    public MultiSelectedNode<T> getItem(int position) {
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


    public int getTreeSize(List<MultiSelectedNode<T>> nodes) {
        int size = 0;
        for (MultiSelectedNode<T> node : nodes) {
            size++;
            if (node.isExpand()) {
                size += getTreeSize(node.getChildren());
            }
        }
        return size;
    }


    public MultiSelectedNode<T> getNode(List<MultiSelectedNode<T>> nodes, int[] position) {
        for (MultiSelectedNode<T> node : nodes) {
            if (position[0] == 0) {
                return node;
            }
            position[0]--;
            if (node.isExpand() && node.getChildren().size() > 0) {
                MultiSelectedNode<T> finalNode = getNode(node.getChildren(), position);
                if (finalNode != null) {
                    return finalNode;
                }
            }
        }
        return null;
    }


}
