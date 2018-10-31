package com.shushang.aishangjia.Bean;

import java.io.Serializable;
import java.util.List;

public class LeaguesList {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"leagueFinanceId":"402880b76699583701669aa10f74005a","leagueId":"402880b766854bd701668551f7aa000d","leagueName":"联盟1测试","leagueCode":"testlm1","leagueInOut":111,"type":"0","imageIds":"402880b76699583701669ad622f50068,402880b76699583701669ad623ef006a","useCase":"测试","beizhu":"123","reterenceName":"刘德华(数尚地板)","time":"2018-10-01","chuangjianren":"402880b766854bd701668551f7d9000e","xiugairen":"402880b766854bd701668551f7d9000e","cjsj":1540192538000,"xgsj":1540196044000,"del":"0","enable":"1"},{"leagueFinanceId":"402880b76699583701669acb77b50061","leagueId":"402880b766854bd701668551f7aa000d","leagueName":"联盟1测试","leagueCode":"testlm1","leagueInOut":1,"type":"0","imageIds":"402880b76699583701669ad6b33d006b","useCase":"123","beizhu":"123","reterenceName":"超级管理员(数尚地板)","time":"2018-10-01","chuangjianren":"402880b766854bd701668551f7d9000e","xiugairen":"402880b766854bd701668551f7d9000e","cjsj":1540195318000,"xgsj":1540196056000,"del":"0","enable":"1"}]
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

    public static class DataListBean implements Serializable {
        /**
         * leagueFinanceId : 402880b76699583701669aa10f74005a
         * leagueId : 402880b766854bd701668551f7aa000d
         * leagueName : 联盟1测试
         * leagueCode : testlm1
         * leagueInOut : 111.0
         * type : 0
         * imageIds : 402880b76699583701669ad622f50068,402880b76699583701669ad623ef006a
         * useCase : 测试
         * beizhu : 123
         * reterenceName : 刘德华(数尚地板)
         * time : 2018-10-01
         * chuangjianren : 402880b766854bd701668551f7d9000e
         * xiugairen : 402880b766854bd701668551f7d9000e
         * cjsj : 1540192538000
         * xgsj : 1540196044000
         * del : 0
         * enable : 1
         */

        private String leagueFinanceId;
        private String leagueId;
        private String leagueName;
        private String leagueCode;
        private double leagueInOut;
        private String type;
        private String imageIds;
        private String useCase;
        private String beizhu;
        private String reterenceName;
        private String time;
        private String chuangjianren;
        private String xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String enable;

        public String getLeagueFinanceId() {
            return leagueFinanceId;
        }

        public void setLeagueFinanceId(String leagueFinanceId) {
            this.leagueFinanceId = leagueFinanceId;
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

        public double getLeagueInOut() {
            return leagueInOut;
        }

        public void setLeagueInOut(double leagueInOut) {
            this.leagueInOut = leagueInOut;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImageIds() {
            return imageIds;
        }

        public void setImageIds(String imageIds) {
            this.imageIds = imageIds;
        }

        public String getUseCase() {
            return useCase;
        }

        public void setUseCase(String useCase) {
            this.useCase = useCase;
        }

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getReterenceName() {
            return reterenceName;
        }

        public void setReterenceName(String reterenceName) {
            this.reterenceName = reterenceName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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
