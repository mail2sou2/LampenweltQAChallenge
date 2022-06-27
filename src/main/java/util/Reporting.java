package util;

import base.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class Reporting extends TestBase {

    public static String dateStamp = TestUtil.getCurrentDate();
    public static String timeStamp = TestUtil.getTimeStamp();
    public static String reportPath = null;

    public static void startReport() {
        extent = new ExtentReports();
        String cwd = System.getProperty("user.dir");
        reportPath = cwd + "/Report/" + dateStamp + "/Report_" + timeStamp.replace(":", "_") + ".html";
        ExtentSparkReporter avent = new ExtentSparkReporter(reportPath);
        avent.config().setReportName("Test Automation Report");
        avent.config().setDocumentTitle("Test Automation Report");
        extent.attachReporter(avent);
        systemInfo();
    }

    public static void createTest(String test_name) {
        extentTest = extent.createTest(test_name);
    }

    public static void systemInfo() {
        extent.setSystemInfo("OS", "WIN10");
    }

    public static void report(String info, Status status) {
        switch (status) {
            case PASS:
                extentTest.pass(info, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase()).build());
                break;
            case FAIL:
                extentTest.fail(info, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase()).build());
                break;
            case INFO:
                extentTest.info(info, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase()).build());
                break;
            default:
                extentTest.log(Status.INFO, "Please Provide Correct Status");
        }
    }

    public static String getScreenshot(WebDriver driver) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File SrcFile =
                scrShot.getScreenshotAs(OutputType.FILE);
        String destination =
                System.getProperty("user.dir") + "/Screenshots/" +
                        TestUtil.getTimeStamp().replaceAll(":", "_") + ".png";
        File DestFile = new
                File(destination);
        FileHandler.copy(SrcFile, DestFile);
        return destination;
    }


    public static String getScreenshotAsBase64() throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/Screenshots/" + TestUtil.getTimeStamp().replaceAll(":", "_")
                + ".png";
        FileUtils.copyFile(source, new File(path));
        byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(path));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static String getBase() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    public static void flushReport() {
        extent.flush();
        File htmlFile = new File(reportPath);
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
