/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ai.deepcode.javaclient;

import ai.deepcode.javaclient.requests.ExtendBundleRequest;
import ai.deepcode.javaclient.requests.FileContentRequest;
import ai.deepcode.javaclient.requests.FileHash2ContentRequest;
import ai.deepcode.javaclient.requests.FileHashRequest;
import ai.deepcode.javaclient.responses.CreateBundleResponse;
import ai.deepcode.javaclient.responses.EmptyResponse;
import ai.deepcode.javaclient.responses.GetAnalysisResponse;
import ai.deepcode.javaclient.responses.GetFiltersResponse;
import org.jetbrains.annotations.NotNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeepCodeRestApiTest {

  private final String testFileContent =
      "public class AnnotatorTest {\n"
          + "  public static void delay(long millis) {\n"
          + "    try {\n"
          + "      Thread.sleep(millis);\n"
          + "    } catch (InterruptedException e) {\n"
          + "      e.printStackTrace();\n"
          + "    }\n"
          + "  }\n"
          + "}\n";

  // !!! Will works only with already logged sessionToken
  private static final String loggedToken = System.getenv("SNYK_TOKEN");
  private static final String baseUrl = System.getenv("DEEPROXY_API_URL");

  private static String bundleId = null;

  @Test
  public void _022_setBaseUrl() {
    System.out.println("\n--------------Set base URL----------------\n");
    try {
      doSetBaseUrlTest("", 401);
      doSetBaseUrlTest("https://www.google.com/", 404);
      doSetBaseUrlTest("https://deeproxy.snyk.io/", 401);
    } finally {
      DeepCodeRestApi.setBaseUrl("");
    }
  }

  private void doSetBaseUrlTest(String baseUrl, int expectedStatusCode) {
    DeepCodeRestApi.setBaseUrl(baseUrl, false, true);
    EmptyResponse response = DeepCodeRestApi.checkBundle("blabla", "irrelevant");
    int status = response.getStatusCode();
    String description = response.getStatusDescription();
    System.out.printf(
        "Check Session call to [%3$s] with token [%1$s] return [%2$d] code: [%4$s]\n",
            "blabla", status, baseUrl, description);
    assertEquals(expectedStatusCode, status);
  }

  @Test
  public void _025_getFilters() {
    System.out.println("\n--------------Get Filters----------------\n");
    GetFiltersResponse response = DeepCodeRestApi.getFilters(loggedToken);
    assertNotNull(response);
    final String errorMsg =
        "Get Filters return status code: ["
            + response.getStatusCode()
            + "] "
            + response.getStatusDescription()
            + "\n";
    assertEquals(errorMsg, 200, response.getStatusCode());

    System.out.printf(
        "Get Filters call returns next filters: \nextensions: %1$s \nconfigFiles: %2$s\n",
        response.getExtensions(), response.getConfigFiles());
  }

  @Test
  public void _030_createBundle_from_source() throws NoSuchAlgorithmException {
    System.out.println("\n--------------Create Bundle from Source----------------\n");
    DeepCodeRestApi.setBaseUrl(baseUrl, false, true);
    CreateBundleResponse response = createBundleFromSource(loggedToken);
    assertNotNull(response);
    System.out.printf(
        "Create Bundle call return:\nStatus code [%1$d] %3$s \nBundleId: [%2$s]\n",
        response.getStatusCode(), response.getBundleHash(), response.getStatusDescription());
    assertEquals(200, response.getStatusCode());
    bundleId = response.getBundleHash();
  }

  @NotNull
  private CreateBundleResponse createBundleFromSource(String token) throws NoSuchAlgorithmException {
    FileContentRequest fileContent = new FileContentRequest();
    fileContent.put("/AnnotatorTest.java", new FileHash2ContentRequest(getHash(testFileContent), testFileContent));
    CreateBundleResponse response = DeepCodeRestApi.createBundle(token, fileContent);
    return response;
  }

  @Test
  public void _031_createBundle_wrong_request() throws NoSuchAlgorithmException {
    System.out.println("\n--------------Create Bundle with wrong requests----------------\n");
    final String brokenToken = "fff";
    CreateBundleResponse response = createBundleFromSource(brokenToken);
    assertNotNull(response);
    assertEquals(
        "Create Bundle call with malformed token should not be accepted by server",
        401,
        response.getStatusCode());
    System.out.printf(
        "Create Bundle call with malformed token [%1$s] is not accepted by server with Status code [%2$d].\n",
        brokenToken, response.getStatusCode());
  }

  @Test
  public void _035_createBundle_with_hash() {
    System.out.println("\n--------------Create Bundle with Hash----------------\n");
    CreateBundleResponse response = createBundleWithHash();
    assertEquals(200, response.getStatusCode());
  }

  @NotNull
  private CreateBundleResponse createBundleWithHash() {
    FileHashRequest files = createFileHashRequest(null);
    CreateBundleResponse response = DeepCodeRestApi.createBundle(loggedToken, files);
    assertNotNull(response);
    System.out.printf(
            "Create Bundle call return:\nStatus code [%1$d] %3$s \n bundleId: %2$s\n missingFiles: %4$s\n",
            response.getStatusCode(),
            response.getBundleHash(),
            response.getStatusDescription(),
            response.getMissingFiles());
    return response;
  }

  @Test
  public void _036_Check_Bundle() {
    System.out.println("\n--------------Check Bundle----------------\n");
    FileHashRequest fileHashRequest = createFileHashRequest(null);
    CreateBundleResponse createBundleResponse =
        DeepCodeRestApi.createBundle(loggedToken, fileHashRequest);
    assertNotNull(createBundleResponse);
    System.out.printf(
        "\nCreate Bundle call return:\nStatus code [%1$d] %3$s \n bundleId: %2$s\n missingFiles: %4$s\n",
        createBundleResponse.getStatusCode(),
        createBundleResponse.getBundleHash(),
        createBundleResponse.getStatusDescription(),
        createBundleResponse.getMissingFiles());
    assertEquals(200, createBundleResponse.getStatusCode());
    assertFalse("List of missingFiles is empty.", createBundleResponse.getMissingFiles().isEmpty());

    CreateBundleResponse checkBundleResponse =
        DeepCodeRestApi.checkBundle(loggedToken, createBundleResponse.getBundleHash());
    assertNotNull(checkBundleResponse);
    System.out.printf(
        "\nCheck Bundle call return:\nStatus code [%1$d] %3$s \n bundleId: %2$s\n missingFiles: %4$s\n",
        checkBundleResponse.getStatusCode(),
        checkBundleResponse.getBundleHash(),
        checkBundleResponse.getStatusDescription(),
        checkBundleResponse.getMissingFiles());
    assertEquals(200, checkBundleResponse.getStatusCode());
    assertFalse("List of missingFiles is empty.", checkBundleResponse.getMissingFiles().isEmpty());
    assertEquals(
        "Checked and returned bundleId's are different.",
        createBundleResponse.getBundleHash(),
        checkBundleResponse.getBundleHash());

    EmptyResponse uploadFileResponse = doUploadFile(createBundleResponse, fileHashRequest);

    assertNotNull(uploadFileResponse);
    System.out.printf(
        "\nUpload Files call for file %3$s \nStatus code [%1$d] %2$s\n",
        uploadFileResponse.getStatusCode(),
        uploadFileResponse.getStatusDescription(),
        createBundleResponse.getMissingFiles().get(0));
    assertEquals(200, uploadFileResponse.getStatusCode());

    CreateBundleResponse createBundleResponse1 =
        DeepCodeRestApi.checkBundle(loggedToken, createBundleResponse.getBundleHash());
    assertNotNull(createBundleResponse1);
    System.out.printf(
        "\nCheck Bundle call return:\nStatus code [%1$d] %3$s \n bundleId: %2$s\n missingFiles: %4$s\n",
        createBundleResponse1.getStatusCode(),
        createBundleResponse1.getBundleHash(),
        createBundleResponse1.getStatusDescription(),
        createBundleResponse1.getMissingFiles());
    assertEquals(200, createBundleResponse1.getStatusCode());
    assertTrue(
        "List of missingFiles is NOT empty.", createBundleResponse1.getMissingFiles().isEmpty());
    assertEquals(
        "Checked and returned bundleId's are different.",
        createBundleResponse.getBundleHash(),
        checkBundleResponse.getBundleHash());
  }

  private FileHashRequest createFileHashRequest(String fakeFileName) {
    DeepCodeRestApi.setBaseUrl(baseUrl, false, true);
    final File testFile =
        new File(getClass().getClassLoader().getResource("AnnotatorTest.java").getFile());
    String absolutePath = testFile.getAbsolutePath();
    String deepCodedPath =
        (absolutePath.startsWith("/") ? "" : "/")
            + ((fakeFileName == null)
                ? absolutePath
                : absolutePath.replace("AnnotatorTest.java", fakeFileName));
    System.out.printf("\nFile: %1$s\n", deepCodedPath);
    System.out.println("-----------------");

    // Append with System.currentTimeMillis() to make new Hash.
    try (FileOutputStream fos = new FileOutputStream(absolutePath, true)) {
      fos.write(String.valueOf(System.currentTimeMillis()).getBytes());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    String fileText;
    try {
      // ?? com.intellij.openapi.util.io.FileUtil#loadFile(java.io.File, java.nio.charset.Charset)
      fileText = Files.readString(Paths.get(absolutePath));
      String hash = getHash(fileText);
      System.out.printf("File hash: %1$s\n", hash);
      FileHashRequest fileHashRequest = new FileHashRequest();
      fileHashRequest.put(deepCodedPath, hash);
      return fileHashRequest;
    } catch (IOException | NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

  }

  @NotNull
  private String getHash(String fileText) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    System.out.println(fileText);
    System.out.println("-----------------");

    byte[] encodedhash = digest.digest(fileText.getBytes(StandardCharsets.UTF_8));
    String hash = bytesToHex(encodedhash);
    return hash;
  }

  @SuppressWarnings("DuplicatedCode") // in this test we explicitly allow it to test that hashing works
  private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }

  @Test
  public void _037_ExtendBundle() {
    System.out.println("\n--------------Extend Bundle----------------\n");
    CreateBundleResponse createBundleResponse = createBundleWithHash();
    assertEquals(200, createBundleResponse.getStatusCode());
    assertFalse("List of missingFiles is empty.", createBundleResponse.getMissingFiles().isEmpty());

    FileHashRequest newFileHashRequest = createFileHashRequest("test2.js");
    ExtendBundleRequest extendBundleRequest =
        new ExtendBundleRequest(newFileHashRequest, Collections.emptyList());
    CreateBundleResponse extendBundleResponse =
        DeepCodeRestApi.extendBundle(
            loggedToken, createBundleResponse.getBundleHash(), extendBundleRequest);
    assertNotNull(extendBundleResponse);
    System.out.printf(
        "Extend Bundle call return:\nStatus code [%1$d] %3$s \n bundleId: %2$s\n missingFiles: %4$s\n",
        extendBundleResponse.getStatusCode(),
        extendBundleResponse.getBundleHash(),
        extendBundleResponse.getStatusDescription(),
        extendBundleResponse.getMissingFiles());
    assertEquals(200, extendBundleResponse.getStatusCode());
    assertFalse("List of missingFiles is empty.", extendBundleResponse.getMissingFiles().isEmpty());
  }

  @Test
  public void _040_UploadFiles() {
    System.out.println("\n--------------Upload Files by Hash----------------\n");
    FileHashRequest fileHashRequest = createFileHashRequest(null);
    CreateBundleResponse createBundleResponse =
        DeepCodeRestApi.createBundle(loggedToken, fileHashRequest);
    assertNotNull(createBundleResponse);
    System.out.printf(
        "Create Bundle call return:\nStatus code [%1$d] %3$s \n bundleId: %2$s\n missingFiles: %4$s\n",
        createBundleResponse.getStatusCode(),
        createBundleResponse.getBundleHash(),
        createBundleResponse.getStatusDescription(),
        createBundleResponse.getMissingFiles());
    assertEquals(200, createBundleResponse.getStatusCode());
    assertFalse("List of missingFiles is empty.", createBundleResponse.getMissingFiles().isEmpty());

    EmptyResponse response = doUploadFile(createBundleResponse, fileHashRequest);

    assertNotNull(response);
    System.out.printf(
        "\nUpload Files call for file %3$s \nStatus code [%1$d] %2$s\n",
        response.getStatusCode(),
        response.getStatusDescription(),
        createBundleResponse.getMissingFiles().get(0));
    assertEquals(200, response.getStatusCode());
  }

  private EmptyResponse doUploadFile(
      CreateBundleResponse createBundleResponse, FileHashRequest fileHashRequest) {
    final File testFile =
        new File(Objects.requireNonNull(getClass().getClassLoader().getResource("AnnotatorTest.java")).getFile());
    final String absolutePath = testFile.getAbsolutePath();
    String fileText;
    try {
      // ?? com.intellij.openapi.util.io.FileUtil#loadFile(java.io.File, java.nio.charset.Charset)
      fileText = Files.readString(Paths.get(absolutePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    final String filePath = createBundleResponse.getMissingFiles().get(0);
    final String fileHash = fileHashRequest.get(filePath);
    Map<String, Object> map = new HashMap<>();
    map.put(filePath, new FileHash2ContentRequest(fileHash, fileText));
    ExtendBundleRequest ebr = new ExtendBundleRequest(map, Collections.emptyList());
    return DeepCodeRestApi.extendBundle(
        loggedToken, createBundleResponse.getBundleHash(), ebr);
  }

  @Test
  public void _090_getAnalysis() {
    System.out.println("\n--------------Get Analysis----------------\n");
    assertNotNull(
        "`bundleId` should be initialized at `_030_createBundle_from_source()`", bundleId);
    final String deepcodedFilePath =
        createFileHashRequest(null).keySet().stream().findFirst().orElseThrow();
    final List<String> analysedFiles = Collections.singletonList(deepcodedFilePath);
    GetAnalysisResponse response = DeepCodeRestApi.getAnalysis(loggedToken, bundleId, null, analysedFiles);
    assertAndPrintGetAnalysisResponse(response);
    boolean resultsEmpty = response.getSuggestions() == null || response.getSuggestions().isEmpty();
    assertFalse("Analysis results must not be empty", resultsEmpty);
    System.out.println("\n---- With `severity=2` param:\n");
    assertAndPrintGetAnalysisResponse(
        DeepCodeRestApi.getAnalysis(loggedToken, bundleId, 2, analysedFiles));
  }

  private void assertAndPrintGetAnalysisResponse(GetAnalysisResponse response) {
    assertNotNull(response);
    System.out.printf(
        "Get Analysis call for test file: \n-----------\n %1$s \n-----------\nreturns Status code: %2$s \n%3$s\n",
        testFileContent, response.getStatusCode(), response);
    assertEquals("COMPLETE", response.getStatus());
    assertEquals("Get Analysis request not succeed", 200, response.getStatusCode());
  }

//  This test would need a mock of SSLSocketFactory to verify the x509 manager. Commenting the test for now
//  @Test
//  public void setBaseUrl_shouldUseEmptyTrustManager_whenDisableSslVerificationIsTrue() {
//    DeepCodeRestApi.setBaseUrl(baseUrl, true);
//
//    EmptyResponse emptyResponse = DeepCodeRestApi.checkSession(loggedToken);
//
//    assertThat(emptyResponse, notNullValue());
//    assertThat(emptyResponse.getStatusCode(), equalTo(200));
//  }
}
