package ai.deepcode.javaclient;

import ai.deepcode.javaclient.requests.FileContentRequest;
import ai.deepcode.javaclient.requests.FileHashRequest;
import ai.deepcode.javaclient.responses.CreateBundleResponse;
import ai.deepcode.javaclient.responses.GetAnalysisResponse;
import ai.deepcode.javaclient.responses.GetFiltersResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DeepCodeRestApi {
  /**
   * Creates a new bundle with direct file(s) source.
   *
   * @return {@link CreateBundleResponse} instance
   */
  @NotNull CreateBundleResponse createBundle(String token, FileContentRequest files);

  /**
   * Creates a new bundle for file(s) with Hash.
   *
   * @return {@link CreateBundleResponse} instance
   */
  @NotNull CreateBundleResponse createBundle(String token, FileHashRequest files);

  /**
   * Checks the status of a bundle.
   *
   * @param bundleId the parent bundle to extend
   * @return {@link CreateBundleResponse} instance
   */
  @NotNull CreateBundleResponse checkBundle(String token, String bundleId);

  /**
   * Creates a new bundle by extending a previously uploaded one.
   *
   * @param bundleId the parent bundle to extend
   * @return {@link CreateBundleResponse} instance
   */
  @NotNull <Req> CreateBundleResponse extendBundle(
    String token, String bundleId, Req request);

  /**
   * Starts a new bundle analysis or checks its current status and available results.
   *
   * @return {@link GetAnalysisResponse} instance}
   */
  @NotNull GetAnalysisResponse getAnalysis(
    String token,
    String bundleId,
    Integer severity,
    List<String> filesToAnalyse,
    String shard,
    String ideProductName,
    String orgDisplayName
  );

  /**
   * Requests current filtering options for uploaded bundles.
   *
   * @return {@link GetFiltersResponse} instance}
   */
  @NotNull GetFiltersResponse getFilters(String token);
}
