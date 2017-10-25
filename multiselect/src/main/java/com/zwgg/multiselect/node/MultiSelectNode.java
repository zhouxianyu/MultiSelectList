package com.zwgg.multiselect.node;

import com.zwgg.multiselect.event.MultiSelectEvent;
import com.zwgg.multiselect.event.TreeNodeEvent;

/**
 * Class: MultiSelectNode
 * Author: ZhouWei
 * Date: 2017/10/17
 * Time: 14:55
 * 多层级复选树节点类 - 具有多选和展开功能，并可以添加viewModel
 * 注：选择具有递归性，而展开无递归性
 */

public class MultiSelectNode<T extends MultiSelectNode<T>> extends SimpleTreeNode<T, MultiSelectEvent> {

    private boolean isSelected;
    private boolean isExpand;

    /**
     * @param hierarchy 层级 - 用于产生viewType
     */
    public MultiSelectNode(int hierarchy) {
        super(hierarchy);
    }

    /**
     * 事件处理方法
     *
     * @param event 传递得到的事件
     */
    @Override
    public void onEvent(MultiSelectEvent event) {
        switch (event.getNotifyType()) {
            case TreeNodeEvent.NOTIFY_CHILDREN:
                // 父节点通知子节点改变选择状态
                if (event.getEventType() == MultiSelectEvent.EVENT_SET_SELECTED) {
                    // 如果子节点选择状态有变，则继续通知下层节点改变状态
                    if (event.isSelected() != isSelected()) {
                        setSelected(event.isSelected());
                        notifyChildren(event);
                    }
                }
                break;
            case TreeNodeEvent.NOTIFY_PARENT:
                // 子节点选择状态更改，则通知父节点重新根据所有子节点设置自身状态
                if (event.getEventType() == MultiSelectEvent.EVENT_SET_SELECTED) {
                    if (recheckSelected() != isSelected()) {
                        setSelected(!isSelected());
                        // 如果父节点有变，则继续递归通知
                        notifyParent(event);
                    }
                }
                break;
            case TreeNodeEvent.NOTIFY_BROTHER:
                // 通知兄弟节点改变扩展状态
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
     * 通知父节点根据子节点设置状态
     * 注：选择具有递归性，如果父类状态有变会继续通知父类
     */
    public void setParentRecheckSelected() {
        MultiSelectEvent event = new MultiSelectEvent(MultiSelectEvent.EVENT_SET_SELECTED);
        notifyParent(event);
    }

    /**
     * 通知子节点设置选择状态
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

}
