package util;

import base.TestBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class TestUtil extends TestBase {

    public static long Page_load_Timeout = 60;
    public static int Implicit_wait = 20;

    public static String getCurrentDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;
    }

    public static String getTimeStamp() {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return timeStamp;
    }

    public static String getProperty(String propertyName) throws IOException {
        Properties prop = new Properties();
        FileInputStream file;
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                file = new FileInputStream(System.getProperty("user.dir") + "\\config\\config.properties");
            } else {
                file = new FileInputStream(System.getProperty("user.dir") + "/config/config.properties");
            }
            prop.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String propVal = prop.getProperty(propertyName);
        return propVal;
    }

}
