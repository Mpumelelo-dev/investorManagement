package enviro.assessment.grad001.MpumeleloNgozo.dto;

import enviro.assessment.grad001.MpumeleloNgozo.entity.Product;


public class ProductDto {
    private Long id;
    private String productID;
    private String type;
    private String name;
    private double balance;

    // Constructors, getters, and setters

    public ProductDto(Product product) {
        this.id = product.getId();
        this.productID = product.getProductID();
        this.type = product.getType();
        this.name = product.getName();
        this.balance = product.getBalance();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
