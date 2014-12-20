package test;

import com.qmxtech.oggaudiodata.OggAudioData;
import java.lang.Exception;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Test {
  static private String TEST_PATH_RELATIVE = "test/test.ogg";
  public static void main(String[] args) {
    try {
      System.out.println("");
      System.out.println("Path string:");
      OggAudioData.showInfo(TEST_PATH_RELATIVE);
      System.out.println("");
      System.out.println("File handle:");
      OggAudioData.showInfo(new File(TEST_PATH_RELATIVE));
      System.out.println("");
      System.out.println("Input Stream (without size)");
      OggAudioData.showInfo((InputStream) new FileInputStream(TEST_PATH_RELATIVE));
      System.out.println("");
      System.out.println("Input Stream (with size):");
      OggAudioData.showInfo((InputStream) new FileInputStream(TEST_PATH_RELATIVE), (new File(TEST_PATH_RELATIVE)).length());
      System.out.println("");
      System.out.println("-------------------");
      System.out.println("- All systems go! -");
      System.out.println("-      Enjoy!     -");
      System.out.println("-------------------");
    } catch(Exception e) {
      System.out.println("");
      System.out.println("An exception was thrown...");
      System.out.println(e.getMessage() + "\n Caused by: " + e.getCause());
      e.printStackTrace();
      System.exit(1);
    } finally {
      System.exit(0);
    }
  }
}
