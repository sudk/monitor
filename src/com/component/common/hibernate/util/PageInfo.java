package com.component.common.hibernate.util;

import java.io.Serializable;


/**
 * ��ҳ��Ϣ���󣬼�¼��ǰ��ҳ����������Ϣ����һҳ��������ǰҳ�롢�ܼ�¼����
 * UserBase: mocca
 * Date: 2006-4-12
 * Time: 15:58:06
 */
public class PageInfo  implements Serializable {
    private static final long serialVersionUID = 111111111L;

    /**
     * ҳ�����Ƴ���
     */
    public static final String PAGE_NO = "pageNO";
    /**
     * ҳ����Ϣ���Ƴ���
     */
    public static final String PAGE_INFO = "pageInfo";

    private int pageSize = 20 ;//Integer.parseInt(SystemProperties.get("default.pagesize","20"));

    private int pageNO = 1;

    private int totalPageNo;

    private int totalRecordNo;

    /**
     * ȡ��ҳ��������С����һҳ��������¼��
     * @return ҳ������
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * ����ҳ����������һҳ��������¼��
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        if(pageSize!=0){
            this.pageSize = pageSize;
        }
    }

    /**
     * ȡ�õ�ǰҳ��
     * @return ��ǰҳ��
     */
    public int getPageNO() {
        return pageNO;
    }

    /**
     * ���õ�ǰҪ��ʾ��ҳ��
     * @param pageNO ҳ��
     */
    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    /**
     * ȡ����ҳ��
     * @return ��ҳ��
     */
    public int getTotalPageNo() {
        if(this.totalRecordNo%this.pageSize==0){
            return this.totalRecordNo/this.pageSize;
        }else{
            return this.totalRecordNo/this.pageSize+1;
        }

    }


    /**
     * ȡ���ܼ�¼��
     * @return �ܼ�¼��
     */
    public int getTotalRecordNo() {
        return totalRecordNo;
    }

    /**
     * �����ܼ�¼��
     * @param totalRecordNo �ܼ�¼��
     */
    public void setTotalRecordNo(int totalRecordNo) {
        this.totalRecordNo = totalRecordNo;
    }

    /**
     * ȡ�õ�ǰ��ҳ������Ϣ
     * @return   ��ҳ������Ϣ�����ַ�����ʽ����
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
