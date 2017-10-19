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
    // 依附节点的viewModel
    private T viewModel;

    /**
     * @param hierarchy 层级 - 用于产生viewType
     */
    public MultiSelectNode(int hierarchy) {
        super(hierarchy);
    }

    /**
     * 事件处理方法
     *
     * @param event 事件
     */
    @Override
    public void onEvent(MultiSelectEvent event) {
        switch (event.getNotifyType()) {
            case TreeNodeEvent.NOTIFY_CHILDREN:
                // 父节点通知子节点改变自身状态
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

    /**
     * 兄弟节点关闭扩展
     *
     * @param isExpand 是否扩展
     */
    public void setOtherGroupsExpand(boolean isExpand) {
        MultiSelectEvent event = new MultiSelectEvent(MultiSelectEvent.EVENT_SET_EXPAND);
        event.setExpand(isExpand);
        notifyBrother(event);
    }

    /**
     * 父节点重新检查选择状态
     * 注：选择具有递归性，如果父类状态有变会继续通知父类
     */
    public void setParentRecheckSelected() {
        MultiSelectEvent event = new MultiSelectEvent(MultiSelectEvent.EVENT_SET_SELECTED);
        notifyParent(event);
    }

    /**
     * 设置子节点的选择状态
     * 注：选择具有递归性，会设置所有孩子以及孩子的孩子状态
     *
     * @param isSelected 是否选择
     */
    public void setChildrenSelected(boolean isSelected) {
        MultiSelectEvent event = new MultiSelectEvent(MultiSelectEvent.EVENT_SET_SELECTED);
        event.setSelected(isSelected);
        notifyChildren(event);
    }

    /**
     * 根据子节点设置自身状态
     *
     * @return isSelected boolean
     */
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
