package ai.deepcode.javaclient.responses;

import java.util.List;

public class CreateBundleResponse {
  private final String bundleId;
  private final List<String> missingFiles;
  private final String uploadURL;

  public CreateBundleResponse(String bundleId, List<String> missingFiles, String uploadURL) {
    this.bundleId = bundleId;
    this.missingFiles = missingFiles;
    this.uploadURL = uploadURL;
  }

  public String getBundleId() {
    return bundleId;
  }

  public List<String> getMissingFiles() {
    return missingFiles;
  }

  public String getUploadURL() {
    return uploadURL;
  }
}
