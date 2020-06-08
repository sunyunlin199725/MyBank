package com.nuist.mybank.Interface;



import com.nuist.mybank.POJO.AccountInfo.Account;
import com.nuist.mybank.POJO.AccountInfo.BankCard;
import com.nuist.mybank.POJO.AccountInfo.CustomizeDate;
import com.nuist.mybank.POJO.AccountInfo.FeedBack;
import com.nuist.mybank.POJO.AccountInfo.GoodsCode;
import com.nuist.mybank.POJO.AccountInfo.Ordertable;
import com.nuist.mybank.POJO.AccountInfo.Transfer;
import com.nuist.mybank.POJO.ResultBean.GetAccountFromBcnoResult;
import com.nuist.mybank.POJO.ResultBean.GetAccountResult;
import com.nuist.mybank.POJO.ResultBean.GetBCResult;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.ResultBean.GetChartResult;
import com.nuist.mybank.POJO.ResultBean.GetCodesResult;
import com.nuist.mybank.POJO.ResultBean.GetGoodsFrNaResult;
import com.nuist.mybank.POJO.ResultBean.GetGoodsResult;
import com.nuist.mybank.POJO.ResultBean.GetInfoDetailResult;
import com.nuist.mybank.POJO.ResultBean.GetInfoResult;
import com.nuist.mybank.POJO.ResultBean.GetMoneyResult;
import com.nuist.mybank.POJO.ResultBean.GetMonthSumResult;
import com.nuist.mybank.POJO.ResultBean.GetOrderDetailResult;
import com.nuist.mybank.POJO.ResultBean.GetOrderResult;
import com.nuist.mybank.POJO.ResultBean.GetPayResult;
import com.nuist.mybank.POJO.ResultBean.GetPhoneResult;
import com.nuist.mybank.POJO.ResultBean.GetSevenPayResult;
import com.nuist.mybank.POJO.ResultBean.GetStringResult;
import com.nuist.mybank.POJO.ResultBean.GetTransferDetailResult;
import com.nuist.mybank.POJO.ResultBean.GetTransferResult;
import com.nuist.mybank.POJO.ResultBean.GetYearDataResult;
import com.nuist.mybank.POJO.ResultBean.LoginResult;
import com.nuist.mybank.POJO.ResultBean.ResultFileUpLoad;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.POJO.ResultBean.UpdateInfoResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {
    //获取当前账户
    @POST("getCurrentAccount.do")
    Observable<GetAccountResult> getCurrentAccount(@Query("account_id") int account_id);
    //获取当前账户头像
    @POST("getHeaderUrl.do")
    Observable<GetStringResult> getCurrentHeader(@Query("account_id") int account_id);
    //文件上传
    @Multipart
    @POST("upload.do")
    Observable<ResultFileUpLoad> fileupload(@Part MultipartBody.Part file);
    //用户登录
    @POST("login.do")
    Observable<LoginResult> login(@Query("user_name") String name, @Query("user_pwd") String pwd);
    //更新用户信息
    @POST("update.do")
    Observable<UpdateInfoResult> updateInfo(@Body Account account);
    //判断用户名是否可用
    @POST("judge.do")
    Observable<ResultObjectBean> judge(@Query("user_name") String user_name);
    //用户注册
    @POST("register.do")
    Observable<ResultObjectBean> register(@Body Account account);
    //获取当前账户绑定银行卡
    @POST("getBankCard.do")
    Observable<GetBankCardResult> getBankCard(@Query("account_id")int account_id);
    //判断银行卡是否存在
    @POST("isCardExist.do")
    Observable<ResultObjectBean> isCardExist(@Query("bc_no") String bc_no);
    //通过银行卡号查询银行卡
    @POST("getBankCardFromBcno.do")
    Observable<GetBCResult> getBankCardFromBcno(@Query("bc_no") String bc_no);
    //获取银行卡绑定账户信息
    @POST("getAcFromBcno.do")
    Observable<GetAccountFromBcnoResult> getAcnameFromBcno(@Query("bc_no") String bc_no);
    //转账
    @POST("addTransfer.do")
    Observable<ResultObjectBean> addTransfer(@Body Transfer transfer);
    //获取所有轮播图资源
    @GET("getAllChart.do")
    Observable<GetChartResult> getAllChart();
    //获取所有商品列表
    @GET("getAllGoods.do")
    Observable<GetGoodsResult> getAllGoods();
    //通过商品名获得商品信息
    @POST("getGoodsFromName.do")
    Observable<GetGoodsFrNaResult> getGoodsFromName(@Query("goods_name")String goods_name);
    //添加订单信息
    @POST("addOrdertable.do")
    Observable<ResultObjectBean> addOrdertable(@Body Ordertable ordertable);
    //生成取货码
    @POST("addGoodsCode.do")
    Observable<ResultObjectBean> addGoodsCode(@Body GoodsCode goodsCode);
    //获取账户下的取货码
    @POST("getCodeFromAcid.do")
    Observable<GetCodesResult> getCodesFromAcid(@Query("account_id") int account_id);
    //确认收货码
    @POST("updateGoodsCode.do")
    Observable<ResultObjectBean> updateGoodsCode(@Query("goods_code") String goods_code);
    //删除收货码
    @POST("deleteGoodsCode.do")
    Observable<ResultObjectBean> deleteGoodsCode(@Query("goods_code") String goods_code);
    //获取所有信息公告
    @GET("getAllInformation.do")
    Observable<GetInfoResult> getAllInfo();
    //根据公告名称获取公告信息
    @POST("getInfoFromTitle.do")
    Observable<GetInfoDetailResult> getInfoFromTitle(@Query("info_title") String info_title);
    //获取账户的订单
    @POST("getOrderFromAid.do")
    Observable<GetOrderResult> getMyOrder(@Query("account_id") int account_id);
    //根据订单交易流水号查询订单数据
    @POST("getOrderFromOid.do")
    Observable<GetOrderDetailResult> getOrderDetail(@Query("order_id") String order_id);
    //查询账户转账记录
    @POST("getTransferFromAid.do")
    Observable<GetTransferResult> getMyTransfer(@Query("account_id") int account_id);
    //根据转账流水号查询转账详情
    @POST("getTransferFromTid.do")
    Observable<GetTransferDetailResult> getTransferDetail(@Query("transfer_id") String transfer_id);
    //查询账户总资产
    @POST("getAccountMoney.do")
    Observable<GetMoneyResult> getAccountMoney(@Query("account_id") int account_id);
    //查询近一个月支出总额
    @POST("getMonthMoney.do")
    Observable<GetMoneyResult> getMonthMoney(@Query("account_id") int account_id);
    //查询近7次支出记录
    @POST("getWeekPay.do")
    Observable<GetSevenPayResult> getSevenPay(@Query("account_id") int account_id);
    //查询当天收支信息
    @POST("getOneDayPay.do")
    Observable<GetPayResult> getOneDayPay(@Query("account_id") int account_id);
    //查询近一周收支信息
    @POST("getOneWeekPay.do")
    Observable<GetPayResult> getOneWeekPay(@Query("account_id") int account_id);
    //查询近一周收支信息
    @POST("getOneMonthPay.do")
    Observable<GetPayResult> getOneMonthPay(@Query("account_id") int account_id);
    //查询时间范围收支信息
    @FormUrlEncoded
    @POST("getCustomizeDatePay.do")
    Observable<GetPayResult> getCustomizeDatePay(@FieldMap Map<String,Object> params);
    //查询月收入
    @POST("getMonthSum.do")
    Observable<GetMonthSumResult> getMonthSum(@Query("account_id") int account_id);
    //查询月支出
    @POST("getMonthExp.do")
    Observable<GetMonthSumResult> getMonthExp(@Query("account_id") int account_id);
    //查询年收入
    @POST("getYearSum.do")
    Observable<GetYearDataResult> getYearSum(@Query("account_id") int account_id);
    //查询年支出
    @POST("getYearExp.do")
    Observable<GetYearDataResult> getYearExp(@Query("account_id") int account_id);
    //根据手机号查询手机号信息
    @POST("getPhoneFromNum.do")
    Observable<GetPhoneResult> getPhone(@Query("phone_number") String phone_number);
    //跟新手机余额信息
    @POST("updatePhone.do")
    Observable<ResultObjectBean> updatePhone(@Query("phone_number") String phone_number,@Query("phone_money") double phone_money);
    //获取银行卡的收支信息
    @POST("getCardPay.do")
    Observable<GetPayResult> getCardPay(@Query("card_no") String card_no);
    //绑定银行卡
    @POST("bindBankCard.do")
    Observable<ResultObjectBean> bindBankCard(@Body BankCard bankCard);
    //解绑银行卡
    @POST("unBindBankCard.do")
    Observable<ResultObjectBean> UnbindBankCard(@Body BankCard bankCard);
    //根据名称搜索
    @POST("getGoodsWithName.do")
    Observable<GetGoodsResult> getGoodsWithName(@Query("goods_name") String goods_name);
    //根据标题搜索
    @POST("getInfoWithTitle.do")
    Observable<GetInfoResult> getInfoWithTitle(@Query("info_title") String info_title);
    //添加反馈信息
    @POST("addFeedBack.do")
    Observable<ResultObjectBean> addFeedBack(@Body FeedBack feedBack);
    //修改密码
    @POST("modifyPwd.do")
    Observable<UpdateInfoResult> modifyPwd(@Body Account account);
}
