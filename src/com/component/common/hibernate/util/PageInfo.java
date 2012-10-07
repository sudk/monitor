package com.component.common.hibernate.util;

import java.io.Serializable;


/**
 * 翻页信息对象，记录当前翻页主题的相关信息，如一页容量、当前页码、总记录数等
 * UserBase: mocca
 * Date: 2006-4-12
 * Time: 15:58:06
 */
public class PageInfo  implements Serializable {
    private static final long serialVersionUID = 111111111L;

    /**
     * 页码名称常量
     */
    public static final String PAGE_NO = "pageNO";
    /**
     * 页面信息名称常量
     */
    public static final String PAGE_INFO = "pageInfo";

    private int pageSize = 20 ;//Integer.parseInt(SystemProperties.get("default.pagesize","20"));

    private int pageNO = 1;

    private int totalPageNo;

    private int totalRecordNo;

    /**
     * 取得页的容量大小，即一页容纳最多记录数
     * @return 页的容量
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页的容量，即一页容纳最多记录数
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        if(pageSize!=0){
            this.pageSize = pageSize;
        }
    }

    /**
     * 取得当前页码
     * @return 当前页码
     */
    public int getPageNO() {
        return pageNO;
    }

    /**
     * 设置当前要显示的页码
     * @param pageNO 页码
     */
    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    /**
     * 取得总页数
     * @return 总页数
     */
    public int getTotalPageNo() {
        if(this.totalRecordNo%this.pageSize==0){
            return this.totalRecordNo/this.pageSize;
        }else{
            return this.totalRecordNo/this.pageSize+1;
        }

    }


    /**
     * 取得总记录数
     * @return 总记录数
     */
    public int getTotalRecordNo() {
        return totalRecordNo;
    }

    /**
     * 设置总记录数
     * @param totalRecordNo 总记录数
     */
    public void setTotalRecordNo(int totalRecordNo) {
        this.totalRecordNo = totalRecordNo;
    }

    /**
     * 取得当前翻页对象信息
     * @return   翻页对象信息，以字符串形式返回
     */
    public String toString() {
        return "PageInfo{" +
                "pageSize=" + pageSize +
                ", pageNO=" + pageNO +
                ", totalPageNo=" + getTotalPageNo() +
                ", totalRecordNo=" + totalRecordNo +
                '}';
    }
}
