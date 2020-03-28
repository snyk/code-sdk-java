package ai.deepcode.javaclient.requests;

import java.util.Map;

public class FileHashRequest {
  private Map<String,String> files;

  public FileHashRequest(Map<String,String> files) {
    super();
    this.files = files;
  }

  public Map<String,String> getFiles() {
    return files;
  }
}
