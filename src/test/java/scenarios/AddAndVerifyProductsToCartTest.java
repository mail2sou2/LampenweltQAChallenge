package scenarios;

import base.TestBase;
import com.aventstack.extentreports.Status;
import components.*;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import util.Action;
import util.CustomListeners;
import util.Reporting;
import util.TestUtil;

import java.io.IOException;

@Listeners(CustomListeners.class)
public class AddAndVerifyProductsToCartTest extends TestBase {

    Home home = new Home();
    ProductPage productPage = new ProductPage();
    CartPage cartPage = new CartPage();
    AddNewProductPage addAnotherProduct = new AddNewProductPage();
    UpdatedCartPage updatedCartPage = new UpdatedCartPage();
    public double newCartTotal, totalAmount;

    @Test(priority = 1)
    public void navigateToHomeAndVerifyTitle() throws IOException {
        Action.navigateToURL(TestUtil.getProperty("baseUrl"));
        if (home.getTheTitle().equals("Lampen & Leuchten für Ihr Zuhause | Lampenwelt.de"))
            Reporting.report("Title is validated successfully", Status.PASS);
        else {
            Reporting.report("Title is validation failed", Status.FAIL);
            Assert.fail("Title is validation failed");
        }
    }

    @Test(priority = 2)
    public void addSomeProducts() throws IOException, ParseException {
        home.acceptCookie();
        home.navigateToProductPage();
        productPage.getAllProducts();
        Reporting.report("Few Products added", Status.PASS);
    }

    @Test(priority = 3)
    public void verifyProductsInCart() {
        cartPage.getAllCartProducts();
        if (productPage.map.equals(cartPage.map1))
            Reporting.report("Products in the cart along with name, product id, price and quantity are validated successfully", Status.PASS);
        else {
            Reporting.report("Products in the cart are not similar to the ones added to the cart", Status.FAIL);
            Assert.fail("Products in the cart are not similar to the ones added to the cart");
        }
        if (cartPage.getCartTotal() == Math.round(cartPage.netCartAmount * 100.0) / 100.0)
            Reporting.report("Cart subtotal is proper", Status.PASS);
        else {
            Reporting.report("Cart subtotal is not matching", Status.FAIL);
            Assert.fail("Cart subtotal is not matching");
        }
        if (cartPage.getGrandTotalAmount() == Math.round(cartPage.grandTotal * 100.0) / 100.0)
            Reporting.report("Total amount of cart is matching", Status.PASS);
        else {
            Reporting.report("Total amount of cart is not matching", Status.FAIL);
            Assert.fail("Total amount of cart is not matching");
        }
    }

    @Test(priority = 4)
    public void deleteProductFromCart() {
        cartPage.deleteAProductFromCart();
        if (cartPage.getCartTotal() < 100) {
            totalAmount = cartPage.netCartAmount - cartPage.priceFirstProduct + cartPage.getShippingTotal();
            newCartTotal = totalAmount;
        } else {
            if (cartPage.getShippingTotal() == 0)
                Reporting.report("Cart value is more than 100 Euros and so shipping total is 0 and successfully validated", Status.PASS);
            else {
                Reporting.report("Cart value is more than 100 Euros but still shipping is charged", Status.FAIL);
                Assert.fail("Cart value is more than 100 Euros but still shipping is charged");
            }
            newCartTotal = cartPage.netCartAmount - cartPage.priceFirstProduct;
            totalAmount = newCartTotal;
        }
        if (cartPage.getCartTotal() == Math.round(newCartTotal * 100.0) / 100.0)
            Reporting.report("Cart sub total is proper after deleting a product", Status.PASS);
        else {
            Reporting.report("Cart sub total is not matching after deleting a product", Status.FAIL);
            Assert.fail("Cart sub total is not matching after deleting a product");
        }
        if (cartPage.getGrandTotalAmount() == Math.round(totalAmount * 100.0) / 100.0)
            Reporting.report("Cart total amount is proper after deleting a product", Status.PASS);
        else {
            Reporting.report("Cart total amount is not matching after deleting a product", Status.FAIL);
            Assert.fail("Cart total amount is not matching after deleting a product");
        }
    }

    @Test(priority = 5)
    public void updateProductQuantity() {
        cartPage.updateAProductInCart();
        newCartTotal = newCartTotal + (cartPage.priceFirstProduct * (Integer.parseInt(cartPage.newQuantity))) - cartPage.priceFirstProduct;
        if (cartPage.getCartTotal() < 100) {
            totalAmount = newCartTotal + cartPage.getShippingTotal();
        } else {
            if (cartPage.getShippingTotal() == 0)
                Reporting.report("Cart value is more than 100 Euros and so shipping total is 0 and successfully validated", Status.PASS);
            else {
                Reporting.report("Cart value is more than 100 Euros but still shipping is charged", Status.FAIL);
                Assert.fail("Cart value is more than 100 Euros but still shipping is charged");
            }
            totalAmount = newCartTotal;
        }
        if (cartPage.getCartTotal() == Math.round(newCartTotal * 100.0) / 100.0)
            Reporting.report("Cart sub total is proper after updating product quantity", Status.PASS);
        else {
            Reporting.report("Cart sub total is not matching after updating product quantity", Status.FAIL);
            Assert.fail("Cart sub total is not matching after updating product quantity");
        }
        if (cartPage.getGrandTotalAmount() == Math.round(totalAmount * 100.0) / 100.0)
            Reporting.report("Cart total amount is proper after updating product quantity", Status.PASS);
        else {
            Reporting.report("Cart total amount is not matching after updating product quantity", Status.FAIL);
            Assert.fail("Cart total amount is not matching after updating product quantity");
        }
    }

    @Test(priority = 6)
    public void newProductAdd() throws IOException, ParseException {
        cartPage.navigateToProductPage();
        cartPage.addANewProduct();
        addAnotherProduct.navigateToWarenkorb();
        updatedCartPage.getUpdatedProducts();
        if (cartPage.updatedCartMap.equals(updatedCartPage.newCartMap))
            Reporting.report("Updated cart along with name, product id, price and quantity are validated successfully", Status.PASS);
        else {
            Reporting.report("Updated cart does not match the products selected by the user", Status.FAIL);
            Assert.fail("Updated cart does not match the products selected by the user");
        }
        if (updatedCartPage.getCartTotal() == Math.round(updatedCartPage.netCartAmount * 100.0) / 100.0)
            Reporting.report("Cart subtotal after updating the cart is proper", Status.PASS);
        else {
            Reporting.report("Cart subtotal after updating the cart is not matching", Status.FAIL);
            Assert.fail("Cart subtotal after updating the cart is not matching");
        }
        if (updatedCartPage.getGrandTotalAmount() == Math.round(updatedCartPage.grandTotal * 100.0) / 100.0)
            Reporting.report("Total amount of cart post cart update is proper", Status.PASS);
        else {
            Reporting.report("Total amount of cart post cart update is not matching", Status.FAIL);
            Assert.fail("Total amount of cart post cart update is not matching");
        }
    }
}
