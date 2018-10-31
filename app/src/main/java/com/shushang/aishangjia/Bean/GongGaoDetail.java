package com.shushang.aishangjia.Bean;

public class GongGaoDetail {


    /**
     * ret : 200
     * msg : success
     * data : {"leagueNoticeId":"402880b766a3c8750166a3cc038d0001","leagueId":"402880b766800be40166815040990006","leagueName":"测试联盟1黄河路","leagueCode":"testlm","leagueTitle":"开始","leagueNotice":"没有","startTime":1540224000000,"endTime":1540483200000,"chuangjianren":"402880b766800be40166815040ef0007","xiugairen":"402880b766800be40166815040ef0007","cjsj":1540346348000,"xgsj":1540346348000,"del":"0","enable":"1"}
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
         * leagueNoticeId : 402880b766a3c8750166a3cc038d0001
         * leagueId : 402880b766800be40166815040990006
         * leagueName : 测试联盟1黄河路
         * leagueCode : testlm
         * leagueTitle : 开始
         * leagueNotice : 没有
         * startTime : 1540224000000
         * endTime : 1540483200000
         * chuangjianren : 402880b766800be40166815040ef0007
         * xiugairen : 402880b766800be40166815040ef0007
         * cjsj : 1540346348000
         * xgsj : 1540346348000
         * del : 0
         * enable : 1
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
    }
}
