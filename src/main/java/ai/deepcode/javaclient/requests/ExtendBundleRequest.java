package ai.deepcode.javaclient.requests;

import java.util.List;
import java.util.Map;

public class ExtendBundleRequest implements IExtendBundleRequest {
  private Map<String,String> files;
  private List<String> removedFiles;

  /**
   * @param files filePath: fileHash
   * @param removedFiles List of FilePaths
   */
  public ExtendBundleRequest(Map<String, String> files, List<String> removedFiles) {
    super();
    this.files = files;
    this.removedFiles = removedFiles;
  }

  public Map<String,String> getFiles() {
    return files;
  }

  public List<String> getRemovedFiles() {
    return removedFiles;
  }
}
