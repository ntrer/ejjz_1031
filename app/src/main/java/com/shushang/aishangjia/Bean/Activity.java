package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/8/7.
 */

public class Activity {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"activityId":"402880b7653b275e01653c3ed98800a8","activityCode":1049,"activityName":"安阳万达双11送大礼","eventStart":1533052800000,"eventEnd":1538323200000,"sellCardStart":1527782400000,"sellCardEnd":1538323200000,"sceneStart":1534780800000,"sceneEnd":1534867200000,"chuangjianren":"402880b7653b275e01653b68e4d00046","xiugairen":"402880b7653b275e01653b68e4d00046","cjsj":1534314077000,"xgsj":1534316617000,"del":"0","active":"1","merchantId":"402880b7653b275e01653b68e4cf0045","merchants":null,"coverImageId":null,"shengCode":"410000","shengName":"河南省","shiCode":"410500","shiName":"安阳市","quCode":null,"quName":null,"qdjpname":"玩偶","yxjpname":"折叠凳","isUnderLine":null}]
     */

    private String ret;
    private String msg;
    private Object data;
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

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * activityId : 402880b7653b275e01653c3ed98800a8
         * activityCode : 1049
         * activityName : 安阳万达双11送大礼
         * eventStart : 1533052800000
         * eventEnd : 1538323200000
         * sellCardStart : 1527782400000
         * sellCardEnd : 1538323200000
         * sceneStart : 1534780800000
         * sceneEnd : 1534867200000
         * chuangjianren : 402880b7653b275e01653b68e4d00046
         * xiugairen : 402880b7653b275e01653b68e4d00046
         * cjsj : 1534314077000
         * xgsj : 1534316617000
         * del : 0
         * active : 1
         * merchantId : 402880b7653b275e01653b68e4cf0045
         * merchants : null
         * coverImageId : null
         * shengCode : 410000
         * shengName : 河南省
         * shiCode : 410500
         * shiName : 安阳市
         * quCode : null
         * quName : null
         * qdjpname : 玩偶
         * yxjpname : 折叠凳
         * isUnderLine : null
         */

        private String activityId;
        private int activityCode;
        private String activityName;
        private long eventStart;
        private long eventEnd;
        private long sellCardStart;
        private long sellCardEnd;
        private long sceneStart;
        private long sceneEnd;
        private String chuangjianren;
        private String xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String active;
        private String merchantId;
        private Object merchants;
        private Object coverImageId;
        private String shengCode;
        private String shengName;
        private String shiCode;
        private String shiName;
        private Object quCode;
        private Object quName;
        private String qdjpname;
        private String yxjpname;
        private Object isUnderLine;
        private Object isCheck;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public int getActivityCode() {
            return activityCode;
        }

        public void setActivityCode(int activityCode) {
            this.activityCode = activityCode;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public long getEventStart() {
            return eventStart;
        }

        public void setEventStart(long eventStart) {
            this.eventStart = eventStart;
        }

        public long getEventEnd() {
            return eventEnd;
        }

        public void setEventEnd(long eventEnd) {
            this.eventEnd = eventEnd;
        }

        public long getSellCardStart() {
            return sellCardStart;
        }

        public void setSellCardStart(long sellCardStart) {
            this.sellCardStart = sellCardStart;
        }

        public long getSellCardEnd() {
            return sellCardEnd;
        }

        public void setSellCardEnd(long sellCardEnd) {
            this.sellCardEnd = sellCardEnd;
        }

        public long getSceneStart() {
            return sceneStart;
        }

        public void setSceneStart(long sceneStart) {
            this.sceneStart = sceneStart;
        }

        public long getSceneEnd() {
            return sceneEnd;
        }

        public void setSceneEnd(long sceneEnd) {
            this.sceneEnd = sceneEnd;
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

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public Object getMerchants() {
            return merchants;
        }

        public void setMerchants(Object merchants) {
            this.merchants = merchants;
        }

        public Object getCoverImageId() {
            return coverImageId;
        }

        public void setCoverImageId(Object coverImageId) {
            this.coverImageId = coverImageId;
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

        public String getQdjpname() {
            return qdjpname;
        }

        public void setQdjpname(String qdjpname) {
            this.qdjpname = qdjpname;
        }

        public String getYxjpname() {
            return yxjpname;
        }

        public void setYxjpname(String yxjpname) {
            this.yxjpname = yxjpname;
        }

        public Object getisCheck() {
            return isCheck;
        }

        public void setisCheck(Object isCheck) {
            this.isCheck = isCheck;
        }

        public Object getIsUnderLine() {
            return isUnderLine;
        }

        public void setIsUnderLine(Object isUnderLine) {
            this.isUnderLine = isUnderLine;
        }
    }
}
