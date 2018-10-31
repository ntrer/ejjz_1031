package com.shushang.aishangjia.Bean;

import java.io.Serializable;
import java.util.List;

public class GongGao {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"leagueNoticeId":"402880b766aa93900166aaa7ac8a001a","leagueId":"402880b766800be40166815040990006","leagueName":"测试联盟1黄河路","leagueCode":"testlm","leagueTitle":"哈哈哈","leagueNotice":"呵呵呵","startTime":1540396800000,"endTime":1540915200000,"chuangjianren":"联盟超级管理员","xiugairen":"402880b766800be40166815040ef0007","cjsj":1540461407000,"xgsj":1540461407000,"del":"0","enable":"1","isRead":"1"},{"leagueNoticeId":"402880b766aa93900166aa9f52d60010","leagueId":"402880b766800be40166815040990006","leagueName":"测试联盟1黄河路","leagueCode":"testlm","leagueTitle":"好消息","leagueNotice":"好消息是没有的","startTime":1540483200000,"endTime":1540915200000,"chuangjianren":"联盟超级管理员","xiugairen":"402880b766800be40166815040ef0007","cjsj":1540460860000,"xgsj":1540460860000,"del":"0","enable":"1","isRead":"0"},{"leagueNoticeId":"402880b76689dcb2016689f1888d0008","leagueId":"402880b766800be40166815040990006","leagueName":"测试联盟1黄河路","leagueCode":"testlm","leagueTitle":null,"leagueNotice":"拉会计师的考拉坚实的拉开了看了健康，爱神的箭卡圣诞快乐阿萨德","startTime":1538323200000,"endTime":1540656000000,"chuangjianren":"联盟超级管理员","xiugairen":"402880b766800be40166815040ef0007","cjsj":1539912600000,"xgsj":1539912600000,"del":"0","enable":"1","isRead":"1"},{"leagueNoticeId":"402880b76686b67b016686bbac9a0000","leagueId":"402880b766800be40166815040990006","leagueName":"测试联盟1黄河路","leagueCode":"testlm","leagueTitle":null,"leagueNotice":"拉屎的健康那就开始的检索到课教案速度快安速度快，卡萨丁吗，是你的，啊","startTime":1538323200000,"endTime":1540656000000,"chuangjianren":"联盟超级管理员","xiugairen":"402880b766800be40166815040ef0007","cjsj":1539858738000,"xgsj":1539858738000,"del":"0","enable":"1","isRead":"1"},{"leagueNoticeId":"402880b7668677910166868f9a15000a","leagueId":"402880b766800be40166815040990006","leagueName":"测试联盟1黄河路","leagueCode":"testlm","leagueTitle":null,"leagueNotice":"阿克苏较好的卡机阿萨德看奥卡仕达看的数据库安居客十多年安居客十多年健康的接口","startTime":1539855845000,"endTime":1540569600000,"chuangjianren":"联盟超级管理员","xiugairen":"402880b766800be40166815040ef0007","cjsj":1539855850000,"xgsj":1539858716000,"del":"0","enable":"1","isRead":"1"}]
     * intcurrentPage : 1
     * intpageSize : 10
     * intmaxCount : 5
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

    public static class DataListBean implements Serializable {
        /**
         * leagueNoticeId : 402880b766aa93900166aaa7ac8a001a
         * leagueId : 402880b766800be40166815040990006
         * leagueName : 测试联盟1黄河路
         * leagueCode : testlm
         * leagueTitle : 哈哈哈
         * leagueNotice : 呵呵呵
         * startTime : 1540396800000
         * endTime : 1540915200000
         * chuangjianren : 联盟超级管理员
         * xiugairen : 402880b766800be40166815040ef0007
         * cjsj : 1540461407000
         * xgsj : 1540461407000
         * del : 0
         * enable : 1
         * isRead : 1
         */

        private String leagueNoticeId;
        private String leagueId;
        private String leagueName;
        private String leagueCode;
        private String leagueTitle;
        private String leagueNotice;
        private long startTime;
        private long endTime;
        private String chuangjianren;
        private String xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String enable;
        private String isRead;

        public String getLeagueNoticeId() {
            return leagueNoticeId;
        }

        public void setLeagueNoticeId(String leagueNoticeId) {
            this.leagueNoticeId = leagueNoticeId;
        }

        public String getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(String leagueId) {
            this.leagueId = leagueId;
        }

        public String getLeagueName() {
            return leagueName;
        }

        public void setLeagueName(String leagueName) {
            this.leagueName = leagueName;
        }

        public String getLeagueCode() {
            return leagueCode;
        }

        public void setLeagueCode(String leagueCode) {
            this.leagueCode = leagueCode;
        }

        public String getLeagueTitle() {
            return leagueTitle;
        }

        public void setLeagueTitle(String leagueTitle) {
            this.leagueTitle = leagueTitle;
        }

        public String getLeagueNotice() {
            return leagueNotice;
        }

        public void setLeagueNotice(String leagueNotice) {
            this.leagueNotice = leagueNotice;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
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

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }
    }
}
