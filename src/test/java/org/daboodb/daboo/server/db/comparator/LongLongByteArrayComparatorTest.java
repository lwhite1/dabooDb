package org.daboodb.daboo.server.db.comparator;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 * Tests LongLongByteArrayComparator
 */
public class LongLongByteArrayComparatorTest {

  LongLongByteArrayComparator comparator = LongLongByteArrayComparator.INSTANCE;

  // we will test using datetimes

  @Test
  public void testCompareEquals() {

    LocalDateTime localDateTime1A = LocalDateTime.now();
    LocalDateTime localDateTime1B = localDateTime1A;

    byte[] ldt1ABytes = convert(localDateTime1A, new byte[16]);
    byte[] ldt1BBytes = convert(localDateTime1B, new byte[16]);

    assertTrue(localDateTime1A.compareTo(localDateTime1B) == comparator.compare(ldt1ABytes, ldt1BBytes));
  }

  @Test
  public void testCompare1() {

    LocalDateTime localDateTime1A = LocalDateTime.now();
    LocalDateTime localDateTime1B = localDateTime1A.minusDays(2);

    byte[] ldt1ABytes = convert(localDateTime1A, new byte[16]);
    byte[] ldt1BBytes = convert(localDateTime1B, new byte[16]);

    int stdResult = localDateTime1A.compareTo(localDateTime1B);
    int othResult = comparator.compare(ldt1ABytes, ldt1BBytes);
    assertTrue(Integer.signum(stdResult) ==  Integer.signum(othResult));
  }
  
  @Test
  public void testCompare2() {

    LocalDateTime localDateTime1A = LocalDateTime.now();
    LocalDateTime localDateTime1B = localDateTime1A.plusDays(10000000);

    byte[] ldt1ABytes = convert(localDateTime1A, new byte[16]);
    byte[] ldt1BBytes = convert(localDateTime1B, new byte[16]);

    int stdResult = localDateTime1A.compareTo(localDateTime1B);
    int othResult = comparator.compare(ldt1ABytes, ldt1BBytes);
    assertTrue(Integer.signum(stdResult) ==  Integer.signum(othResult));
  }

  @Test
  public void testCompare3() {

    LocalDateTime localDateTime1A = LocalDateTime.now();
    LocalDateTime localDateTime1B = localDateTime1A.plusNanos(100);

    byte[] ldt1ABytes = convert(localDateTime1A, new byte[16]);
    byte[] ldt1BBytes = convert(localDateTime1B, new byte[16]);

    int stdResult = localDateTime1A.compareTo(localDateTime1B);
    int othResult = comparator.compare(ldt1ABytes, ldt1BBytes);
    assertTrue(Integer.signum(stdResult) ==  Integer.signum(othResult));
  }

  @Test
  public void testCompare4() {

    LocalDateTime localDateTime1A = LocalDateTime.now();
    LocalDateTime localDateTime1B = localDateTime1A.minusNanos(100);

    byte[] ldt1ABytes = convert(localDateTime1A, new byte[16]);
    byte[] ldt1BBytes = convert(localDateTime1B, new byte[16]);

    int stdResult = localDateTime1A.compareTo(localDateTime1B);
    int othResult = comparator.compare(ldt1ABytes, ldt1BBytes);
    assertTrue(Integer.signum(stdResult) ==  Integer.signum(othResult));
  }

  private byte[] convert(LocalDateTime ldt, byte[] destination) {
    long date = ldt.toLocalDate().toEpochDay();
    long time = ldt.toLocalTime().toNanoOfDay();

    return Bytes.concat(Longs.toByteArray(date), Longs.toByteArray(time));
  }
}