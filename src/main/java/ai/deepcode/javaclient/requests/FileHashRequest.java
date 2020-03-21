package ai.deepcode.javaclient.requests;

import java.util.Map;

public class FileHashRequest {
  final Map<String,String> files;

  public FileHashRequest(Map<String, String> files) {
    this.files = files;
  }
}
