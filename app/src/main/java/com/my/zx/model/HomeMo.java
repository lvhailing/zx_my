package com.my.zx.model;

import com.my.zx.db.afinal.annotation.sqlite.Id;
import com.my.zx.db.afinal.annotation.sqlite.Table;

import java.io.Serializable;


@Table(name = "tb_home")
public class HomeMo implements Serializable {
    @Id
    private int _id;

    private long id;            // 11000

    private String itemName;    // "通讯录"
    private String href;        // "http://www.baidu.com"
    private boolean hasNum;    // false
    private int taskNum;        // 0
    private int toWhere;        // 0: 代表打开的是列表；    1: 代表打开的是H5网页；

    public HomeMo() {
        super();
    }

    public HomeMo(long id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public HomeMo(long id, String itemName, boolean hasNum, int taskNum, int toWhere, String href) {
        this.id = id;
        this.itemName = itemName;
        this.href = href;
        this.hasNum = hasNum;
        this.taskNum = taskNum;
        this.toWhere = toWhere;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getToWhere() {
        return toWhere;
    }

    public void setToWhere(int toWhere) {
        this.toWhere = toWhere;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getItemName() {
        return itemName;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getHref() {
        return href;
    }


    public void setHref(String href) {
        this.href = href;
    }


    public boolean isHasNum() {
        return hasNum;
    }


    public void setHasNum(boolean hasNum) {
        this.hasNum = hasNum;
    }


    public int getTaskNum() {
        return taskNum;
    }


    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

}
