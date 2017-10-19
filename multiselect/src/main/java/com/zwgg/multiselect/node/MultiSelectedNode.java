package com.zwgg.multiselect.node;

import com.zwgg.multiselect.event.MultiSelectedEvent;
import com.zwgg.multiselect.event.TreeNodeEvent;

/**
 * Class: MultiSelectedNode
 * Author: ZhouWei
 * Date: 2017/10/17
 * Time: 14:55
 * 多层级复选树节点
 */

public class MultiSelectedNode<T> extends SimpleTreeNode<MultiSelectedNode<T>, MultiSelectedEvent> {

    private boolean isSelected;
    private boolean isExpand;
    private T viewModel;

    public MultiSelectedNode(int hierarchy) {
        super(hierarchy);
    }

    @Override
    public void onEvent(MultiSelectedEvent event) {
        switch (event.getNotifyType()) {
            case TreeNodeEvent.NOTIFY_CHILDREN:
                if (event.getEventType() == MultiSelectedEvent.EVENT_SET_SELECTED) {
                    if (event.isSelected() != isSelected()) {
                        setSelected(event.isSelected());
                        notifyChildren(event);
                    }
                }
                break;
            case TreeNodeEvent.NOTIFY_PARENT:
                if (event.getEventType() == MultiSelectedEvent.EVENT_SET_SELECTED) {
                    if (recheckSelected() != isSelected()) {
                        setSelected(!isSelected());
                        notifyParent(event);
                    }
                }
                break;
            case TreeNodeEvent.NOTIFY_BROTHER:
                if (event.getEventType() == MultiSelectedEvent.EVENT_SET_EXPAND) {
                    if (event.isExpand() != isExpand()) {
                        setExpand(event.isExpand());
                    }
                }
                break;
            default:
                break;
        }
    }

    public void setOtherGroupsExpand(boolean isExpand) {
        MultiSelectedEvent event = new MultiSelectedEvent(MultiSelectedEvent.EVENT_SET_EXPAND);
        event.setExpand(isExpand);
        notifyBrother(event);
    }


    public void setParentRecheckSelected() {
        MultiSelectedEvent event = new MultiSelectedEvent(MultiSelectedEvent.EVENT_SET_SELECTED);
        notifyParent(event);
    }

    public void setChildrenSelected(boolean isSelected) {
        MultiSelectedEvent event = new MultiSelectedEvent(MultiSelectedEvent.EVENT_SET_SELECTED);
        event.setSelected(isSelected);
        notifyChildren(event);
    }

    public boolean recheckSelected() {
        for (MultiSelectedNode child : children) {
            if (!child.isSelected()) {
                return false;
            }
        }
        return true;
    }


    public boolean isExpand() {
        return isExpand;
    }


    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public T getViewModel() {
        return viewModel;
    }

    public void setViewModel(T viewModel) {
        this.viewModel = viewModel;
    }

}
