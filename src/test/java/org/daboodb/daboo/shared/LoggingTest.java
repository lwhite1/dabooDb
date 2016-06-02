package org.daboodb.daboo.shared;

import org.daboodb.daboo.shared.util.logging.Log4jLogger;
import org.daboodb.daboo.shared.util.logging.LoggerWriter;
import org.daboodb.daboo.shared.util.logging.LoggingFactory;
import org.junit.Test;

/**
 *
 */
public class LoggingTest {

  LoggerWriter logger = LoggingFactory.getLogger(LoggingTest.class);

  @Test
  public void testLogging() {
    logger.logInfo("Hello Logger");
  }
}
