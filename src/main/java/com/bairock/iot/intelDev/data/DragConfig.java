package com.bairock.iot.intelDev.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DragConfig {

    //组id
    @Id
    private String devGroupId;
    // 组态页面背景图路径
    private String dragViewBackgroundImagePath = "";
    @Column(columnDefinition = "int default 300", nullable = false)
    private int dragBackgroundWidth = 300;
    @Column(columnDefinition = "int default 300", nullable = false)
    private int dragBackgroundHeight = 300;

    public String getDevGroupId() {
        return devGroupId;
    }

    public void setDevGroupId(String devGroupId) {
        this.devGroupId = devGroupId;
    }

    public String getDragViewBackgroundImagePath() {
        return dragViewBackgroundImagePath;
    }

    public void setDragViewBackgroundImagePath(String dragViewBackgroundImagePath) {
        this.dragViewBackgroundImagePath = dragViewBackgroundImagePath;
    }

    public int getDragBackgroundWidth() {
        return dragBackgroundWidth;
    }

    public void setDragBackgroundWidth(int dragBackgroundWidth) {
        this.dragBackgroundWidth = dragBackgroundWidth;
    }

    public int getDragBackgroundHeight() {
        return dragBackgroundHeight;
    }

    public void setDragBackgroundHeight(int dragBackgroundHeight) {
        this.dragBackgroundHeight = dragBackgroundHeight;
    }
}
