package com.jingdong.view.frafment;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jingdong.R;
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
        return view;
    }

    private void initView(View view) {
        mLogin = (TextView) view.findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mBackLogin = (LinearLayout) view.findViewById(R.id.backLogin);
        mBackLogin.setOnClickListener(this);
        mHeadIv = (ImageView) view.findViewById(R.id.head_iv);
        mHeadIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.backLogin:
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
                    mLogin.setText("未登录,请登录!");
                    Glide.with(getActivity()).load(R.drawable.ic_default_user_head).into(mHeadIv);
                }
                break;
            case R.id.head_iv://点击更换头像
                String uid1 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", "");
                String uName1 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uName", "");
                if (uid1.isEmpty() && uName1.isEmpty()) {
                    Toast.makeText(getActivity(), "你还没登陆呢,怎么上传头像呢?", Toast.LENGTH_SHORT).show();
                } else {
                    showTypeDialog();
                }
                break;
        }
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
