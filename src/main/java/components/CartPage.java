package components;

import base.TestBase;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import util.Action;
import util.ProductDetails;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartPage extends TestBase {

    String fetchedCartProductName, fetchedCartProductPrice, fetchedCartQuantity, fetchedCartProId;
    public double netCartAmount, grandTotal;
    double cartTotal, shippingTotal, getGrandTotal;
    public double priceFirstProduct;
    public String newQuantity = "2";

    AddNewProductPage addNewProductPage = new AddNewProductPage();
    public Map<String, ProductDetails> map1 = new HashMap();
    public Map<String, ProductDetails> updatedCartMap = new HashMap();
    public Map<String, ProductDetails> productMap = new HashMap();

    By zwischensumme = By.xpath("//tr[@class='total-subtotal']//span");
    By versandkosten = By.xpath("//tr[@class='total-shipping']//span");
    By gesamtsumme = By.xpath("//tr[@class='total-grand_total']//span[@class='price']");
    By artikelentfernen = By.xpath("(//a[contains(text(),'Artikel entfernen')])[1]");
    By updateProductQuantity = By.xpath("(//select[contains(@class,'qty js-qty-select')])[1]");
    By led = By.xpath("(//span[text()='LED'])[1]/parent::a");
    By led_Aussenleuchten = By.xpath("//span[text()='LED-Außenleuchten']");

    public void getAllCartProducts() {
        netCartAmount = 0;
        for (int j = 1; j <= noOfProducts; j++) {
            By cartProductName = By.xpath("(//div[@class='m-productRow__name']/a)[" + j + "]");
            By cartProductId = By.xpath("(//div/p[@class='m-productRow__sku'])[" + j + "]");
            By cartProductQuantity = By.xpath("(//div[@class='m-productRow__qty']/select[contains(@class,'qty js-qty-select')])[" + j + "]");
            fetchedCartProductName = Action.getTextEle(cartProductName);
            Action.explicitWaitElementToBeClickable(cartProductQuantity);
            Select quantity = new Select(driver.findElement(cartProductQuantity));
            fetchedCartQuantity = quantity.getFirstSelectedOption().getText();
            fetchedCartProId = Action.getTextEle(cartProductId);
            fetchedCartProId = (fetchedCartProId.split(" "))[1];
            By cartProductPrice = By.xpath("(//div[@data-sku='"+fetchedCartProId+"']//span[@class='price'])[2]");
            By cartProductRegularPrice = By.xpath("//div[@data-sku='"+fetchedCartProId+"']//span[@class='regular-price']/span[@class='price']");
            if(Action.isElementDisplayed(cartProductRegularPrice))
                fetchedCartProductPrice = Action.getTextEle(cartProductRegularPrice);
            else
                fetchedCartProductPrice = Action.getTextEle(cartProductPrice);
            fetchedCartProductPrice = (fetchedCartProductPrice.split(" "))[0];
            fetchedCartProductPrice = fetchedCartProductPrice.replace(',', '.');
            getCartDetails(map1);
            netCartAmount = netCartAmount + (Double.parseDouble(fetchedCartProductPrice));
        }
        if (netCartAmount < 100) {
            grandTotal = getShippingTotal() + netCartAmount;
        } else {
            grandTotal = netCartAmount;
        }
    }

    public double getShippingTotal() {
        shippingTotal = Double.parseDouble(Action.getTextEle(versandkosten).split(" ")[0].replace(',', '.'));
        return shippingTotal;
    }

    public double getCartTotal() {
        cartTotal = Double.parseDouble(Action.getTextEle(zwischensumme).split(" ")[0].replace(',', '.'));
        return cartTotal;
    }

    public double getGrandTotalAmount() {
        getGrandTotal = Double.parseDouble(Action.getTextEle(gesamtsumme).split(" ")[0].replace(',', '.'));
        return getGrandTotal;
    }

    public void updateAProductInCart() {
        By cartProductId = By.xpath("(//div/p[@class='m-productRow__sku'])[1]");
        fetchedCartProId = Action.getTextEle(cartProductId);
        fetchedCartProId = (fetchedCartProId.split(" "))[1];
        By cartProductPrice = By.xpath("(//div[@data-sku='"+fetchedCartProId+"']//span[@class='price'])[2]");
        By cartProductRegularPrice = By.xpath("//div[@data-sku='"+fetchedCartProId+"']//span[@class='regular-price']/span[@class='price']");
        if(Action.isElementDisplayed(cartProductRegularPrice))
            fetchedCartProductPrice = Action.getTextEle(cartProductRegularPrice);
        else
            fetchedCartProductPrice = Action.getTextEle(cartProductPrice);
        fetchedCartProductPrice = (fetchedCartProductPrice.split(" "))[0];
        fetchedCartProductPrice = fetchedCartProductPrice.replace(',', '.');
        priceFirstProduct=(Double.parseDouble(fetchedCartProductPrice));
        Action.explicitWaitElementToBeClickable(updateProductQuantity);
        Select updatedQuantity = new Select(driver.findElement(updateProductQuantity));
        updatedQuantity.selectByValue(newQuantity);
        updateMap();
    }

    public void deleteAProductFromCart() {
        By cartProductId = By.xpath("(//div/p[@class='m-productRow__sku'])[1]");
        fetchedCartProId = Action.getTextEle(cartProductId);
        fetchedCartProId = (fetchedCartProId.split(" "))[1];
        By cartProductPrice = By.xpath("(//div[@data-sku='"+fetchedCartProId+"']//span[@class='price'])[2]");
        By cartProductRegularPrice = By.xpath("//div[@data-sku='"+fetchedCartProId+"']//span[@class='regular-price']/span[@class='price']");
        if(Action.isElementDisplayed(cartProductRegularPrice))
            fetchedCartProductPrice = Action.getTextEle(cartProductRegularPrice);
        else
            fetchedCartProductPrice = Action.getTextEle(cartProductPrice);
        fetchedCartProductPrice = (fetchedCartProductPrice.split(" "))[0];
        fetchedCartProductPrice = fetchedCartProductPrice.replace(',', '.');
        priceFirstProduct=(Double.parseDouble(fetchedCartProductPrice));
        Action.click(artikelentfernen);
    }

    public void getCartDetails(Map<String, ProductDetails> map1) {
        ProductDetails productDetails1 = new ProductDetails();
        productDetails1.setProductID((fetchedCartProId));
        productDetails1.setProductPrice(fetchedCartProductPrice);
        productDetails1.setProductName(fetchedCartProductName);
        productDetails1.setProductQuantity(fetchedCartQuantity);
        map1.put(fetchedCartProId, productDetails1);
    }

    public AddNewProductPage navigateToProductPage() throws IOException, ParseException {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(led)).perform();
        Action.explicitWaitElementToBeClickable(led_Aussenleuchten);
        actions.moveToElement(driver.findElement(led_Aussenleuchten)).perform();
        Action.click(led_Aussenleuchten);
        return new AddNewProductPage();
    }

    public void updateMap() {
        By cartSize = By.xpath("(//div/p[@class='m-productRow__sku'])");
        int size = Action.getSizeEle(cartSize);
        for (int k = 1; k <= size; k++) {
            By cartProductName = By.xpath("(//div[@class='m-productRow__name']/a)[" + k + "]");
            By cartProductId = By.xpath("(//div/p[@class='m-productRow__sku'])[" + k + "]");
            By cartProductQuantity = By.xpath("(//select[contains(@class,'qty js-qty-select')])[" + k + "]");
            fetchedCartProductName = Action.getTextEle(cartProductName);
            Select quantity = new Select(driver.findElement(cartProductQuantity));
            fetchedCartQuantity = quantity.getFirstSelectedOption().getText();
            fetchedCartProId = Action.getTextEle(cartProductId);
            fetchedCartProId = (fetchedCartProId.split(" "))[1];
            By cartProductPrice = By.xpath("(//div[@data-sku='"+fetchedCartProId+"']//span[@class='price'])[2]");
            By cartProductRegularPrice = By.xpath("//div[@data-sku='"+fetchedCartProId+"']//span[@class='regular-price']/span[@class='price']");
            if(Action.isElementDisplayed(cartProductRegularPrice))
                fetchedCartProductPrice = Action.getTextEle(cartProductRegularPrice);
            else
                fetchedCartProductPrice = Action.getTextEle(cartProductPrice);
            fetchedCartProductPrice = (fetchedCartProductPrice.split(" "))[0];
            fetchedCartProductPrice = fetchedCartProductPrice.replace(',', '.');
            getCartDetails(updatedCartMap);
        }
    }

    public void addANewProduct() throws IOException, ParseException {
        addNewProductPage.addAnotherProduct(this);
    }

}
