package com.restaurant.print;

import java.awt.print.Printable;

/**
 * Created by YUHB on 2016/8/29.
 */
public abstract class AbstractPrinter implements Printable {

    /**************************************
     * 返回文档高度
     * @return
     */
    public abstract int getHeight();

    /**************************************
     * 返回文档开始位置
     * @return
     */
    public int getStartX() {
        return 5;
    }
}
