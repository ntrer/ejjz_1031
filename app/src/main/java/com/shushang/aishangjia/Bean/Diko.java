package com.shushang.aishangjia.Bean;

import java.io.Serializable;
import java.util.List;

public class Diko {

    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"orderId":"402880b766236a050166239854f00003","customerId":null,"customerName":"jn","customerPhone":"18637280068","address":null,"shengCode":null,"shengName":null,"shiCode":null,"shiName":null,"quCode":null,"quName":null,"customerMerchantId":"402880b7653b275e01653b69cc770050","customerMerchantName":"数尚地板中华路","customerMerchantCode":"ssdb","customerManagerId":"402880b7654294c5016546d90f3800c7","customerManagerName":"刘德华","orderManagerId":"402880b7654294c5016546d90f3800c7","orderManagerName":"刘德华","orderMerchantId":"402880b7653b275e01653b69cc770050","orderMerchantCode":"ssdb","orderMerchantName":"数尚地板中华路","status":"0","totalPrice":22,"activityId":null,"chuangjianren":"402880b7654294c5016546d90f3800c7","xiugairen":"402880b7654294c5016546d90f3800c7","cjsj":1538195478000,"xgsj":1538195478000,"del":"0","enable":"1","xdsj":1538195478000,"source":"0","tailMoney":null,"activityName":null}]
     * intcurrentPage : 0
     * intpageSize : 0
     * intmaxCount : 0
     * intmaxPage : 0
     */

    private String ret;
    private String msg;
    private Object data;
    private int intcurrentPage;
    private int intpageSize;
    private int intmaxCount;
    private int intmaxPage;
    private List<DataListBean> dataList;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getIntcurrentPage() {
        return intcurrentPage;
    }

    public void setIntcurrentPage(int intcurrentPage) {
        this.intcurrentPage = intcurrentPage;
    }

    public int getIntpageSize() {
        return intpageSize;
    }

    public void setIntpageSize(int intpageSize) {
        this.intpageSize = intpageSize;
    }

    public int getIntmaxCount() {
        return intmaxCount;
    }

    public void setIntmaxCount(int intmaxCount) {
        this.intmaxCount = intmaxCount;
    }

    public int getIntmaxPage() {
        return intmaxPage;
    }

    public void setIntmaxPage(int intmaxPage) {
        this.intmaxPage = intmaxPage;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean implements Serializable {
        /**
         * orderId : 402880b766236a050166239854f00003
         * customerId : null
         * customerName : jn
         * customerPhone : 18637280068
         * address : null
         * shengCode : null
         * shengName : null
         * shiCode : null
         * shiName : null
         * quCode : null
         * quName : null
         * customerMerchantId : 402880b7653b275e01653b69cc770050
         * customerMerchantName : 数尚地板中华路
         * customerMerchantCode : ssdb
         * customerManagerId : 402880b7654294c5016546d90f3800c7
         * customerManagerName : 刘德华
         * orderManagerId : 402880b7654294c5016546d90f3800c7
         * orderManagerName : 刘德华
         * orderMerchantId : 402880b7653b275e01653b69cc770050
         * orderMerchantCode : ssdb
         * orderMerchantName : 数尚地板中华路
         * status : 0
         * totalPrice : 22.0
         * activityId : null
         * chuangjianren : 402880b7654294c5016546d90f3800c7
         * xiugairen : 402880b7654294c5016546d90f3800c7
         * cjsj : 1538195478000
         * xgsj : 1538195478000
         * del : 0
         * enable : 1
         * xdsj : 1538195478000
         * source : 0
         * tailMoney : null
         * activityName : null
         */

        private String orderId;
        private Object customerId;
        private String customerName;
        private String customerPhone;
        private Object address;
        private Object shengCode;
        private Object shengName;
        private Object shiCode;
        private Object shiName;
        private Object quCode;
        private Object quName;
        private String customerMerchantId;
        private String customerMerchantName;
        private String customerMerchantCode;
        private String customerManagerId;
        private String customerManagerName;
        private String orderManagerId;
        private String orderManagerName;
        private String orderMerchantId;
        private String orderMerchantCode;
        private String orderMerchantName;
        private String status;
        private double totalPrice;
        private Object activityId;
        private String chuangjianren;
        private String xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String enable;
        private long xdsj;
        private String source;
        private Object tailMoney;
        private Object activityName;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Object getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Object customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getShengCode() {
            return shengCode;
        }

        public void setShengCode(Object shengCode) {
            this.shengCode = shengCode;
        }

        public Object getShengName() {
            return shengName;
        }

        public void setShengName(Object shengName) {
            this.shengName = shengName;
        }

        public Object getShiCode() {
            return shiCode;
        }

        public void setShiCode(Object shiCode) {
            this.shiCode = shiCode;
        }

        public Object getShiName() {
            return shiName;
        }

        public void setShiName(Object shiName) {
            this.shiName = shiName;
        }

        public Object getQuCode() {
            return quCode;
        }

        public void setQuCode(Object quCode) {
            this.quCode = quCode;
        }

        public Object getQuName() {
            return quName;
        }

        public void setQuName(Object quName) {
            this.quName = quName;
        }

        public String getCustomerMerchantId() {
            return customerMerchantId;
        }

        public void setCustomerMerchantId(String customerMerchantId) {
            this.customerMerchantId = customerMerchantId;
        }

        public String getCustomerMerchantName() {
            return customerMerchantName;
        }

        public void setCustomerMerchantName(String customerMerchantName) {
            this.customerMerchantName = customerMerchantName;
        }

        public String getCustomerMerchantCode() {
            return customerMerchantCode;
        }

        public void setCustomerMerchantCode(String customerMerchantCode) {
            this.customerMerchantCode = customerMerchantCode;
        }

        public String getCustomerManagerId() {
            return customerManagerId;
        }

        public void setCustomerManagerId(String customerManagerId) {
            this.customerManagerId = customerManagerId;
        }

        public String getCustomerManagerName() {
            return customerManagerName;
        }

        public void setCustomerManagerName(String customerManagerName) {
            this.customerManagerName = customerManagerName;
        }

        public String getOrderManagerId() {
            return orderManagerId;
        }

        public void setOrderManagerId(String orderManagerId) {
            this.orderManagerId = orderManagerId;
        }

        public String getOrderManagerName() {
            return orderManagerName;
        }

        public void setOrderManagerName(String orderManagerName) {
            this.orderManagerName = orderManagerName;
        }

        public String getOrderMerchantId() {
            return orderMerchantId;
        }

        public void setOrderMerchantId(String orderMerchantId) {
            this.orderMerchantId = orderMerchantId;
        }

        public String getOrderMerchantCode() {
            return orderMerchantCode;
        }

        public void setOrderMerchantCode(String orderMerchantCode) {
            this.orderMerchantCode = orderMerchantCode;
        }

        public String getOrderMerchantName() {
            return orderMerchantName;
        }

        public void setOrderMerchantName(String orderMerchantName) {
            this.orderMerchantName = orderMerchantName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Object getActivityId() {
            return activityId;
        }

        public void setActivityId(Object activityId) {
            this.activityId = activityId;
        }

        public String getChuangjianren() {
            return chuangjianren;
        }

        public void setChuangjianren(String chuangjianren) {
            this.chuangjianren = chuangjianren;
        }

        public String getXiugairen() {
            return xiugairen;
        }

        public void setXiugairen(String xiugairen) {
            this.xiugairen = xiugairen;
        }

        public long getCjsj() {
            return cjsj;
        }

        public void setCjsj(long cjsj) {
            this.cjsj = cjsj;
        }

        public long getXgsj() {
            return xgsj;
        }

        public void setXgsj(long xgsj) {
            this.xgsj = xgsj;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getEnable() {
            return enable;
        }

        public void setEnable(String enable) {
            this.enable = enable;
        }

        public long getXdsj() {
            return xdsj;
        }

        public void setXdsj(long xdsj) {
            this.xdsj = xdsj;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Object getTailMoney() {
            return tailMoney;
        }

        public void setTailMoney(Object tailMoney) {
            this.tailMoney = tailMoney;
        }

        public Object getActivityName() {
            return activityName;
        }

        public void setActivityName(Object activityName) {
            this.activityName = activityName;
        }
    }
}
