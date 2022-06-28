package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import util.Reporting;
import util.TestUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class TestBase implements ITestListener {

    public static WebDriver driver;
    public static Properties prop = new Properties();
    public static WebDriverWait wait;
    public static ExtentReports extent;
    public static ExtentTest extentTest;
    public int noOfProducts = 4;

    @BeforeSuite
    public static void ReportInitialization() {
        Reporting.startReport();
    }

    @BeforeTest
    public static void initialisation() throws IOException {

        FileInputStream fis;
        //select the OS and read the config file to choose browser
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            fis = new FileInputStream(System.getProperty("user.dir") + "\\config\\config.properties");
        } else {
            fis = new FileInputStream(System.getProperty("user.dir") + "/config/config.properties");
        }
        prop.load(fis);
        if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.default_content_settings.cookies", 2);
            options.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
        }
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }

    @AfterSuite
    public static void flush() {
        Reporting.flushReport();
    }

}
