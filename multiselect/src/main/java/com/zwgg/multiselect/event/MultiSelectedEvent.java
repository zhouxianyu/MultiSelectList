package com.zwgg.multiselect.event;

/**
 * Class: MultiSelectedEvent
 * Author: ZhouWei
 * Date: 2017/10/17
 * Time: 17:37
 */

public class MultiSelectedEvent extends TreeNodeEvent {

    public static final int EVENT_SET_SELECTED = 1;
    public static final int EVENT_SET_EXPAND = 2;

    private int eventType;
    private boolean isSelected;
    private boolean isExpand;

    public MultiSelectedEvent() {
    }

    public MultiSelectedEvent(int eventType){
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }
}
