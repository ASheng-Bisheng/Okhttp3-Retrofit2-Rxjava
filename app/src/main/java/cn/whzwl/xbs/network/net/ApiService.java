package cn.whzwl.xbs.network.net;


import cn.whzwl.xbs.network.bean.BaseEntity;
import cn.whzwl.xbs.network.bean.Foods;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


/**
 * 接口定义
 */
public interface ApiService {
//    //Post
//    @FormUrlEncoded
//    @POST("/2.php")
//    Observable<BaseEntity<Result>> postTest(@Field("name") String name, @Field("password") String password);
//    //Get
//    @GET("/api/food/index")
//    Observable<BaseEntity<Food_library>> getFoot_lib(@Query("num") int num, @Query("page") int page, @Query("time") String time, @Query("token") String token);
//
    //下载
    @Streaming
    @GET()
    Observable<ResponseBody> download(@Url String Url);

    //上传
    @Multipart
    @POST("/api/log/uploadlog")
    Observable<BaseEntity> postUploadlog(@Query("time") String time, @Query("token") String token, @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("/api/food/foodlist")
        //获取菜谱
    Observable<BaseEntity<Foods>> postFoodList(@Field("time") String time, @Field("token") String token, @Field("order") String order);


}