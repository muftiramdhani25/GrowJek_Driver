package net.growdev.driverojekonline.network;

import net.growdev.driverojekonline.model.modelauth.ResponseAuth;
import net.growdev.driverojekonline.model.modelhistory.ResponseHistory;
import net.growdev.driverojekonline.model.modelmap.ResponseMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("daftar/2")
    Call<ResponseAuth> register(@Field("nama" )String nama,
                                @Field("email" )String email,
                                @Field("password") String password,
                                @Field("phone") String phone);

    @FormUrlEncoded
    @POST("login_driver")
    Call<ResponseAuth> login(@Field("device") String device,
                             @Field("f_email") String email,
                             @Field("f_password") String password);

    @GET("json")
    Call<ResponseMap> getDataMap(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("key") String key
    );

    //Take Booking/order
    @FormUrlEncoded
    @POST("take_booking")
    Call<ResponseHistory> takeBooking(
            @Field("id") String stridbooking,
            @Field("f_iddriver") String strIddriver,
            @Field("f_device") String strdevice,
            @Field("f_token") String strtoken
    );

    @FormUrlEncoded
    @POST("complete_booking")
    Call<ResponseHistory> completeBooking(
            @Field("f_idUser") String striduser,
            @Field("id") String strIdbooking,
            @Field("f_token") String f_token,
            @Field("f_device") String f_device
    );

    //history request
    @GET("get_request_booking")
    Call<ResponseHistory> getRequestHistory();

    //history proses
    @FormUrlEncoded
    @POST("get_handle_booking")
    Call<ResponseHistory> getProsesHistory(
            @Field("f_idUser") String striduser,
            @Field("f_device") String strdevice,
            @Field("f_token") String strtoken
    );
    //history complete
    @FormUrlEncoded
    @POST("get_complete_booking")
    Call<ResponseHistory> getCompleteHistory(
            @Field("f_idUser") String striduser,
            @Field("f_device") String strdevice,
            @Field("f_token") String strtoken
    );

    //send token to db
    @FormUrlEncoded
    @POST("registerGcm")
    Call<ResponseHistory> registerToken(
            @Field("f_idUser") String striduser,
            @Field("f_gcm") String strfcm
    );

    @FormUrlEncoded
    @POST("insert_posisi")
    Call<ResponseHistory> insertPosisiDriver(
            @Field("f_idUser") String striduser,
            @Field("f_lat") String lat,
            @Field("f_lng") String lng,
            @Field("f_device") String strdevice,
            @Field("f_token") String strtoken
    );


}
