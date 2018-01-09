package com.jingdong.bean;

import java.util.List;

/**
 * 时间:2017/12/21 15:31
 * 作者:韩帅帅
 * 详情:订单数据展示的实体类
 */

public class GetOrdersBean {

    /**
     * msg : 请求成功
     * code : 0
     * data : [{"createtime":"2017-10-22T15:14:39","orderid":890,"price":11800,"status":0,"title":"","uid":71},{"createtime":"2017-11-09T09:20:58","orderid":1447,"price":567,"status":0,"title":"订单标题测试","uid":71},{"createtime":"2017-11-09T09:20:58","orderid":1449,"price":399,"status":0,"title":"订单标题测试","uid":71},{"createtime":"2017-12-11T19:11:33","orderid":3344,"price":99.99,"status":0,"title":null,"uid":71},{"createtime":"2017-12-11T20:13:52","orderid":3345,"price":99.99,"status":0,"title":null,"uid":71},{"createtime":"2017-12-11T20:58:31","orderid":3346,"price":99.99,"status":0,"title":null,"uid":71},{"createtime":"2017-12-12T14:14:48","orderid":3349,"price":99.99,"status":0,"title":null,"uid":71},{"createtime":"2017-12-12T14:14:48","orderid":3350,"price":99.99,"status":0,"title":null,"uid":71},{"createtime":"2017-12-12T14:14:48","orderid":3351,"price":99.99,"status":0,"title":null,"uid":71},{"createtime":"2017-12-13T20:29:14","orderid":3371,"price":99.99,"status":0,"title":null,"uid":71}]
     * page : 1
     */

    private String msg;
    private String code;
    private String page;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : 2017-10-22T15:14:39
         * orderid : 890
         * price : 11800.0
         * status : 0
         * title :
         * uid : 71
         */

        private String createtime;
        private int orderid;
        private double price;
        private int status;
        private String title;
        private int uid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
