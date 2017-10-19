package com.zwgg.multiselect.node;

import com.zwgg.multiselect.event.TreeNodeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: SimpleTreeNode
 * Author: ZhouWei
 * Date: 2017/10/16
 * Time: 10:35
 * 简单的树节点模板类
 * 这个泛型可能有点费解，其实就是自限定泛型：用于以基类导出类作为自身的泛型，以实现模板功能
 * @see Enum
 * 例如：ClassNameA extend SimpleTreeNode< ClassNameA , T >
 */

public abstract class SimpleTreeNode<K extends SimpleTreeNode<K, T>, T extends TreeNodeEvent> {

    protected int hierarchy;

    protected K parent = null;

    protected final List<K> children = new ArrayList<>();

    public SimpleTreeNode() {
    }

    public SimpleTreeNode(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void bindingParent(K parent) {
        this.parent = parent;
    }

    public void bindingChild(K child) {
        this.children.add(child);
    }

    public void bindingChildren(List<K> children) {
        this.children.clear();
        this.children.addAll(children);
    }

    public void dataBinding(K parent, K child) {
        parent.bindingChild(child);
        child.bindingParent(parent);
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }


    public void notifyParent(T event) {
        if (parent != null) {
            event.setNotifyType(TreeNodeEvent.NOTIFY_PARENT);
            parent.onEvent(event);
        }
    }

    public void notifyChildren(T event) {
        event.setNotifyType(TreeNodeEvent.NOTIFY_CHILDREN);
        for (K child : children) {
            child.onEvent(event);
        }
    }

    public void notifyBrother(T event) {
        if (parent != null) {
            event.setNotifyType(TreeNodeEvent.NOTIFY_BROTHER);
            for (K child : parent.children) {
                if (child != this) {
                    child.onEvent(event);
                }
            }
        }
    }

    public abstract void onEvent(T event);


    public List<K> getChildren() {
        return children;
    }
}
