package enviro.assessment.grad001.MpumeleloNgozo.dto;

import enviro.assessment.grad001.MpumeleloNgozo.entity.Investor;
//import org.hibernate.mapping.List;

import java.util.*;

public class InvestorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<ProductDto> products;

    // Constructors, getters, and setters


    public InvestorDto(Long id, String firstName, String lastName, String email, List<ProductDto> products) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.products = products;
    }

    public InvestorDto(Investor investor, List<ProductDto> products) {
        this.id = investor.getId();
        this.firstName = investor.getFirstName();
        this.lastName = investor.getLastName();
        this.email = investor.getEmail();
        this.products = products;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}

