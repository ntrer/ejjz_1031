package com.shushang.aishangjia.Bean;

import java.util.List;

public class XiansuoInfo {


    /**
     * ret : 200
     * msg : success
     * data : {"clueId":"402880b766ccc3c40166cd304f0d0008","name":"小杨","telephone":"186****0069","info":"兔兔","sex":"1","source":"2","address":"兔兔","nowTime":1541040720000,"nowIntention":"1","nextTime":1542855120000,"nextIntention":null,"lastFollowupPerson":"402880b7653b275e01653d01e00b01cc","status":"1","fuZeRen":null,"fuZeRenName":null,"updateTime":1541040720000,"reasons":null,"merchantId":"402880b7653b275e01653b69cc770050","chuangjianren":"402880b7653b275e01653d01e00b01cc","xiugairen":"402880b7653b275e01653d01e00b01cc","cjsj":1541040787000,"xgsj":1541040787000,"del":"0","enable":"1","beizhu":"兔兔","chuangjianrenName":"王苗","lastFollowupPersonName":"王苗","clueActions":[{"time":"2018-11-01 10:53:07","detalInfo":"兔兔","title":"王苗新建线索:小杨"}],"intentionGoodName":null,"outTime":"0","isSelf":null}
     * dataList : null
     * intcurrentPage : 0
     * intpageSize : 0
     * intmaxCount : 0
     * intmaxPage : 0
     */

    private String ret;
    private String msg;
    private DataBean data;
    private Object dataList;
    private int intcurrentPage;
    private int intpageSize;
    private int intmaxCount;
    private int intmaxPage;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getDataList() {
        return dataList;
    }

    public void setDataList(Object dataList) {
        this.dataList = dataList;
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

    public static class DataBean {
        /**
         * clueId : 402880b766ccc3c40166cd304f0d0008
         * name : 小杨
         * telephone : 186****0069
         * info : 兔兔
         * sex : 1
         * source : 2
         * address : 兔兔
         * nowTime : 1541040720000
         * nowIntention : 1
         * nextTime : 1542855120000
         * nextIntention : null
         * lastFollowupPerson : 402880b7653b275e01653d01e00b01cc
         * status : 1
         * fuZeRen : null
         * fuZeRenName : null
         * updateTime : 1541040720000
         * reasons : null
         * merchantId : 402880b7653b275e01653b69cc770050
         * chuangjianren : 402880b7653b275e01653d01e00b01cc
         * xiugairen : 402880b7653b275e01653d01e00b01cc
         * cjsj : 1541040787000
         * xgsj : 1541040787000
         * del : 0
         * enable : 1
         * beizhu : 兔兔
         * chuangjianrenName : 王苗
         * lastFollowupPersonName : 王苗
         * clueActions : [{"time":"2018-11-01 10:53:07","detalInfo":"兔兔","title":"王苗新建线索:小杨"}]
         * intentionGoodName : null
         * outTime : 0
         * isSelf : null
         */

        private String clueId;
        private String name;
        private String telephone;
        private String info;
        private String sex;
        private String source;
        private String address;
        private long nowTime;
        private String nowIntention;
        private long nextTime;
        private Object nextIntention;
        private String lastFollowupPerson;
        private String status;
        private Object fuZeRen;
        private Object fuZeRenName;
        private long updateTime;
        private Object reasons;
        private String merchantId;
        private String chuangjianren;
        private String xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String enable;
        private String beizhu;
        private String chuangjianrenName;
        private String lastFollowupPersonName;
        private Object intentionGoodName;
        private String outTime;
        private Object isSelf;
        private List<ClueActionsBean> clueActions;

        public String getClueId() {
            return clueId;
        }

        public void setClueId(String clueId) {
            this.clueId = clueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getNowTime() {
            return nowTime;
        }

        public void setNowTime(long nowTime) {
            this.nowTime = nowTime;
        }

        public String getNowIntention() {
            return nowIntention;
        }

        public void setNowIntention(String nowIntention) {
            this.nowIntention = nowIntention;
        }

        public long getNextTime() {
            return nextTime;
        }

        public void setNextTime(long nextTime) {
            this.nextTime = nextTime;
        }

        public Object getNextIntention() {
            return nextIntention;
        }

        public void setNextIntention(Object nextIntention) {
            this.nextIntention = nextIntention;
        }

        public String getLastFollowupPerson() {
            return lastFollowupPerson;
        }

        public void setLastFollowupPerson(String lastFollowupPerson) {
            this.lastFollowupPerson = lastFollowupPerson;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getFuZeRen() {
            return fuZeRen;
        }

        public void setFuZeRen(Object fuZeRen) {
            this.fuZeRen = fuZeRen;
        }

        public Object getFuZeRenName() {
            return fuZeRenName;
        }

        public void setFuZeRenName(Object fuZeRenName) {
            this.fuZeRenName = fuZeRenName;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object getReasons() {
            return reasons;
        }

        public void setReasons(Object reasons) {
            this.reasons = reasons;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
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

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getChuangjianrenName() {
            return chuangjianrenName;
        }

        public void setChuangjianrenName(String chuangjianrenName) {
            this.chuangjianrenName = chuangjianrenName;
        }

        public String getLastFollowupPersonName() {
            return lastFollowupPersonName;
        }

        public void setLastFollowupPersonName(String lastFollowupPersonName) {
            this.lastFollowupPersonName = lastFollowupPersonName;
        }

        public Object getIntentionGoodName() {
            return intentionGoodName;
        }

        public void setIntentionGoodName(Object intentionGoodName) {
            this.intentionGoodName = intentionGoodName;
        }

        public String getOutTime() {
            return outTime;
        }

        public void setOutTime(String outTime) {
            this.outTime = outTime;
        }

        public Object getIsSelf() {
            return isSelf;
        }

        public void setIsSelf(Object isSelf) {
            this.isSelf = isSelf;
        }

        public List<ClueActionsBean> getClueActions() {
            return clueActions;
        }

        public void setClueActions(List<ClueActionsBean> clueActions) {
            this.clueActions = clueActions;
        }

        public static class ClueActionsBean {
            /**
             * time : 2018-11-01 10:53:07
             * detalInfo : 兔兔
             * title : 王苗新建线索:小杨
             */

            private String time;
            private String detalInfo;
            private String title;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDetalInfo() {
                return detalInfo;
            }

            public void setDetalInfo(String detalInfo) {
                this.detalInfo = detalInfo;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
