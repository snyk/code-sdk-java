package ai.deepcode.javaclient.requests;

import java.util.List;

public class FileContentRequest {
  private List<FileContent> files = null;

  public FileContentRequest(List<FileContent> files) {
    super();
    this.files = files;
  }

  public List<FileContent> getFiles() {
    return files;
  }
}
