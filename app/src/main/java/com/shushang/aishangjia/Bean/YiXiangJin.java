package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/9/18.
 */

public class YiXiangJin {

    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"orderId":"402880b765ebd1260165ebd668110005","customerId":"402880b7653b275e01653cf534c001be","customerName":"枫林","customerPhone":"18637280068","address":"黑龙江省伊春市翠峦区","shengCode":"230000","shengName":"黑龙江省","shiCode":"230700","shiName":"伊春市","quCode":"230706","quName":"翠峦区","customerMerchantId":"402880b7653b275e01653b597605000f","customerMerchantName":"老板卫浴","customerMerchantCode":"lbwy","customerManagerId":"402880b7653b275e01653b68662b0041","customerManagerName":"yangdong","orderManagerId":"402880b7653b275e01653d0285c901cd","orderManagerName":"徐珊珊","orderMerchantId":"402880b7653b275e01653b69cc770050","orderMerchantCode":"ssdb","orderMerchantName":"数尚地板","status":"0","totalPrice":666,"activityId":"402880b7653b275e01653b94dd8a0079","chuangjianren":"b08872c21c784fc793df98b3149da578","xiugairen":"b08872c21c784fc793df98b3149da578","cjsj":1537260022000,"xgsj":1537260022000,"del":"0","enable":"1","xdsj":1537260022000,"source":"1","tailMoney":null,"activityName":"安阳万达618送大礼"},{"orderId":"402880b265ebcf130165ebcf51f30000","customerId":"402880b7653b275e01653cf534c001be","customerName":"枫林","customerPhone":"18637280068","address":"黑龙江省伊春市翠峦区","shengCode":"230000","shengName":"黑龙江省","shiCode":"230700","shiName":"伊春市","quCode":"230706","quName":"翠峦区","customerMerchantId":"402880b7653b275e01653b597605000f","customerMerchantName":"老板卫浴","customerMerchantCode":"lbwy","customerManagerId":"402880b7653b275e01653b68662b0041","customerManagerName":"yangdong","orderManagerId":"402880b7653b275e01653d0285c901cd","orderManagerName":"徐珊珊","orderMerchantId":"402880b7653b275e01653b69cc770050","orderMerchantCode":"ssdb","orderMerchantName":"数尚地板","status":"0","totalPrice":100.1,"activityId":"402880b7653b275e01653b94dd8a0079","chuangjianren":"b08872c21c784fc793df98b3149da578","xiugairen":"b08872c21c784fc793df98b3149da578","cjsj":1537259557000,"xgsj":1537259557000,"del":"0","enable":"1","xdsj":1537259557000,"source":"1","tailMoney":null,"activityName":"安阳万达618送大礼"}]
     * intcurrentPage : 1
     * intpageSize : 10
     * intmaxCount : 2
     * intmaxPage : 1
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

    public static class DataListBean {
        /**
         * orderId : 402880b765ebd1260165ebd668110005
         * customerId : 402880b7653b275e01653cf534c001be
         * customerName : 枫林
         * customerPhone : 18637280068
         * address : 黑龙江省伊春市翠峦区
         * shengCode : 230000
         * shengName : 黑龙江省
         * shiCode : 230700
         * shiName : 伊春市
         * quCode : 230706
         * quName : 翠峦区
         * customerMerchantId : 402880b7653b275e01653b597605000f
         * customerMerchantName : 老板卫浴
         * customerMerchantCode : lbwy
         * customerManagerId : 402880b7653b275e01653b68662b0041
         * customerManagerName : yangdong
         * orderManagerId : 402880b7653b275e01653d0285c901cd
         * orderManagerName : 徐珊珊
         * orderMerchantId : 402880b7653b275e01653b69cc770050
         * orderMerchantCode : ssdb
         * orderMerchantName : 数尚地板
         * status : 0
         * totalPrice : 666.0
         * activityId : 402880b7653b275e01653b94dd8a0079
         * chuangjianren : b08872c21c784fc793df98b3149da578
         * xiugairen : b08872c21c784fc793df98b3149da578
         * cjsj : 1537260022000
         * xgsj : 1537260022000
         * del : 0
         * enable : 1
         * xdsj : 1537260022000
         * source : 1
         * tailMoney : null
         * activityName : 安阳万达618送大礼
         */

        private String orderId;
        private String customerId;
        private String customerName;
        private String customerPhone;
        private String address;
        private String shengCode;
        private String shengName;
        private String shiCode;
        private String shiName;
        private String quCode;
        private String quName;
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
        private String activityId;
        private String chuangjianren;
        private String xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String enable;
        private long xdsj;
        private String source;
        private Object tailMoney;
        private String activityName;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShengCode() {
            return shengCode;
        }

        public void setShengCode(String shengCode) {
            this.shengCode = shengCode;
        }

        public String getShengName() {
            return shengName;
        }

        public void setShengName(String shengName) {
            this.shengName = shengName;
        }

        public String getShiCode() {
            return shiCode;
        }

        public void setShiCode(String shiCode) {
            this.shiCode = shiCode;
        }

        public String getShiName() {
            return shiName;
        }

        public void setShiName(String shiName) {
            this.shiName = shiName;
        }

        public String getQuCode() {
            return quCode;
        }

        public void setQuCode(String quCode) {
            this.quCode = quCode;
        }

        public String getQuName() {
            return quName;
        }

        public void setQuName(String quName) {
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

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
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

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }
    }
}
