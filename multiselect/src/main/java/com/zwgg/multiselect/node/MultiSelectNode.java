package com.zwgg.multiselect.node;

import com.zwgg.multiselect.event.MultiSelectEvent;
import com.zwgg.multiselect.event.TreeNodeEvent;

/**
 * Class: MultiSelectNode
 * Author: ZhouWei
 * Date: 2017/10/17
 * Time: 14:55
 * 多层级复选树节点类 - 具有多选和展开功能，并可以添加viewModel
 */

public class MultiSelectNode<T> extends SimpleTreeNode<MultiSelectNode<T>, MultiSelectEvent> {

    private boolean isSelected;
    private boolean isExpand;
    private T viewModel;

    public MultiSelectNode(int hierarchy) {
        super(hierarchy);
    }

    @Override
    public void onEvent(MultiSelectEvent event) {
        switch (event.getNotifyType()) {
            case TreeNodeEvent.NOTIFY_CHILDREN:
                if (event.getEventType() == MultiSelectEvent.EVENT_SET_SELECTED) {
                    if (event.isSelected() != isSelected()) {
                        setSelected(event.isSelected());
                        notifyChildren(event);
                    }
                }
                break;
            case TreeNodeEvent.NOTIFY_PARENT:
                if (event.getEventType() == MultiSelectEvent.EVENT_SET_SELECTED) {
                    if (recheckSelected() != isSelected()) {
                        setSelected(!isSelected());
                        notifyParent(event);
                    }
                }
                break;
            case TreeNodeEvent.NOTIFY_BROTHER:
                if (event.getEventType() == MultiSelectEvent.EVENT_SET_EXPAND) {
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
        MultiSelectEvent event = new MultiSelectEvent(MultiSelectEvent.EVENT_SET_EXPAND);
        event.setExpand(isExpand);
        notifyBrother(event);
    }


    public void setParentRecheckSelected() {
        MultiSelectEvent event = new MultiSelectEvent(MultiSelectEvent.EVENT_SET_SELECTED);
        notifyParent(event);
    }

    public void setChildrenSelected(boolean isSelected) {
        MultiSelectEvent event = new MultiSelectEvent(MultiSelectEvent.EVENT_SET_SELECTED);
        event.setSelected(isSelected);
        notifyChildren(event);
    }

    public boolean recheckSelected() {
        for (MultiSelectNode child : children) {
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
