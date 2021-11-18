package ai.deepcode.javaclient.requests;

import java.util.Objects;

public class FileHash2ContentRequest {
  private final String hash;
  private final String content;

  public FileHash2ContentRequest(String hash, String content) {
    super();
    this.hash = hash;
    this.content = content;
  }

  public String getHash() {
    return hash;
  }

  public String getContent() {
    return content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileHash2ContentRequest that = (FileHash2ContentRequest) o;
    return Objects.equals(hash, that.hash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash);
  }

  @Override
  public String toString() {
    return "FileHash2ContentRequest{" +
            "hash='" + hash + '\'' +
            '}';
  }
}
