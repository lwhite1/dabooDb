package org.daboodb.daboo.shared.encryption;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Returns a compressor of the type specified in the input
 */
public class EncryptorFactory {

  public static final EncryptorDecryptor NONE = NullEncryptor.get();

  // holds the single instance of standard text encryptor for this database. All encryption and decryption must be done
  // with one algorithm/password
  private static EncryptorDecryptor standard;

  /**
   * Returns a compressor of the type specified in the input
   */
  public static EncryptorDecryptor get(EncryptionType type, String password) {

    Preconditions.checkState(type == EncryptionType.NONE || !Strings.isNullOrEmpty(password));

    switch (type) {
      case STANDARD:
        if (standard == null) {
          standard = new StandardTextEncryptor(password);
        }
        return standard;
      case NONE:
        return NONE;
      default:
        throw new RuntimeException("No Encryptor available for specified type: " + type.name());
    }
  }
}
