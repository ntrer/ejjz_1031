package com.shushang.aishangjia.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YD on 2018/8/6.
 */

public class NewPeople implements Serializable {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"type":"4","activityId":null,"activityCode":null,"activityName":null,"username":"ok","phone":"11111111111","sex":"未知","address":null,"sheng_code":null,"shi_code":null,"qu_code":null,"sheng_name":null,"shi_name":null,"qu_name":null,"decorationProgress":null,"decorationStyle":null,"likecolor":null,"thinkBuyGood":null,"merchantName":"数尚地板中华路","merchantId":"402880b7653b275e01653b69cc770050","cjsj":"2018-10-07 12:39:47","cardNum":"402880b7664bfdd101664cd2fc170009","customerActionId":"402880b7664bfdd101664cd2fc18000a"},{"type":"4","activityId":null,"activityCode":null,"activityName":null,"username":"jj","phone":"16666666666","sex":"未知","address":null,"sheng_code":null,"shi_code":null,"qu_code":null,"sheng_name":null,"shi_name":null,"qu_name":null,"decorationProgress":null,"decorationStyle":null,"likecolor":null,"thinkBuyGood":null,"merchantName":"数尚地板中华路","merchantId":"402880b7653b275e01653b69cc770050","cjsj":"2018-10-07 12:19:36","cardNum":"402880b7664bfdd101664cc0805a0005","customerActionId":"402880b7664bfdd101664cc0805a0006"},{"type":"4","activityId":null,"activityCode":null,"activityName":null,"username":"hj","phone":"15555555555","sex":"未知","address":null,"sheng_code":null,"shi_code":null,"qu_code":null,"sheng_name":null,"shi_name":null,"qu_name":null,"decorationProgress":null,"decorationStyle":null,"likecolor":null,"thinkBuyGood":null,"merchantName":"数尚地板中华路","merchantId":"402880b7653b275e01653b69cc770050","cjsj":"2018-10-07 12:19:03","cardNum":"402880b7664bfdd101664cbffdb40003","customerActionId":"402880b7664bfdd101664cbffdc90004"}]
     * intcurrentPage : 1
     * intpageSize : 10
     * intmaxCount : 3
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

    public static class DataListBean implements Serializable{
        /**
         * type : 4
         * activityId : null
         * activityCode : null
         * activityName : null
         * username : ok
         * phone : 11111111111
         * sex : 未知
         * address : null
         * sheng_code : null
         * shi_code : null
         * qu_code : null
         * sheng_name : null
         * shi_name : null
         * qu_name : null
         * decorationProgress : null
         * decorationStyle : null
         * likecolor : null
         * thinkBuyGood : null
         * merchantName : 数尚地板中华路
         * merchantId : 402880b7653b275e01653b69cc770050
         * cjsj : 2018-10-07 12:39:47
         * cardNum : 402880b7664bfdd101664cd2fc170009
         * customerActionId : 402880b7664bfdd101664cd2fc18000a
         */

        private String type;
        private Object activityId;
        private Object activityCode;
        private Object activityName;
        private String username;
        private String phone;
        private String sex;
        private Object address;
        private Object sheng_code;
        private Object shi_code;
        private Object qu_code;
        private Object sheng_name;
        private Object shi_name;
        private Object qu_name;
        private Object decorationProgress;
        private Object decorationStyle;
        private Object likecolor;
        private Object thinkBuyGood;
        private String merchantName;
        private String merchantId;
        private String cjsj;
        private String cardNum;
        private String customerActionId;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getActivityId() {
            return activityId;
        }

        public void setActivityId(Object activityId) {
            this.activityId = activityId;
        }

        public Object getActivityCode() {
            return activityCode;
        }

        public void setActivityCode(Object activityCode) {
            this.activityCode = activityCode;
        }

        public Object getActivityName() {
            return activityName;
        }

        public void setActivityName(Object activityName) {
            this.activityName = activityName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getSheng_code() {
            return sheng_code;
        }

        public void setSheng_code(Object sheng_code) {
            this.sheng_code = sheng_code;
        }

        public Object getShi_code() {
            return shi_code;
        }

        public void setShi_code(Object shi_code) {
            this.shi_code = shi_code;
        }

        public Object getQu_code() {
            return qu_code;
        }

        public void setQu_code(Object qu_code) {
            this.qu_code = qu_code;
        }

        public Object getSheng_name() {
            return sheng_name;
        }

        public void setSheng_name(Object sheng_name) {
            this.sheng_name = sheng_name;
        }

        public Object getShi_name() {
            return shi_name;
        }

        public void setShi_name(Object shi_name) {
            this.shi_name = shi_name;
        }

        public Object getQu_name() {
            return qu_name;
        }

        public void setQu_name(Object qu_name) {
            this.qu_name = qu_name;
        }

        public Object getDecorationProgress() {
            return decorationProgress;
        }

        public void setDecorationProgress(Object decorationProgress) {
            this.decorationProgress = decorationProgress;
        }

        public Object getDecorationStyle() {
            return decorationStyle;
        }

        public void setDecorationStyle(Object decorationStyle) {
            this.decorationStyle = decorationStyle;
        }

        public Object getLikecolor() {
            return likecolor;
        }

        public void setLikecolor(Object likecolor) {
            this.likecolor = likecolor;
        }

        public Object getThinkBuyGood() {
            return thinkBuyGood;
        }

        public void setThinkBuyGood(Object thinkBuyGood) {
            this.thinkBuyGood = thinkBuyGood;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getCjsj() {
            return cjsj;
        }

        public void setCjsj(String cjsj) {
            this.cjsj = cjsj;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getCustomerActionId() {
            return customerActionId;
        }

        public void setCustomerActionId(String customerActionId) {
            this.customerActionId = customerActionId;
        }
    }
}
