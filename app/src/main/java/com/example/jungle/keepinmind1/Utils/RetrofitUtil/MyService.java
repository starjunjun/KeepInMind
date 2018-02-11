package com.example.jungle.keepinmind1.Utils.RetrofitUtil;


import com.example.jungle.keepinmind1.Bean.GuPiaoBean;
import com.example.jungle.keepinmind1.Bean.GuPiaoContent;
import com.example.jungle.keepinmind1.Bean.JiJinBean;
import com.example.jungle.keepinmind1.Bean.ManageMoneyPassage;
import com.example.jungle.keepinmind1.Bean.RetrunJson;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


import okhttp3.Response;
import okhttp3.ResponseBody;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface MyService {

    String BASE_URL = "http://192.168.1.104:8080";  // 地址

    //    @FormUrlEncoded
//    @POST("buyer-cloth/cloth/get-seller-cloth")
//    Observable<ResultBean<String>> getData(@Field("token") String token, @Field("clothId") int clothId);
//
//    @FormUrlEncoded
//    @POST("buyer-cloth/collect-record")
//    Observable<ResultBean<Data>> getData(@Field("token") String token);
//
//    // 首页 - 获取当前登录用户及其所关注（授权）用户的最新微博
    @GET("/Test/getpassage")
    Observable<RetrunJson<List<ManageMoneyPassage>>> getpassage();

    @GET("/Test/getgupiao")
    Observable<RetrunJson<List<GuPiaoBean>>> getgupiao();

    @GET()
    Observable<RetrunJson<Map<String, JiJinBean>[]>> getJiJin(@Url String url);

    @GET("/Test/searchgupiao")
    Observable<RetrunJson<GuPiaoContent>> getGuPiaoByCode(@Query("code") String code);

    @GET("/Test/getad")
    Observable<RetrunJson<List<ManageMoneyPassage>>> getAd();

    @Multipart
    @POST("/Test/upload")
    Observable<RetrunJson<String>> upload(@Part("username") RequestBody username,
                                          @Part MultipartBody.Part file);


    @GET("Test/download")
    Observable<ResponseBody> download(@Query("username") String username);


    @FormUrlEncoded
    @POST("/Test/sign")
    Observable<RetrunJson<String>> sign(@Field("account") String account,@Field("password") String password);

    @FormUrlEncoded
    @POST("/Test/register")
    Observable<RetrunJson<String>> register(@Field("account") String account,@Field("password") String password,@Field("username") String username);

}
