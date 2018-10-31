package com.shushang.aishangjia.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CustomersBean  {
    @Id(autoincrement = true)
    private Long id;
    private String checkCode;
    private String chuangjianren;
    private long cjsj;
    private int customerCode;
    private String customerId;
    private String customerMobile;
    private String customerName;
    private String del;
    private long xgsj;
    private String xiugairen;
    @Generated(hash = 1897601722)
    public CustomersBean(Long id, String checkCode, String chuangjianren, long cjsj,
            int customerCode, String customerId, String customerMobile,
            String customerName, String del, long xgsj, String xiugairen) {
        this.id = id;
        this.checkCode = checkCode;
        this.chuangjianren = chuangjianren;
        this.cjsj = cjsj;
        this.customerCode = customerCode;
        this.customerId = customerId;
        this.customerMobile = customerMobile;
        this.customerName = customerName;
        this.del = del;
        this.xgsj = xgsj;
        this.xiugairen = xiugairen;
    }
    @Generated(hash = 805578431)
    public CustomersBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCheckCode() {
        return this.checkCode;
    }
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
    public String getChuangjianren() {
        return this.chuangjianren;
    }
    public void setChuangjianren(String chuangjianren) {
        this.chuangjianren = chuangjianren;
    }
    public long getCjsj() {
        return this.cjsj;
    }
    public void setCjsj(long cjsj) {
        this.cjsj = cjsj;
    }
    public int getCustomerCode() {
        return this.customerCode;
    }
    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }
    public String getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerMobile() {
        return this.customerMobile;
    }
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }
    public String getCustomerName() {
        return this.customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getDel() {
        return this.del;
    }
    public void setDel(String del) {
        this.del = del;
    }
    public long getXgsj() {
        return this.xgsj;
    }
    public void setXgsj(long xgsj) {
        this.xgsj = xgsj;
    }
    public String getXiugairen() {
        return this.xiugairen;
    }
    public void setXiugairen(String xiugairen) {
        this.xiugairen = xiugairen;
    }

}
