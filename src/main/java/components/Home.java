package components;

import base.TestBase;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import util.Action;

import java.io.IOException;

public class Home extends TestBase {

    By cookie = By.xpath("//span[text()='Einverstanden']");
    By led = By.xpath("(//span[text()='LED'])[1]/parent::a");
    By led_Aussenleuchten = By.xpath("//span[text()='LED-Außenleuchten']");

    public String getTheTitle() {
        return driver.getTitle();
    }

    public void acceptCookie() {
        Action.click(cookie);
    }

    public ProductPage navigateToProductPage() throws IOException, ParseException {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(led)).perform();
        Action.explicitWaitElementToBeClickable(led_Aussenleuchten);
        actions.moveToElement(driver.findElement(led_Aussenleuchten)).perform();
        Action.click(led_Aussenleuchten);
        return new ProductPage();
    }

}
