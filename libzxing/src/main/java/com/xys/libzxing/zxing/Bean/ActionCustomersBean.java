package com.xys.libzxing.zxing.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ActionCustomersBean  {
            @Id(autoincrement = true)
            private Long id;
            private int activityCode;
            private String activityId;
            private String activityName;
            private String address;
            private String cardNum;
            private String chuangjianren;
            private long cjsj;
            private String customerActionId;
            private String customerMobile;
            private String customerName;
            private String customerSex;
            private String customersource;
            private String decorationProgress;
            private String decorationStyle;
            private String del;
            private String managerAcount;
            private String managerId;
            private String managerRealName;
            private String merchantCode;
            private String merchantId;
            private String merchantName;
            private String qdsucess;
            private String quCode;
            private String quName;
            private String shengCode;
            private String shengName;
            private String shiCode;
            private String shiName;
            private String status;
            private String thinkBuyGood;
            private long xgsj;
            private String xiugairen;
            private long qdsj;
            private long lqsj;
            private String lqsuccess;
            private String isSign;
            @Generated(hash = 448617752)
            public ActionCustomersBean(Long id, int activityCode, String activityId,
                    String activityName, String address, String cardNum,
                    String chuangjianren, long cjsj, String customerActionId,
                    String customerMobile, String customerName, String customerSex,
                    String customersource, String decorationProgress,
                    String decorationStyle, String del, String managerAcount,
                    String managerId, String managerRealName, String merchantCode,
                    String merchantId, String merchantName, String qdsucess, String quCode,
                    String quName, String shengCode, String shengName, String shiCode,
                    String shiName, String status, String thinkBuyGood, long xgsj,
                    String xiugairen, long qdsj, long lqsj, String lqsuccess,
                    String isSign) {
                this.id = id;
                this.activityCode = activityCode;
                this.activityId = activityId;
                this.activityName = activityName;
                this.address = address;
                this.cardNum = cardNum;
                this.chuangjianren = chuangjianren;
                this.cjsj = cjsj;
                this.customerActionId = customerActionId;
                this.customerMobile = customerMobile;
                this.customerName = customerName;
                this.customerSex = customerSex;
                this.customersource = customersource;
                this.decorationProgress = decorationProgress;
                this.decorationStyle = decorationStyle;
                this.del = del;
                this.managerAcount = managerAcount;
                this.managerId = managerId;
                this.managerRealName = managerRealName;
                this.merchantCode = merchantCode;
                this.merchantId = merchantId;
                this.merchantName = merchantName;
                this.qdsucess = qdsucess;
                this.quCode = quCode;
                this.quName = quName;
                this.shengCode = shengCode;
                this.shengName = shengName;
                this.shiCode = shiCode;
                this.shiName = shiName;
                this.status = status;
                this.thinkBuyGood = thinkBuyGood;
                this.xgsj = xgsj;
                this.xiugairen = xiugairen;
                this.qdsj = qdsj;
                this.lqsj = lqsj;
                this.lqsuccess = lqsuccess;
                this.isSign = isSign;
            }
            @Generated(hash = 1693199693)
            public ActionCustomersBean() {
            }
            public Long getId() {
                return this.id;
            }
            public void setId(Long id) {
                this.id = id;
            }
            public int getActivityCode() {
                return this.activityCode;
            }
            public void setActivityCode(int activityCode) {
                this.activityCode = activityCode;
            }
            public String getActivityId() {
                return this.activityId;
            }
            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }
            public String getActivityName() {
                return this.activityName;
            }
            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }
            public String getAddress() {
                return this.address;
            }
            public void setAddress(String address) {
                this.address = address;
            }
            public String getCardNum() {
                return this.cardNum;
            }
            public void setCardNum(String cardNum) {
                this.cardNum = cardNum;
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
            public String getCustomerActionId() {
                return this.customerActionId;
            }
            public void setCustomerActionId(String customerActionId) {
                this.customerActionId = customerActionId;
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
            public String getCustomerSex() {
                return this.customerSex;
            }
            public void setCustomerSex(String customerSex) {
                this.customerSex = customerSex;
            }
            public String getCustomersource() {
                return this.customersource;
            }
            public void setCustomersource(String customersource) {
                this.customersource = customersource;
            }
            public String getDecorationProgress() {
                return this.decorationProgress;
            }
            public void setDecorationProgress(String decorationProgress) {
                this.decorationProgress = decorationProgress;
            }
            public String getDecorationStyle() {
                return this.decorationStyle;
            }
            public void setDecorationStyle(String decorationStyle) {
                this.decorationStyle = decorationStyle;
            }
            public String getDel() {
                return this.del;
            }
            public void setDel(String del) {
                this.del = del;
            }
            public String getManagerAcount() {
                return this.managerAcount;
            }
            public void setManagerAcount(String managerAcount) {
                this.managerAcount = managerAcount;
            }
            public String getManagerId() {
                return this.managerId;
            }
            public void setManagerId(String managerId) {
                this.managerId = managerId;
            }
            public String getManagerRealName() {
                return this.managerRealName;
            }
            public void setManagerRealName(String managerRealName) {
                this.managerRealName = managerRealName;
            }
            public String getMerchantCode() {
                return this.merchantCode;
            }
            public void setMerchantCode(String merchantCode) {
                this.merchantCode = merchantCode;
            }
            public String getMerchantId() {
                return this.merchantId;
            }
            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }
            public String getMerchantName() {
                return this.merchantName;
            }
            public void setMerchantName(String merchantName) {
                this.merchantName = merchantName;
            }
            public String getQdsucess() {
                return this.qdsucess;
            }
            public void setQdsucess(String qdsucess) {
                this.qdsucess = qdsucess;
            }
            public String getQuCode() {
                return this.quCode;
            }
            public void setQuCode(String quCode) {
                this.quCode = quCode;
            }
            public String getQuName() {
                return this.quName;
            }
            public void setQuName(String quName) {
                this.quName = quName;
            }
            public String getShengCode() {
                return this.shengCode;
            }
            public void setShengCode(String shengCode) {
                this.shengCode = shengCode;
            }
            public String getShengName() {
                return this.shengName;
            }
            public void setShengName(String shengName) {
                this.shengName = shengName;
            }
            public String getShiCode() {
                return this.shiCode;
            }
            public void setShiCode(String shiCode) {
                this.shiCode = shiCode;
            }
            public String getShiName() {
                return this.shiName;
            }
            public void setShiName(String shiName) {
                this.shiName = shiName;
            }
            public String getStatus() {
                return this.status;
            }
            public void setStatus(String status) {
                this.status = status;
            }
            public String getThinkBuyGood() {
                return this.thinkBuyGood;
            }
            public void setThinkBuyGood(String thinkBuyGood) {
                this.thinkBuyGood = thinkBuyGood;
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
            public long getQdsj() {
                return this.qdsj;
            }
            public void setQdsj(long qdsj) {
                this.qdsj = qdsj;
            }
            public long getLqsj() {
                return this.lqsj;
            }
            public void setLqsj(long lqsj) {
                this.lqsj = lqsj;
            }
            public String getLqsuccess() {
                return this.lqsuccess;
            }
            public void setLqsuccess(String lqsuccess) {
                this.lqsuccess = lqsuccess;
            }
            public String getIsSign() {
                return this.isSign;
            }
            public void setIsSign(String isSign) {
                this.isSign = isSign;
            }
            
}
