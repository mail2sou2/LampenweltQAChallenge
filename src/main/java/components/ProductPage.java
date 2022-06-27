package components;

import base.TestBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import util.Action;
import util.ProductDetails;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductPage extends TestBase {

    String productName, selectedProductPrice, selectedQuantity, proId;
    public Map<String, ProductDetails> map = new HashMap();

    By productTitle = By.xpath("//h1");
    By productPrice = By.xpath("(//span[@class='price']/span)[2]");
    By productQuantity = By.xpath("//select[@id='qty']");
    By inDenWarenkorb = By.xpath("//span[text()='In den Warenkorb']");
    By warenkorb = By.xpath("//span[text()='Warenkorb']/ancestor::a");
    By prodId = By.xpath("//th[text()='Artikelnummer']/parent::tr/td");
    By led = By.xpath("(//span[text()='LED'])[1]/parent::a");
    By led_Aussenleuchten = By.xpath("//span[text()='LED-Außenleuchten']");


    public CartPage getAllProducts() throws IOException, ParseException {
        for (int i = 1; i <= noOfProducts; i++) {
            By firstProduct = By.xpath("(//div[@class='product-info -flow'])[" + i + "]/a");
            Action.click(firstProduct);
            proId = Action.getTextEle(prodId);
            proId = proId.trim();
            productName = Action.getTextEle(productTitle);
            selectedProductPrice = Action.getTextEle(productPrice);
            selectedProductPrice = selectedProductPrice.replace(',', '.');
            Select quantity = new Select(driver.findElement(productQuantity));
            quantity.selectByValue("1");
            selectedQuantity = quantity.getFirstSelectedOption().getText();
            Action.click(inDenWarenkorb);
            navigateToProductPage();
            getValues(map);
        }
        JSONObject json = new JSONObject(map);
        Action.click(warenkorb);
        return new CartPage();
    }

    public void getValues(Map<String, ProductDetails> map) {
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductID((proId));
        productDetails.setProductPrice(selectedProductPrice);
        productDetails.setProductName(productName);
        productDetails.setProductQuantity(selectedQuantity);
        map.put(proId, productDetails);
    }

    public void navigateToProductPage() throws IOException, ParseException {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(led)).perform();
        Action.explicitWaitElementToBeClickable(led_Aussenleuchten);
        actions.moveToElement(driver.findElement(led_Aussenleuchten)).perform();
        Action.click(led_Aussenleuchten);
    }

}
