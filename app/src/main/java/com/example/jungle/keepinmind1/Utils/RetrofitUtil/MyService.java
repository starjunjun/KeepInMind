package com.example.jungle.keepinmind1.Utils.RetrofitUtil;


import com.example.jungle.keepinmind1.Bean.GuPiaoBean;
import com.example.jungle.keepinmind1.Bean.GuPiaoContent;
import com.example.jungle.keepinmind1.Bean.JiJinBean;
import com.example.jungle.keepinmind1.Bean.ManageMoneyPassage;
import com.example.jungle.keepinmind1.Bean.RetrunJson;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface MyService {

    String BASE_URL = "http://192.168.1.101:8080";  // 地址

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

}
