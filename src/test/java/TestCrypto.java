import com.pl.sql.CryptoImpl;
import com.pl.sql.CryptoStringDelegator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestCrypto {
   private final static byte[] A_KEY =
         {0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00, (byte) 0xff, (byte) 0xee,
               (byte) 0xdd, (byte) 0xcc, (byte) 0xbb, (byte) 0xaa, (byte) 0x99, (byte) 0x88};
   private final static byte[] B_KEY =
         {(byte) 0xef, (byte) 0xcd, (byte) 0xab, (byte) 0x89, 0x67, 0x45, 0x23, 0x01,
               0x10, 0x32, 0x54, 0x76, (byte) 0x98, (byte) 0xba, (byte) 0xdc, (byte) 0xfe};

   private CryptoStringDelegator crypto = new CryptoStringDelegator(new CryptoImpl());

   @Test
   public void shouldCryptAndDecryptMessage() {
      // Given
      String text = "Hello world! Hello world! Hello world!";

      // When
      crypto.expandKeys(A_KEY, B_KEY);
      byte[] crypted = crypto.encrypt(text);
      String decrypted = crypto.decrypt(crypted);

      // Then
      assertThat(decrypted, is(text));
   }

   @Test
   public void shouldCryptAndDecrypt255UnicodeVarchar() {
      // Given
      String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
            + "Suspendisse sed felis sed libero posuere sodales. Etiam viverra fusce.";

      // When
      crypto.expandKeys(A_KEY, B_KEY);
      byte[] crypted = crypto.encrypt(text);
      String decrypted = crypto.decrypt(crypted);

      // Then
      assertThat(decrypted, is(text));
   }

}
