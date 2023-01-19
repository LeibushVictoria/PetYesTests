package com.petyes.domain;

import com.petyes.api.*;
import com.petyes.config.App;
import com.petyes.pages.components.CalendarComponent;

import java.io.File;
import java.util.Date;

public class DataBuilder {
    public static String breederToken;
    public static String customerToken;
    public static String adminToken;
    public static int breeder_id;
    public static int customer_id;
    public static String categoryName = "autoTestCategory";
    public static int category_id;
    public static int article_id;
    public static String petName = "autoTestCat";
    public static int pet_id;
    public static int petForSale_id;
    public static int sale_id;
    public static int request_id;

    public DataBuilder() {
    }

    public String getBreederToken() {
        return breederToken;
    }

    public void setBreederToken() {
        Login login = new Login();
        breederToken = login.loginByAPI("7"+ App.config.breederPhoneNumber(), App.config.userPassword());
    }

    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken() {
        Login login = new Login();
        customerToken = login.loginByAPI("7"+ App.config.customerPhoneNumber(), App.config.userPassword());
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken() {
        Login login = new Login();
        adminToken = login.loginByAPI(App.config.adminPhoneNumber(), App.config.adminPassword());
    }

    public int getBreederId() {
        return breeder_id;
    }

    public void setBreederId() {
        Login login = new Login();
        breeder_id = login.getUserId(breederToken);
    }

    public int getCustomerId() {
        return customer_id;
    }

    public void setCustomerId() {
        Login login = new Login();
        customer_id = login.getUserId(customerToken);
    }

    public int getBlogCategoryId() {
        return category_id;
    }

    public void setBlogCategoryId() {
        Blog blog = new Blog();
        category_id = blog.addBlogCategoryByAPI(categoryName, "#666666");
    }

    public int getArticleId() {
        return article_id;
    }

    public void setArticleId() {
        Blog blog = new Blog();
        File file = new File("src/test/resources/pet_photo.jpg");
        article_id = blog.addArticleByAPI("autotest article", "autotest text", file,
                "autotest annotation", getBlogCategoryId(), 13);
    }

    public int getPetId() {
        return pet_id;
    }

    public void setPetId() {
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Date birth = calendarComponent.getOtherDate(-20);
        pet_id = pet.createPetByAPI(false, petName, birth, 0, 1);
    }

    public int getPetForSaleId() {
        return petForSale_id;
    }

    public void setPetForSaleId() {
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Date birth = calendarComponent.getOtherDate(-20);
        petForSale_id = pet.createPetByAPI(false, "autoTestSaleCat", birth, 0, 1);
    }

    public int getSaleId() {
        return sale_id;
    }

    public void setSaleId() {
        Sale sale = new Sale();
        sale_id = sale.salePetByAPI(false, false, true, 10000, petForSale_id);
    }

    public int getRequestId() {
        return request_id;
    }

    public void setRequestId() {
        CalendarComponent calendarComponent = new CalendarComponent();
        Request request = new Request();
        Date today = calendarComponent.getTodayDate();
        request_id = request.createRequestByAPI(0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 0,0, 6,
                false, true, today);
    }
}
