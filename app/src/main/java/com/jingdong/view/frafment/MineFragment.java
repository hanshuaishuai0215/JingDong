package com.jingdong.view.frafment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jingdong.R;
import com.jingdong.view.BossActivity;
import com.jingdong.view.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * 时间:2017/12/3 21:08
 * 作者:韩帅帅
 * 详情:
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    /**
     * 未登陆
     */
    private TextView mLogin;
    private LinearLayout mBackLogin;
    private ImageView mHeadIv;
    private boolean isReady = false;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private MineFragment mineFragment;
    private LinearLayout mAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //取消预加载
        if (view == null) {
            view = inflater.inflate(R.layout.mine_fragment, null);
            initView(view);
            isReady = true;
            delayLoad();
            Log.d("info", "onCreateView");
        } else {
            Log.d("info", "rootView != null");
        }
        // Cache rootView.
        // remove rootView from its parent
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLogin = (TextView) view.findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mBackLogin = (LinearLayout) view.findViewById(R.id.backLogin);
        mBackLogin.setOnClickListener(this);
        mHeadIv = (ImageView) view.findViewById(R.id.head_iv);
        mHeadIv.setOnClickListener(this);
        mAddress = (LinearLayout) view.findViewById(R.id.address);
        mAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (!mLogin.getText().equals("未登录,请登录!")) {
                    Toast.makeText(getActivity(), "来给自己换个高大上的名字吧!!!", Toast.LENGTH_SHORT).show();
                    updateUserName();
                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.backLogin://退出登陆
                String uid = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", "");
                String uName = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uName", "");
                if (uid.isEmpty() && uName.isEmpty()) {
                    Toast.makeText(getActivity(), "你还没登陆呢,怎么能退出登录呢?", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences userSettings = getActivity().getSharedPreferences("user", 0);
                    SharedPreferences.Editor editor = userSettings.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(getActivity(), "成功退出登陆!!!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getActivity(), BossActivity.class);
                    getActivity().startActivity(in);
                    getActivity().finish();
                }
                break;
            case R.id.head_iv://点击更换头像
                String uid1 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", "");
                String uName1 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uName", "");
                if (uid1.isEmpty() && uName1.isEmpty()) {
                    Toast.makeText(getActivity(), "你还没登陆呢,怎么能上传头像呢?", Toast.LENGTH_SHORT).show();
                } else {
                    showTypeDialog();
                }
                break;
            case R.id.address:
                String uid2 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", "");
                String uName2 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uName", "");
                if (uid2.isEmpty() && uName2.isEmpty()) {
                    Toast.makeText(getActivity(), "你还没登陆呢,怎么能添加地址呢?", Toast.LENGTH_SHORT).show();
                } else {
                    inAddress();
                }
                break;
        }
    }

    /**
     * 添加地址
     */
    private void inAddress() {
        LayoutInflater factory = LayoutInflater.from(getActivity());//提示框
        final View view = factory.inflate(R.layout.dialog_address, null);//这里必须是final的
        final EditText userCity = (EditText) view.findViewById(R.id.userCity);//获得输入框对象
        final EditText userAddress = (EditText) view.findViewById(R.id.userAddress);//获得输入框对象
        new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //事件
                                String city = userCity.getText().toString().trim();
                                String address = userAddress.getText().toString().trim();
                                if (city.length() < 5 || address.length() < 5) {
                                    Toast.makeText(getActivity(), "地址不合法奥!!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }).setNegativeButton("取消", null).create().show();
    }

    /**
     * 修改名字
     */
    private void updateUserName() {
        LayoutInflater factory = LayoutInflater.from(getActivity());//提示框
        final View view = factory.inflate(R.layout.dialog_user_name, null);//这里必须是final的
        final EditText userName = (EditText) view.findViewById(R.id.userName);//获得输入框对象
        new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //事件
                                String trim = userName.getText().toString().trim();
                                if (trim.length() < 1) {
                                    Toast.makeText(getActivity(), "名字不合法!!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                mLogin.setText(trim);
                            }
                        }).setNegativeButton("取消", null).create().show();
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        mHeadIv.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //取消预加载  负责会提前土司
    @Override
    protected void delayLoad() {
        if (!isReady || !isVisible) {
            return;
        }
        //　This is a random widget, it will be instantiation in init()
        if (mineFragment == null) {
            init();
        }
    }

    private void init() {
        mineFragment = new MineFragment();
        String uid = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", "");
        String uName = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uName", "");
        String uImg = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("headimg", "");
        if (uid.length() < 1) {
            mLogin.setText("未登录,请登录!");
        } else {
            mLogin.setText("欢迎:" + uName);
            if (uImg.length() < 1) {
                Toast.makeText(getActivity(), "怎么能没有头像呢?请点击头像上传你的专属头像!", Toast.LENGTH_SHORT).show();
            } else {
                Glide.with(getActivity()).load(uImg).into(mHeadIv);
            }
        }
    }
}
