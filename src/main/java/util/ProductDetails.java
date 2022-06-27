package util;

public class ProductDetails {

    String productName, productPrice, productQuantity, productID;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productQuantity='" + productQuantity + '\'' +
                ", productID='" + productID + '\'' +
                '}';
    }

    // Overriding using hashCode() method

    @Override
    public int hashCode() {
        /* overriding hashCode() method
        to check the length of the names */
        return this.productID.length() % 10;
    }


    // Boolean function to check

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null
                || this.getClass() != obj.getClass())
            return false;
        ProductDetails p1 = (ProductDetails) obj;
        return this.productID.equals(p1.productID)
                && this.productName.equals(p1.productName) && this.productPrice.equals(p1.productPrice)
                && this.productQuantity.equals(p1.productQuantity);

    }

}
