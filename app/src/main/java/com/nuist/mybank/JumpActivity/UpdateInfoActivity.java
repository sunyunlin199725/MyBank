package com.nuist.mybank.JumpActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Dialog.InputDialog;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.AccountInfo.Account;
import com.nuist.mybank.POJO.ResultBean.GetAccountResult;
import com.nuist.mybank.POJO.ResultBean.ResultFileUpLoad;
import com.nuist.mybank.POJO.ResultBean.UpdateInfoResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CircleImageView;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class UpdateInfoActivity extends AppCompatActivity {

    @BindView(R.id.updateinfo_actionbar)
    CustomActionBar updateinfoActionbar;
    @BindView(R.id.header_iv)
    CircleImageView headerIv;
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.userphone_tv)
    TextView userphoneTv;
    @BindView(R.id.useremail_tv)
    TextView useremailTv;
    @BindView(R.id.useraddress_tv)
    TextView useraddressTv;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private String Tag = "UpdateInfoActivity";
    private File tempFile;//临时文件，用于保存拍照或相册获取的原始图片
    private File mCropImageFile;//用于保存裁剪完之后的图片
    private String img_url; //图片文件绝对地址
    private PopupWindow popupWindow;//下拉弹框
    private View contentView;//用于绑定资源文件
    private TextView mSelectphoto;//下拉弹框资源文件的选择图片
    private TextView mSelectcamera;//下拉弹框资源文件的选择相机
    private TextView mSelectcancel;//下拉弹框资源文件的取消
    private AlertDialog alertDialog;
    public static  int IMAGE_REQUEST_CODE = 0;//从相册获取
    public static  int CAMERA_REQUEST_CODE = 1;//拍照获取
    public static  int RESIZE_REQUEST_CODE = 2;//裁剪
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private GetAccountResult.AccountBean mCurrentAccount;//当前用户
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        ButterKnife.bind(this);
        updateinfoActionbar.setStyle("信息修改");
        requestPermission();
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        getCurrentAccount(mSharedPreferences.getInt("account_id",000));

    }

    @OnClick({R.id.header_iv, R.id.userphone_tv, R.id.useremail_tv, R.id.useraddress_tv, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_iv:
                showPopwindow();
                break;
            case R.id.userphone_tv:
                showDialog(userphoneTv);
                break;
            case R.id.useremail_tv:
                showDialog(useremailTv);
                break;
            case R.id.useraddress_tv:
                showDialog(useraddressTv);
                break;
            case R.id.btn_commit:
                Account account = new Account();
                if(mCropImageFile != null){
                    account.setUser_header("images/"+mCropImageFile.getName());
                    Log.e(Tag,account.getUser_header());
                }
                account.setAccount_id(mSharedPreferences.getInt("account_id",000));
                Log.e(Tag,account.getAccount_id()+"");
                account.setUser_phone(userphoneTv.getText().toString());
                account.setUser_email(useremailTv.getText().toString());
                account.setUser_address(useraddressTv.getText().toString());
                UpdateInfo(account);
                break;
        }
    }

    /**
     * 获取当前用户
     * @param account_id
     */
    public void getCurrentAccount(int account_id){
        mService.getCurrentAccount(account_id)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                 .subscribe(new Consumer<GetAccountResult>() {
                     @Override
                     public void accept(GetAccountResult getAccountResult) throws Exception {
                         mCurrentAccount = getAccountResult.getData();
                         if(getAccountResult.isSuccess() == true){
                             if(mCurrentAccount.getUser_header() != null){
                                 Picasso.with(UpdateInfoActivity.this).load(Config.baseurl + mCurrentAccount.getUser_header()).into(headerIv);
                             }
                             if(mCurrentAccount.getAccount_name() != null){
                                 usernameTv.setText(mCurrentAccount.getAccount_name());
                             }
                             if(mCurrentAccount.getUser_phone() != null){
                                 userphoneTv.setText(mCurrentAccount.getUser_phone());
                             }
                             if (mCurrentAccount.getUser_email() != null) {
                                 useremailTv.setText(mCurrentAccount.getUser_email());
                             }
                             if (mCurrentAccount.getUser_address() != null){
                                 useraddressTv.setText(mCurrentAccount.getUser_address());
                             }
                         }
                         Log.e(Tag, mCurrentAccount.toString());
                     }
                 }, new Consumer<Throwable>() {
                     @Override
                     public void accept(Throwable throwable) throws Exception {
                         Log.e(Tag, throwable.toString());
                     }
                 });
    }
    //显示底部弹窗
    private void showPopwindow() {
        //加载弹出框的布局
        contentView = LayoutInflater.from(UpdateInfoActivity.this).inflate(
                R.layout.popup_window, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable( new ColorDrawable());

        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM,0,0);
        // 按下android回退物理键 PopipWindow消失解决
        //处理popupwindow
        popupwindowclick(contentView);
    }
    //底部弹窗的控件绑定
    private void popupwindowclick(View contentView) {
        mSelectcamera = (TextView) contentView.findViewById(R.id.select_camera_tv);
        mSelectphoto = (TextView) contentView.findViewById(R.id.select_phone_tv);
        mSelectcancel = (TextView)contentView.findViewById(R.id.select_cancel_tv);
        mSelectcamera.setOnClickListener(popUpListener);
        mSelectphoto.setOnClickListener(popUpListener);
        mSelectcancel.setOnClickListener(popUpListener);
    }
    //底部控件的监听事件
    View.OnClickListener popUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.select_camera_tv:
                    pickImageFromCamera();
                    if(popupWindow!=null){
                        popupWindow.dismiss();
                    }
                    break;
                case R.id.select_phone_tv:
                    pickImageFromAlbum();
                    if(popupWindow!=null){
                        popupWindow.dismiss();
                    }
                    break;
                case R.id.select_cancel_tv:
                    if(popupWindow!=null){
                        popupWindow.dismiss();
                    }
                    break;
            }
        }
    };

    /**
     * 这个函数处理从相册获取图片问题
     */
    private void pickImageFromAlbum(){
        Intent picIntent = new Intent(Intent.ACTION_PICK, null);
        picIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(picIntent, IMAGE_REQUEST_CODE);
    }
    /**
     * 这个函数是处理从摄像机图片问题
     */
    private void pickImageFromCamera(){
        if (hasSdcard()) {
            Date date=new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//获取当前时间，进一步转化为字符串
            //设置图片的名字为时间戳，例如202003131226.jpg
            String name=format.format(date)+".jpg";
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    name);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(
                        this, "com.nuist.mybank.FileProvider",
                        tempFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(tempFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case 0://从相册获取
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    try {
                        Uri originalUri = data.getData();        //获得图片的uri
                        //这里开始的第二部分，获取图片的路径：
                        cropPic(originalUri);
                        //iv_head.setImageURI(originalUri);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        img_url = cursor.getString(column_index);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        Log.e("this",img_url);
                    }
                    break;
                case 1://拍照获取
                    Uri uri;
                    //如果用户手机操作系统大于安卓7.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        uri = FileProvider.getUriForFile(this, "com.nuist.mybank.FileProvider", tempFile);
                    } else{
                        uri = Uri.fromFile(tempFile);
                    }
                    cropPic(uri);
                    break;
                case 2://裁剪图片
                    //用于设置显示的图片
                    Bitmap headerBitmap = BitmapFactory.decodeFile(mCropImageFile.getAbsolutePath());
                    headerIv.setImageBitmap(headerBitmap);
                    //getJson4(mCropImageFile);
                    UploadHeaderImage(mCropImageFile);
                    Log.e(Tag,mCropImageFile.getAbsolutePath());
                    Log.e(Tag,mCropImageFile.getName());
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void cropPic(Uri data) {
        Date date=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//获取当前时间，进一步转化为字符串
        //设置图片的名字为时间戳，例如202003131226.jpg
        String name=format.format(date)+"_crop.jpg";
        mCropImageFile = new File(Environment.getExternalStorageDirectory(),   //创建一个保存裁剪后照片的file
                name);
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);         //X方向上的比列
        intent.putExtra("aspectY", 1);         // Y方向上的比例
        intent.putExtra("outputX", 400);       //裁剪区的宽度
        intent.putExtra("outputY", 400);       //裁剪区的高度
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);  //是否在Intent中返回数据
        //如果用户手机操作系统大于安卓7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropImageFile));
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }
    /**
     * 判断存储卡状态
     */
    private boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    /**
     *  用于动态获取各种权限
     */
    private void requestPermission() {
        //读写权限
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.USE_FINGERPRINT};    //请求状态码
        int REQUEST_PERMISSION_CODE = 2;
        //判断6.0大于等于.当前权限时，直接运行，超过6.0的话动态调取权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED|| ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
    }
    /**
     *  显示自定义对话框
     */
    private void showDialog(final TextView textView){
        InputDialog inputDialog=new InputDialog(UpdateInfoActivity.this, new InputDialog.OnEditInputFinishedListener() {
            @Override
            public void editInputFinished(String message) {
                if(!TextUtils.isEmpty(message)){
                    textView.setText(message);
                }
            }
        });
        inputDialog.setView(new EditText(UpdateInfoActivity.this));
        inputDialog.show();
    }

    /**
     * 上传头像图片到服务器
     * @param file
     */
    private void UploadHeaderImage(File file){
        Log.e(Tag,file.toString());
        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part part =MultipartBody.Part.createFormData("file",file.getName(),body);
        mService.fileupload(part)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<ResultFileUpLoad>() {
                    @Override
                    public void accept(ResultFileUpLoad resultFileUpLoad) throws Exception {
                        Toast.makeText(UpdateInfoActivity.this,resultFileUpLoad.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.toString());
                    }
                });
    }

    private void UpdateInfo(Account account){
        mService.updateInfo(account)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<UpdateInfoResult>() {
                    @Override
                    public void accept(UpdateInfoResult updateInfoResult) throws Exception {
                        Toast.makeText(UpdateInfoActivity.this, updateInfoResult.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"跟新错误信息 --》" + throwable.toString());
                    }
                });
    }
}
