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
 * 多层节点adapter
 * 主要重写getItem与getItemCount实现成员隐藏功能
 */

public abstract class MultiSelectAdapter<T> extends RecyclerView.Adapter {

    // 需要展示的最外层节点
    private final List<MultiSelectNode<T>> topGroups = new ArrayList<>();

    public void setData(MultiSelectNode<T> parent) {
        topGroups.clear();
        addData(parent);
    }

    public void setData(List<MultiSelectNode<T>> list) {
        topGroups.clear();
        addData(list);
    }

    public void addData(List<MultiSelectNode<T>> list) {
        if (list != null) {
            topGroups.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addData(MultiSelectNode<T> parent) {
        if (parent != null) {
            topGroups.add(parent);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getHierarchy();
    }

    @Override
    public int getItemCount() {
        return getTreeSize(topGroups);
    }

    /**
     * 获取指定位置的data
     * @param position itemPosition
     * @return MultiSelectNode or null
     */
    public MultiSelectNode<T> getItem(int position) {
        int[] cur = {position};
        return getNode(topGroups, cur);
    }

    /**
     * 先序遍历 - 获取展示的总长度 （isExpand = true）
     * @param nodes nodes
     * @return int
     */
    protected int getTreeSize(List<MultiSelectNode<T>> nodes) {
        int size = 0;
        for (MultiSelectNode<T> node : nodes) {
            size++;
            if (node.isExpand()) {
                size += getTreeSize(node.getChildren());
            }
        }
        return size;
    }


    /**
     * 先序遍历 - 获取指定位置的节点
     *
     * @param nodes    nodes
     * @param position itemPosition 数组只是为了实现手动box实现共享position
     * @return MultiSelectNode or null
     */
    protected MultiSelectNode<T> getNode(List<MultiSelectNode<T>> nodes, final int[] position) {
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
