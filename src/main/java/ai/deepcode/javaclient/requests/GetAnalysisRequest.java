package ai.deepcode.javaclient.requests;

import java.util.List;
import java.util.Objects;

public class GetAnalysisRequest {
  private GetAnalysisKey key;
  private Integer severity;
  private boolean prioritized;
  private boolean legacy;

  /**
   * @param bundleHash
   * @param limitToFiles list of filePath
   * @param severity
   * @param shard uniq String (hash) per Project to optimize jobs on backend (run on the same worker to reuse caches)
   * @param prioritized
   * @param legacy
   */
  public GetAnalysisRequest(
    String bundleHash,
    List<String> limitToFiles,
    Integer severity,
    String shard,
    boolean prioritized,
    boolean legacy
  ) {
    this.key = new GetAnalysisKey(bundleHash, limitToFiles, shard);
    this.severity = severity;
    this.prioritized = prioritized;
    this.legacy = legacy;
  }

  public GetAnalysisRequest(String bundleHash, List<String> limitToFiles, Integer severity, String shard) {
    this(bundleHash, limitToFiles, severity, shard, false, true);
  }

  private static class GetAnalysisKey {
    private final String type = "file";
    private final String hash;
    private final List<String> limitToFiles;
    private final String shard;

    public GetAnalysisKey(String hash, List<String> limitToFiles, String shard) {
      this.hash = hash;
      this.limitToFiles = limitToFiles;
      this.shard = shard;
    }

    public String getHash() {
      return hash;
    }

    public List<String> getLimitToFiles() {
      return limitToFiles;
    }

    public String getShard() {
      return shard;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      GetAnalysisKey that = (GetAnalysisKey) o;
      return type.equals(that.type)
          && hash.equals(that.hash)
          && Objects.equals(limitToFiles, that.limitToFiles);
    }

    @Override
    public String toString() {
      return "GetAnalysisKey{"
          + "type='"
          + type
          + '\''
          + ", hash='"
          + hash
          + '\''
          + ", limitToFiles="
          + limitToFiles
          + '}';
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, hash, limitToFiles);
    }
  }
}
