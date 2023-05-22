import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CsrfToken {
    private static final Base64.Encoder encoder =
            Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder decoder =
            Base64.getUrlDecoder();

    public static String csrfToken(String id) {
        return encode(sha256(id));
    }

    public static boolean matches(String id, String token) {
        var expected = CsrfToken.sha256(id);
        var actual = CsrfToken.decode(token);
        return MessageDigest.isEqual(expected, actual);
    }

    private static byte[] sha256(String tokenId) {
        try {
            var sha256 = MessageDigest.getInstance("SHA-256");
            return sha256.digest(
                    tokenId.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String encode(byte[] data) {
        return encoder.encodeToString(data);
    }

    private static byte[] decode(String encoded) {
        return decoder.decode(encoded);
    }
}
