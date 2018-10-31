package com.shushang.aishangjia.Bean;

import java.util.List;

public class UserData {

    private DataBean data;
    private int intcurrentPage;
    private int intmaxCount;
    private int intmaxPage;
    private int intpageSize;
    private String msg;
    private String ret;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getIntcurrentPage() {
        return intcurrentPage;
    }

    public void setIntcurrentPage(int intcurrentPage) {
        this.intcurrentPage = intcurrentPage;
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

    public int getIntpageSize() {
        return intpageSize;
    }

    public void setIntpageSize(int intpageSize) {
        this.intpageSize = intpageSize;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public static class DataBean {
        private ActivityBean activity;
        private List<ActionCustomersBean> actionCustomers;
        private List<CustomersBean> customers;

        public ActivityBean getActivity() {
            return activity;
        }

        public void setActivity(ActivityBean activity) {
            this.activity = activity;
        }

        public List<ActionCustomersBean> getActionCustomers() {
            return actionCustomers;
        }

        public void setActionCustomers(List<ActionCustomersBean> actionCustomers) {
            this.actionCustomers = actionCustomers;
        }

        public List<CustomersBean> getCustomers() {
            return customers;
        }

        public void setCustomers(List<CustomersBean> customers) {
            this.customers = customers;
        }

//        public static class ActivityBean {
//            private String active;
//            private int activityCode;
//            private String activityId;
//            private String activityName;
//            private String chuangjianren;
//            private long cjsj;
//            private String coverImageId;
//            private String del;
//            private long eventEnd;
//            private long eventStart;
//            private String isUnderLine;
//            private String merchantId;
//            private String qdjpname;
//            private long sceneEnd;
//            private long sceneStart;
//            private long sellCardEnd;
//            private long sellCardStart;
//            private String shengCode;
//            private String shengName;
//            private String shiCode;
//            private String shiName;
//            private long xgsj;
//            private String xiugairen;
//            private String yxjpname;
//
//            public String getActive() {
//                return active;
//            }
//
//            public void setActive(String active) {
//                this.active = active;
//            }
//
//            public int getActivityCode() {
//                return activityCode;
//            }
//
//            public void setActivityCode(int activityCode) {
//                this.activityCode = activityCode;
//            }
//
//            public String getActivityId() {
//                return activityId;
//            }
//
//            public void setActivityId(String activityId) {
//                this.activityId = activityId;
//            }
//
//            public String getActivityName() {
//                return activityName;
//            }
//
//            public void setActivityName(String activityName) {
//                this.activityName = activityName;
//            }
//
//            public String getChuangjianren() {
//                return chuangjianren;
//            }
//
//            public void setChuangjianren(String chuangjianren) {
//                this.chuangjianren = chuangjianren;
//            }
//
//            public long getCjsj() {
//                return cjsj;
//            }
//
//            public void setCjsj(long cjsj) {
//                this.cjsj = cjsj;
//            }
//
//            public String getCoverImageId() {
//                return coverImageId;
//            }
//
//            public void setCoverImageId(String coverImageId) {
//                this.coverImageId = coverImageId;
//            }
//
//            public String getDel() {
//                return del;
//            }
//
//            public void setDel(String del) {
//                this.del = del;
//            }
//
//            public long getEventEnd() {
//                return eventEnd;
//            }
//
//            public void setEventEnd(long eventEnd) {
//                this.eventEnd = eventEnd;
//            }
//
//            public long getEventStart() {
//                return eventStart;
//            }
//
//            public void setEventStart(long eventStart) {
//                this.eventStart = eventStart;
//            }
//
//            public String getIsUnderLine() {
//                return isUnderLine;
//            }
//
//            public void setIsUnderLine(String isUnderLine) {
//                this.isUnderLine = isUnderLine;
//            }
//
//            public String getMerchantId() {
//                return merchantId;
//            }
//
//            public void setMerchantId(String merchantId) {
//                this.merchantId = merchantId;
//            }
//
//            public String getQdjpname() {
//                return qdjpname;
//            }
//
//            public void setQdjpname(String qdjpname) {
//                this.qdjpname = qdjpname;
//            }
//
//            public long getSceneEnd() {
//                return sceneEnd;
//            }
//
//            public void setSceneEnd(long sceneEnd) {
//                this.sceneEnd = sceneEnd;
//            }
//
//            public long getSceneStart() {
//                return sceneStart;
//            }
//
//            public void setSceneStart(long sceneStart) {
//                this.sceneStart = sceneStart;
//            }
//
//            public long getSellCardEnd() {
//                return sellCardEnd;
//            }
//
//            public void setSellCardEnd(long sellCardEnd) {
//                this.sellCardEnd = sellCardEnd;
//            }
//
//            public long getSellCardStart() {
//                return sellCardStart;
//            }
//
//            public void setSellCardStart(long sellCardStart) {
//                this.sellCardStart = sellCardStart;
//            }
//
//            public String getShengCode() {
//                return shengCode;
//            }
//
//            public void setShengCode(String shengCode) {
//                this.shengCode = shengCode;
//            }
//
//            public String getShengName() {
//                return shengName;
//            }
//
//            public void setShengName(String shengName) {
//                this.shengName = shengName;
//            }
//
//            public String getShiCode() {
//                return shiCode;
//            }
//
//            public void setShiCode(String shiCode) {
//                this.shiCode = shiCode;
//            }
//
//            public String getShiName() {
//                return shiName;
//            }
//
//            public void setShiName(String shiName) {
//                this.shiName = shiName;
//            }
//
//            public long getXgsj() {
//                return xgsj;
//            }
//
//            public void setXgsj(long xgsj) {
//                this.xgsj = xgsj;
//            }
//
//            public String getXiugairen() {
//                return xiugairen;
//            }
//
//            public void setXiugairen(String xiugairen) {
//                this.xiugairen = xiugairen;
//            }
//
//            public String getYxjpname() {
//                return yxjpname;
//            }
//
//            public void setYxjpname(String yxjpname) {
//                this.yxjpname = yxjpname;
//            }
//        }

//        public  class ActionCustomersBean {
//            private long id;
//            private int activityCode;
//            private String activityId;
//            private String activityName;
//            private String address;
//            private String cardNum;
//            private String chuangjianren;
//            private long cjsj;
//            private String customerActionId;
//            private String customerMobile;
//            private String customerName;
//            private String customerSex;
//            private String customersource;
//            private String decorationProgress;
//            private String decorationStyle;
//            private String del;
//            private String managerAcount;
//            private String managerId;
//            private String managerRealName;
//            private String merchantCode;
//            private String merchantId;
//            private String merchantName;
//            private String qdsucess;
//            private String quCode;
//            private String quName;
//            private String shengCode;
//            private String shengName;
//            private String shiCode;
//            private String shiName;
//            private String status;
//            private String thinkBuyGood;
//            private long xgsj;
//            private String xiugairen;
//            private long qdsj;
//            private long lqsj;
//            private String lqsuccess;
//
//            public int getActivityCode() {
//                return activityCode;
//            }
//
//            public void setActivityCode(int activityCode) {
//                this.activityCode = activityCode;
//            }
//
//            public String getActivityId() {
//                return activityId;
//            }
//
//            public void setActivityId(String activityId) {
//                this.activityId = activityId;
//            }
//
//            public String getActivityName() {
//                return activityName;
//            }
//
//            public void setActivityName(String activityName) {
//                this.activityName = activityName;
//            }
//
//            public String getAddress() {
//                return address;
//            }
//
//            public void setAddress(String address) {
//                this.address = address;
//            }
//
//            public String getCardNum() {
//                return cardNum;
//            }
//
//            public void setCardNum(String cardNum) {
//                this.cardNum = cardNum;
//            }
//
//            public String getChuangjianren() {
//                return chuangjianren;
//            }
//
//            public void setChuangjianren(String chuangjianren) {
//                this.chuangjianren = chuangjianren;
//            }
//
//            public long getCjsj() {
//                return cjsj;
//            }
//
//            public void setCjsj(long cjsj) {
//                this.cjsj = cjsj;
//            }
//
//            public String getCustomerActionId() {
//                return customerActionId;
//            }
//
//            public void setCustomerActionId(String customerActionId) {
//                this.customerActionId = customerActionId;
//            }
//
//            public String getCustomerMobile() {
//                return customerMobile;
//            }
//
//            public void setCustomerMobile(String customerMobile) {
//                this.customerMobile = customerMobile;
//            }
//
//            public String getCustomerName() {
//                return customerName;
//            }
//
//            public void setCustomerName(String customerName) {
//                this.customerName = customerName;
//            }
//
//            public String getCustomerSex() {
//                return customerSex;
//            }
//
//            public void setCustomerSex(String customerSex) {
//                this.customerSex = customerSex;
//            }
//
//            public String getCustomersource() {
//                return customersource;
//            }
//
//            public void setCustomersource(String customersource) {
//                this.customersource = customersource;
//            }
//
//            public String getDecorationProgress() {
//                return decorationProgress;
//            }
//
//            public void setDecorationProgress(String decorationProgress) {
//                this.decorationProgress = decorationProgress;
//            }
//
//            public String getDecorationStyle() {
//                return decorationStyle;
//            }
//
//            public void setDecorationStyle(String decorationStyle) {
//                this.decorationStyle = decorationStyle;
//            }
//
//            public String getDel() {
//                return del;
//            }
//
//            public void setDel(String del) {
//                this.del = del;
//            }
//
//            public String getManagerAcount() {
//                return managerAcount;
//            }
//
//            public void setManagerAcount(String managerAcount) {
//                this.managerAcount = managerAcount;
//            }
//
//            public String getManagerId() {
//                return managerId;
//            }
//
//            public void setManagerId(String managerId) {
//                this.managerId = managerId;
//            }
//
//            public String getManagerRealName() {
//                return managerRealName;
//            }
//
//            public void setManagerRealName(String managerRealName) {
//                this.managerRealName = managerRealName;
//            }
//
//            public String getMerchantCode() {
//                return merchantCode;
//            }
//
//            public void setMerchantCode(String merchantCode) {
//                this.merchantCode = merchantCode;
//            }
//
//            public String getMerchantId() {
//                return merchantId;
//            }
//
//            public void setMerchantId(String merchantId) {
//                this.merchantId = merchantId;
//            }
//
//            public String getMerchantName() {
//                return merchantName;
//            }
//
//            public void setMerchantName(String merchantName) {
//                this.merchantName = merchantName;
//            }
//
//            public String getQdsucess() {
//                return qdsucess;
//            }
//
//            public void setQdsucess(String qdsucess) {
//                this.qdsucess = qdsucess;
//            }
//
//            public String getQuCode() {
//                return quCode;
//            }
//
//            public void setQuCode(String quCode) {
//                this.quCode = quCode;
//            }
//
//            public String getQuName() {
//                return quName;
//            }
//
//            public void setQuName(String quName) {
//                this.quName = quName;
//            }
//
//            public String getShengCode() {
//                return shengCode;
//            }
//
//            public void setShengCode(String shengCode) {
//                this.shengCode = shengCode;
//            }
//
//            public String getShengName() {
//                return shengName;
//            }
//
//            public void setShengName(String shengName) {
//                this.shengName = shengName;
//            }
//
//            public String getShiCode() {
//                return shiCode;
//            }
//
//            public void setShiCode(String shiCode) {
//                this.shiCode = shiCode;
//            }
//
//            public String getShiName() {
//                return shiName;
//            }
//
//            public void setShiName(String shiName) {
//                this.shiName = shiName;
//            }
//
//            public String getStatus() {
//                return status;
//            }
//
//            public void setStatus(String status) {
//                this.status = status;
//            }
//
//            public String getThinkBuyGood() {
//                return thinkBuyGood;
//            }
//
//            public void setThinkBuyGood(String thinkBuyGood) {
//                this.thinkBuyGood = thinkBuyGood;
//            }
//
//            public long getXgsj() {
//                return xgsj;
//            }
//
//            public void setXgsj(long xgsj) {
//                this.xgsj = xgsj;
//            }
//
//            public String getXiugairen() {
//                return xiugairen;
//            }
//
//            public void setXiugairen(String xiugairen) {
//                this.xiugairen = xiugairen;
//            }
//
//            public long getQdsj() {
//                return qdsj;
//            }
//
//            public void setQdsj(long qdsj) {
//                this.qdsj = qdsj;
//            }
//
//            public long getLqsj() {
//                return lqsj;
//            }
//
//            public void setLqsj(long lqsj) {
//                this.lqsj = lqsj;
//            }
//
//            public String getLqsuccess() {
//                return lqsuccess;
//            }
//
//            public void setLqsuccess(String lqsuccess) {
//                this.lqsuccess = lqsuccess;
//            }
//        }
//
//
//        public class CustomersBean {
//
//            private long id;
//            private String checkCode;
//            private String chuangjianren;
//            private long cjsj;
//            private int customerCode;
//            private String customerId;
//            private String customerMobile;
//            private String customerName;
//            private String del;
//            private long xgsj;
//            private String xiugairen;
//
//            public String getCheckCode() {
//                return checkCode;
//            }
//
//            public void setCheckCode(String checkCode) {
//                this.checkCode = checkCode;
//            }
//
//            public String getChuangjianren() {
//                return chuangjianren;
//            }
//
//            public void setChuangjianren(String chuangjianren) {
//                this.chuangjianren = chuangjianren;
//            }
//
//            public long getCjsj() {
//                return cjsj;
//            }
//
//            public void setCjsj(long cjsj) {
//                this.cjsj = cjsj;
//            }
//
//            public int getCustomerCode() {
//                return customerCode;
//            }
//
//            public void setCustomerCode(int customerCode) {
//                this.customerCode = customerCode;
//            }
//
//            public String getCustomerId() {
//                return customerId;
//            }
//
//            public void setCustomerId(String customerId) {
//                this.customerId = customerId;
//            }
//
//            public String getCustomerMobile() {
//                return customerMobile;
//            }
//
//            public void setCustomerMobile(String customerMobile) {
//                this.customerMobile = customerMobile;
//            }
//
//            public String getCustomerName() {
//                return customerName;
//            }
//
//            public void setCustomerName(String customerName) {
//                this.customerName = customerName;
//            }
//
//            public String getDel() {
//                return del;
//            }
//
//            public void setDel(String del) {
//                this.del = del;
//            }
//
//            public long getXgsj() {
//                return xgsj;
//            }
//
//            public void setXgsj(long xgsj) {
//                this.xgsj = xgsj;
//            }
//
//            public String getXiugairen() {
//                return xiugairen;
//            }
//
//            public void setXiugairen(String xiugairen) {
//                this.xiugairen = xiugairen;
//            }
//        }
    }
}
