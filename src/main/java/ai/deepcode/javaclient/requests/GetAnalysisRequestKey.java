package ai.deepcode.javaclient.requests;

import java.util.Objects;

public class GetAnalysisRequestKey {
    private String type = "File";
    private String hash;

    public GetAnalysisRequestKey(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetAnalysisRequestKey that = (GetAnalysisRequestKey) o;
        return Objects.equals(type, that.type) && Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, hash);
    }

    @Override
    public String toString() {
        return "GetAnalysisRequestKey{" +
                "type='" + type + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public String getHash() {
        return hash;
    }
}
