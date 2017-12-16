package com.jingdong.net;

/**
 * 时间:2017/12/3 21:17
 * 作者:韩帅帅
 * 详情:
 */
public interface Api {
    public static boolean isOnline = false;
    public static final String DEV = "http://120.27.23.105/";
    public static final String WROK = "";
    public static final String HOST = isOnline ? WROK : DEV;
    public static final String PRODUCT_CATAGORY_LIST = HOST + "/product/getProducts";
    public static final String LOGIN = HOST + "user/login";//登陆
    public static final String REGISTER = HOST + "user/reg";//注册
    public static final String CLASS = HOST + "product/getCatagory";//分类
    public static final String PRODUCT_CATAGORY = HOST + "product/getProductCatagory";//商品子分类接口
    public static final String ZHUYEURL = HOST+"ad/getAd";
    public static final String ZHUYEMIDDLEVIEW = HOST+"product/getCatagory";
    public static final String ADD_CARD = HOST + "/product/addCart";
    public static final String PRODUCT_DETAIL = HOST + "/product/getProductDetail?pid=%s&source=android";
    public static final String SELECT_CARD = HOST + "/product/getCarts";
    public static final String DEL_CARD = HOST + "/product/deleteCart";
}
