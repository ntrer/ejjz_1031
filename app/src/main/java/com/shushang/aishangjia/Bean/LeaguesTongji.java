package com.shushang.aishangjia.Bean;

public class LeaguesTongji {


    /**
     * ret : 200
     * msg : success
     * data : {"leagueId":null,"leagueName":null,"leagueCode":null,"weekIn":0,"monthIn":600,"yearIn":600,"weekOut":0,"monthOut":423,"yearOut":423,"totalIn":600,"totalOut":423,"weekBalance":0,"monthBalance":177,"yearBalance":177,"totalBalance":177}
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
         * leagueId : null
         * leagueName : null
         * leagueCode : null
         * weekIn : 0.0
         * monthIn : 600.0
         * yearIn : 600.0
         * weekOut : 0.0
         * monthOut : 423.0
         * yearOut : 423.0
         * totalIn : 600.0
         * totalOut : 423.0
         * weekBalance : 0.0
         * monthBalance : 177.0
         * yearBalance : 177.0
         * totalBalance : 177.0
         */

        private Object leagueId;
        private Object leagueName;
        private Object leagueCode;
        private double weekIn;
        private double monthIn;
        private double yearIn;
        private double weekOut;
        private double monthOut;
        private double yearOut;
        private double totalIn;
        private double totalOut;
        private double weekBalance;
        private double monthBalance;
        private double yearBalance;
        private double totalBalance;

        public Object getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(Object leagueId) {
            this.leagueId = leagueId;
        }

        public Object getLeagueName() {
            return leagueName;
        }

        public void setLeagueName(Object leagueName) {
            this.leagueName = leagueName;
        }

        public Object getLeagueCode() {
            return leagueCode;
        }

        public void setLeagueCode(Object leagueCode) {
            this.leagueCode = leagueCode;
        }

        public double getWeekIn() {
            return weekIn;
        }

        public void setWeekIn(double weekIn) {
            this.weekIn = weekIn;
        }

        public double getMonthIn() {
            return monthIn;
        }

        public void setMonthIn(double monthIn) {
            this.monthIn = monthIn;
        }

        public double getYearIn() {
            return yearIn;
        }

        public void setYearIn(double yearIn) {
            this.yearIn = yearIn;
        }

        public double getWeekOut() {
            return weekOut;
        }

        public void setWeekOut(double weekOut) {
            this.weekOut = weekOut;
        }

        public double getMonthOut() {
            return monthOut;
        }

        public void setMonthOut(double monthOut) {
            this.monthOut = monthOut;
        }

        public double getYearOut() {
            return yearOut;
        }

        public void setYearOut(double yearOut) {
            this.yearOut = yearOut;
        }

        public double getTotalIn() {
            return totalIn;
        }

        public void setTotalIn(double totalIn) {
            this.totalIn = totalIn;
        }

        public double getTotalOut() {
            return totalOut;
        }

        public void setTotalOut(double totalOut) {
            this.totalOut = totalOut;
        }

        public double getWeekBalance() {
            return weekBalance;
        }

        public void setWeekBalance(double weekBalance) {
            this.weekBalance = weekBalance;
        }

        public double getMonthBalance() {
            return monthBalance;
        }

        public void setMonthBalance(double monthBalance) {
            this.monthBalance = monthBalance;
        }

        public double getYearBalance() {
            return yearBalance;
        }

        public void setYearBalance(double yearBalance) {
            this.yearBalance = yearBalance;
        }

        public double getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(double totalBalance) {
            this.totalBalance = totalBalance;
        }
    }
}
