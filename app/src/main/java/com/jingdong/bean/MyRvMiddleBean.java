package com.jingdong.bean;

import java.util.List;

/**
 * 时间:2017/12/5 20:45
 * 作者:韩帅帅
 * 详情:
 */

public class MyRvMiddleBean extends BaseBean{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cid : 1
         * createtime : 2017-10-10T19:41:39
         * icon : http://120.27.23.105/images/category/shop.png
         * ishome : 1
         * name : 京东超市
         */

        private int cid;
        private String createtime;
        private String icon;
        private int ishome;
        private String name;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getIshome() {
            return ishome;
        }

        public void setIshome(int ishome) {
            this.ishome = ishome;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
