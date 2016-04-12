package org.dabudb.dabu.shared.encryption;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Returns a compressor of the type specified in the input
 */
public class EncryptorFactory {

  public static final EncryptorDecryptor NONE = NullEncryptor.get();

  /**
   * Returns a compressor of the type specified in the input
   */
  public static EncryptorDecryptor get(EncryptionType type, String password) {

    Preconditions.checkState(type == EncryptionType.NONE || !Strings.isNullOrEmpty(password));

    switch (type) {
      // TODO(lwhite): instantiate the encryptor using a pool like the underlying algorithm does
      case STANDARD:
        return new StandardTextEncryptor(password);
      case NONE:
        return NONE;
      default:
        throw new RuntimeException("No Encryptor available for specified type: " + type.name());
    }
  }
}
