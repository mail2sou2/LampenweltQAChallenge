package components;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import util.Action;
import util.ProductDetails;

import java.util.HashMap;
import java.util.Map;

public class UpdatedCartPage extends TestBase {

    public Map<String, ProductDetails> newCartMap = new HashMap();
    public double netCartAmount, grandTotal;
    double cartTotal, shippingTotal, getGrandTotal;
    String fetchedCartProductName, fetchedCartProductPrice, fetchedCartQuantity, fetchedCartProId;
    By zwischensumme = By.xpath("//tr[@class='total-subtotal']//span");
    By versandkosten = By.xpath("//tr[@class='total-shipping']//span");
    By gesamtsumme = By.xpath("//tr[@class='total-grand_total']//span[@class='price']");

    public void getUpdatedProducts() {
        By cartSize = By.xpath("(//div/p[@class='m-productRow__sku'])");
        int size = Action.getSizeEle(cartSize);
        for (int k = 1; k < size + 1; k++) {
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
            getCartDetails(newCartMap);
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

    public void getCartDetails(Map<String, ProductDetails> map1) {
        ProductDetails productDetails1 = new ProductDetails();
        productDetails1.setProductID((fetchedCartProId));
        productDetails1.setProductPrice(fetchedCartProductPrice);
        productDetails1.setProductName(fetchedCartProductName);
        productDetails1.setProductQuantity(fetchedCartQuantity);
        map1.put(fetchedCartProId, productDetails1);
    }
}
