package ai.deepcode.javaclient.requests;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExtendBundleWithContentRequest implements IExtendBundleRequest {
  private Map<String, FileHash2ContentRequest> files;
  private List<String> removedFiles;

  /**
   * @param files filePath: fileHash
   * @param removedFiles List of FilePaths
   */
  public ExtendBundleWithContentRequest(Map<String, FileHash2ContentRequest> files, List<String> removedFiles) {
    super();
    this.files = files;
    this.removedFiles = removedFiles;
  }

  public Map<String, FileHash2ContentRequest> getFiles() {
    return files;
  }

  public List<String> getRemovedFiles() {
    return removedFiles;
  }

  @Override
  public String toString() {
    return "ExtendBundleWithContentRequest{" +
            "files=" + files +
            ", removedFiles=" + removedFiles +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExtendBundleWithContentRequest that = (ExtendBundleWithContentRequest) o;
    return Objects.equals(files, that.files) && Objects.equals(removedFiles, that.removedFiles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(files, removedFiles);
  }
}
