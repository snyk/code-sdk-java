package ai.deepcode.javaclient.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendBundleRequest {
    private final Map<String, Object> files;
    private final List<String> removedFiles;

    /**
     * @param files        filePath: fileHash
     * @param removedFiles List of FilePaths
     */
    public ExtendBundleRequest(Map<String, Object> files, List<String> removedFiles) {
        super();
        this.files = files;
        this.removedFiles = removedFiles;
    }

    public ExtendBundleRequest(FileHashRequest fileHashRequest, List<String> removedFiles) {
        this.files = new HashMap<>(fileHashRequest);
        this.removedFiles = removedFiles;
    }

    public Map<String, Object> getFiles() {
        return files;
    }

    public List<String> getRemovedFiles() {
        return removedFiles;
    }
}
