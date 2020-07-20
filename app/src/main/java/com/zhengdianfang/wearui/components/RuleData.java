package com.zhengdianfang.wearui.components;

public class RuleData {

    public static final int DEFAULT_COLOR = 0;

    private String title;
    private int color;
    private boolean isSelected;

    public RuleData() {
    }

    public RuleData(String title, int color, boolean isSelected) {
        this.title = title;
        this.color = color;
        this.isSelected = isSelected;
    }

    public RuleData(String title, boolean isSelected) {
        this(title, DEFAULT_COLOR, isSelected);
    }

    public RuleData(String title) {
        this(title, DEFAULT_COLOR, false);
    }

    public RuleData(String title, int color) {
        this(title, color, false);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
