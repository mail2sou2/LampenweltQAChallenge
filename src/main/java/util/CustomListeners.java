package util;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListeners extends Reporting implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        try {
            Reporting.createTest(result.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
    }

    @Override
    public void onFinish(ITestContext result) {
    }

    @Override
    public void onStart(ITestContext arg0) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
    }


    @Override
    public void onTestFailure(ITestResult result) {
        try {
            Reporting.getScreenshot(driver);
            Reporting.
                    report("Failed to identity Element, check screenshot for Reference " + result.
                                    getThrowable() + "  in TestMethodName " + result.getMethod().getMethodName(),
                            Status.FAIL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTestSkipped(ITestResult arg0) {
    }

}
