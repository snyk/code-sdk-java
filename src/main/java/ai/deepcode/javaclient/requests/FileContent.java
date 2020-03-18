package ai.deepcode.javaclient.requests;

public class FileContent {
  private String filePath;
  private String fileContent;

  public FileContent(String filePath, String fileContent) {
    super();
    this.filePath = filePath;
    this.fileContent = fileContent;
  }

  public String getFilePath() {
    return filePath;
  }

  public String getFileContent() {
    return fileContent;
  }
}
