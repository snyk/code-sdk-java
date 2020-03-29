package ai.deepcode.javaclient.requests;

public class FileHash2ContentRequest {
  private String fileHash;
  private String fileContent;

  public FileHash2ContentRequest(String fileHash, String fileContent) {
    super();
    this.fileHash = fileHash;
    this.fileContent = fileContent;
  }

  public String getFileHash() {
    return fileHash;
  }

  public String getFileContent() {
    return fileContent;
  }
}
