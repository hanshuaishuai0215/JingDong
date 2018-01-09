package com.jingdong.view.IView;

import com.jingdong.bean.LoginBean;

/**
 * 时间:2017/12/3 21:19
 * 作者:韩帅帅
 * 详情:
 */

public interface IMainActivity {
    public String getAccount();

    public String getPwd();

    public void show(String str);

    public void toRegisterAc();

    public void toClassAc(LoginBean loginBean);
}
