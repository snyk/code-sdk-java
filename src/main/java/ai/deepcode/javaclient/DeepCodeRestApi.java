/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ai.deepcode.javaclient;

import ai.deepcode.javaclient.requests.*;
import ai.deepcode.javaclient.responses.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.io.IOException;
import java.util.List;

/**
 * https://deepcode.freshdesk.com/support/solutions/articles/60000346777-sessions
 * https://deepcode.freshdesk.com/support/solutions/articles/60000357438-bundles
 */
public final class DeepCodeRestApi {

  //  private static final Logger LOGGER = LoggerFactory.getLogger(DeepCodeRestApi.class);

  private DeepCodeRestApi() {}

  private static String API_URL = "https://www.deepcode.ai/";

  private static Retrofit retrofit = buildRetrofit(API_URL);

  // Create simple REST adapter which points the baseUrl.
  private static Retrofit buildRetrofit(String baseUrl) {
    return new Retrofit.Builder()
        .baseUrl(baseUrl + "publicapi/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  /**
   * Re-set baseUrl for retrofit instance
   *
   * @param baseUrl new baseUrl. <b>Null</b> or empty "" value will reset to default {@code
   *     #API_URL}
   */
  public static void setBaseUrl(@Nullable String baseUrl) {
    retrofit = buildRetrofit((baseUrl == null || baseUrl.isEmpty()) ? API_URL : baseUrl);
  }

  private interface LoginCall {
    @retrofit2.http.Headers("Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> doNewLogin();
  }

  /**
   * Requests the creation of a new login session.
   *
   * @return {@link LoginResponse} instance
   */
  @NotNull
  public static LoginResponse newLogin() {
    final LoginCall loginCall = retrofit.create(LoginCall.class);
    try {
      final Response<LoginResponse> retrofitResponse = loginCall.doNewLogin().execute();
      LoginResponse result = retrofitResponse.body();
      if (result == null) return new LoginResponse();
      result.setStatusCode(retrofitResponse.code());
      switch (retrofitResponse.code()) {
        case 200:
          result.setStatusDescription("The new login request was successful");
          break;
        default:
          result.setStatusDescription("Unknown Status Code: " + retrofitResponse.code());
          break;
      }
      return result;
    } catch (IOException e) {
      return new LoginResponse();
    }
  }

  private interface CheckSessionCall {
    @GET("session")
    Call<Void> doCheckSession(@Header("Session-Token") String token);
  }

  /**
   * Checks status of the login process.
   *
   * @return {@link EmptyResponse} instance
   */
  @NotNull
  public static EmptyResponse checkSession(String token) {
    CheckSessionCall checkSessionCall = retrofit.create(CheckSessionCall.class);
    final EmptyResponse result = new EmptyResponse();
    final Response<Void> retrofitResponse;
    try {
      retrofitResponse = checkSessionCall.doCheckSession(token).execute();
    } catch (IOException e) {
      return result;
    }
    result.setStatusCode(retrofitResponse.code());
    switch (retrofitResponse.code()) {
      case 200:
        result.setStatusDescription("The login process was successful");
        break;
      case 304:
        result.setStatusDescription("The login process has not been completed yet");
        break;
      case 401:
        result.setStatusDescription("Missing or invalid sessionToken");
        break;
      default:
        result.setStatusDescription("Unknown Status Code: " + retrofitResponse.code());
        break;
    }
    return result;
  }

  private interface CreateBundleCall {
    @retrofit2.http.Headers("Content-Type: application/json")
    @POST("bundle")
    Call<CreateBundleResponse> doCreateBundle(
        @Header("Session-Token") String token, @Body FileContentRequest files);

    @retrofit2.http.Headers("Content-Type: application/json")
    @POST("bundle")
    Call<CreateBundleResponse> doCreateBundle(
        @Header("Session-Token") String token, @Body FileHashRequest files);
  }

  private static <Req> CreateBundleResponse doCreateBundle(String token, Req request) {
    CreateBundleCall createBundleCall = retrofit.create(CreateBundleCall.class);
    Response<CreateBundleResponse> retrofitResponse;
    try {
      if (request instanceof FileContentRequest)
        retrofitResponse =
            createBundleCall.doCreateBundle(token, (FileContentRequest) request).execute();
      else if (request instanceof FileHashRequest)
        retrofitResponse =
            createBundleCall.doCreateBundle(token, (FileHashRequest) request).execute();
      else throw new IllegalArgumentException();
    } catch (IOException e) {
      return new CreateBundleResponse();
    }
    CreateBundleResponse result = retrofitResponse.body();
    if (result == null) {
      result = new CreateBundleResponse();
    }
    result.setStatusCode(retrofitResponse.code());
    switch (retrofitResponse.code()) {
      case 200:
        result.setStatusDescription("The bundle creation was successful");
        break;
      case 400:
        result.setStatusDescription("Request content doesn't match the specifications");
        break;
      case 401:
        result.setStatusDescription("Missing sessionToken or incomplete login process");
        break;
      case 403:
        result.setStatusDescription("Unauthorized access to requested repository");
        break;
      case 404:
        result.setStatusDescription("Unable to resolve requested oid");
        break;
      default:
        result.setStatusDescription("Unknown Status Code: " + retrofitResponse.code());
        break;
    }
    return result;
  }

  /**
   * Creates a new bundle with direct file(s) source at {@link FileContent}.
   *
   * @return {@link CreateBundleResponse} instance
   */
  @NotNull
  public static CreateBundleResponse createBundle(String token, FileContentRequest files) {
    return doCreateBundle(token, files);
  }

  /**
   * Creates a new bundle for file(s) with Hash.
   *
   * @return {@link CreateBundleResponse} instance
   */
  @NotNull
  public static CreateBundleResponse createBundle(String token, FileHashRequest files) {
    return doCreateBundle(token, files);
  }

  private interface ExtendBundleCall {
    @retrofit2.http.Headers("Content-Type: application/json")
    @PUT("bundle/{bundleId}")
    Call<CreateBundleResponse> doExtendBundle(
            @Header("Session-Token") String token,
            @Path(value = "bundleId", encoded = true) String bundleId,
            @Body ExtendBundleRequest extendBundleRequest);
  }

  /**
   * Creates a new bundle by extending a previously uploaded one.
   *
   * @param bundleId the parent bundle to extend
   * @return {@link CreateBundleResponse} instance
   */
  @NotNull
  public static CreateBundleResponse extendBundle(
      String token, String bundleId, ExtendBundleRequest extendBundleRequest) {
    ExtendBundleCall extendBundleCall = retrofit.create(ExtendBundleCall.class);
    Response<CreateBundleResponse> retrofitResponse;
    try {
      retrofitResponse =
          extendBundleCall.doExtendBundle(token, bundleId, extendBundleRequest).execute();
    } catch (IOException e) {
      return new CreateBundleResponse();
    }
    CreateBundleResponse result = retrofitResponse.body();
    if (result == null) {
      result = new CreateBundleResponse();
    }
    result.setStatusCode(retrofitResponse.code());
    switch (retrofitResponse.code()) {
      case 200:
        result.setStatusDescription("The bundle extension was successful");
        break;
      case 400:
        result.setStatusDescription("Attempted to extend a git bundle, or ended up with an empty bundle after the extension");
        break;
      case 401:
        result.setStatusDescription("Missing sessionToken or incomplete login process");
        break;
      case 403:
        result.setStatusDescription("Unauthorized access to parent bundle");
        break;
      case 404:
        result.setStatusDescription("Parent bundle has expired");
        break;
      case 413:
        result.setStatusDescription("Payload too large");
        break;
      default:
        result.setStatusDescription("Unknown Status Code: " + retrofitResponse.code());
        break;
    }
    return result;
  }

  private interface UploadFilesCall {
    @retrofit2.http.Headers("Content-Type: application/json;charset=utf-8")
    @POST("file/{bundleId}")
    Call<Void> doUploadFiles(
        @Header("Session-Token") String token,
        @Path(value = "bundleId", encoded = true) String bundleId,
        @Body List<FileHash2ContentRequest> listHash2Content);
  }

  /**
   * Uploads missing files to a bundle.
   *
   * @param token
   * @param bundleId
   * @param request List<FileHash2ContentRequest>
   * @return EmptyResponse with return code and description.
   */
  public static EmptyResponse UploadFiles(
      String token, String bundleId, List<FileHash2ContentRequest> request) {
    UploadFilesCall uploadFilesCall = retrofit.create(UploadFilesCall.class);
    Response<Void> retrofitResponse;
    try {
      retrofitResponse = uploadFilesCall.doUploadFiles(token, bundleId, request).execute();
    } catch (IOException e) {
      return new EmptyResponse();
    }
    EmptyResponse result = new EmptyResponse();
    result.setStatusCode(retrofitResponse.code());
    switch (retrofitResponse.code()) {
      case 200:
        result.setStatusDescription("Upload succeeded");
        break;
      case 400:
        result.setStatusDescription(
            "Content and hash mismatch or attempted to upload files to a git bundle");
        break;
      case 401:
        result.setStatusDescription("Missing sessionToken or incomplete login process");
        break;
      case 403:
        result.setStatusDescription("Unauthorized access to requested bundle");
        break;
      case 413:
        result.setStatusDescription("Payload too large");
        break;
      default:
        result.setStatusDescription("Unknown Status Code: " + retrofitResponse.code());
        break;
    }
    return result;
  }

  private interface GetAnalysisCall {
    //    @retrofit2.http.Headers("Content-Type: application/json")
    @GET("analysis/{bundleId}")
    Call<GetAnalysisResponse> doGetAnalysis(
        @Header("Session-Token") String token,
        @Path(value = "bundleId", encoded = true) String bundleId,
        @Query("severity") Integer severity,
        @QueryName String linters);
  }

  /**
   * Starts a new bundle analysis or checks its current status and available results.
   *
   * @return {@link GetAnalysisResponse} instance}
   */
  @NotNull
  public static GetAnalysisResponse getAnalysis(
      String token, String bundleId, Integer severity, boolean useLinters) {
    GetAnalysisCall getAnalysisCall = retrofit.create(GetAnalysisCall.class);
    try {
      Response<GetAnalysisResponse> retrofitResponse =
          getAnalysisCall
              .doGetAnalysis(token, bundleId, severity, (useLinters) ? "linters" : null)
              .execute();
      GetAnalysisResponse result = retrofitResponse.body();
      if (result == null) result = new GetAnalysisResponse();
      result.setStatusCode(retrofitResponse.code());
      switch (retrofitResponse.code()) {
        case 200:
          result.setStatusDescription("The analysis request was successful");
          break;
        case 401:
          result.setStatusDescription("Missing sessionToken or incomplete login process");
          break;
        case 403:
          result.setStatusDescription("Unauthorized access to requested repository");
          break;
        default:
          result.setStatusDescription("Unknown Status Code: " + retrofitResponse.code());
          break;
      }
      return result;
    } catch (IOException e) {
      return new GetAnalysisResponse();
    }
  }

  private interface GetFiltersCall {
    @GET("filters")
    Call<GetFiltersResponse> doGetFilters(@Header("Session-Token") String token);
  }

  /**
   * Requests current filtering options for uploaded bundles.
   *
   * @return {@link GetFiltersResponse} instance}
   */
  @NotNull
  public static GetFiltersResponse getFilters(String token) {
    GetFiltersCall getFiltersCall = retrofit.create(GetFiltersCall.class);
    try {
      Response<GetFiltersResponse> retrofitResponse = getFiltersCall.doGetFilters(token).execute();
      GetFiltersResponse result = retrofitResponse.body();
      if (result == null) result = new GetFiltersResponse();
      result.setStatusCode(retrofitResponse.code());
      switch (retrofitResponse.code()) {
        case 200:
          result.setStatusDescription("The filters request was successful");
          break;
        case 401:
          result.setStatusDescription("Missing sessionToken or incomplete login process");
          break;
        default:
          result.setStatusDescription("Unknown Status Code: " + retrofitResponse.code());
          break;
      }
      return result;
    } catch (IOException e) {
      return new GetFiltersResponse();
    }
  }
}
