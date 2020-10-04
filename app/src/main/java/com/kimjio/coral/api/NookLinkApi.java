package com.kimjio.coral.api;

import com.kimjio.coral.data.nook.LandProfile;
import com.kimjio.coral.data.nook.Message;
import com.kimjio.coral.data.nook.Reactions;
import com.kimjio.coral.data.nook.ResponseStatus;
import com.kimjio.coral.data.nook.Token;
import com.kimjio.coral.data.nook.TokenRequest;
import com.kimjio.coral.data.nook.UserProfile;
import com.kimjio.coral.data.nook.Users;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NookLinkApi {
    String NOOK_LINK = "https://web.sd.lp1.acbaa.srv.nintendo.net/";

    @GET(".")
    @Headers({
            "Accept: text/html, application/xhtml+xml, application/xml"
    })
    Observable<Response<Void>> getCookie(@Header("X-GameWebToken") String webServiceToken);

    @GET("api/sd/v1/users")
    Observable<Users> getUsers();

    @POST("api/sd/v1/auth_token")
    Observable<Token> getAuthToken(@Body TokenRequest request);

    @POST("api/sd/v1/messages")
    Observable<ResponseStatus> sendMessage(@Header("Authorization") String authorization, @Body Message message);

    @GET("api/sd/v1/users/{id}/profile")
    Observable<UserProfile> getUserProfile(@Header("Authorization") String authorization, @Path("id") String id, @Query("language") String language);

    @GET("api/sd/v1/lands/{id}/profile")
    Observable<LandProfile> getLandProfile(@Header("Authorization") String authorization, @Path("id") String id, @Query("language") String language);

    @GET("api/sd/v1/emoticons")
    Observable<Reactions> getReactions(@Header("Authorization") String authorization, @Query("language") String language);
}
