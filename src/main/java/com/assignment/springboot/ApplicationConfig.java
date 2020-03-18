package com.assignment.springboot;


import com.assignment.springboot.Repository.*;
import com.assignment.springboot.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.assignment.springboot"})
public class ApplicationConfig implements WebMvcConfigurer {
    @Autowired
    public ApplicationContext applicationContext;

    @Bean
    public ProductRepository getProductRepository() {
        return new ProductRepositoryImpl();
    }

    @Bean
    public ProductService getProductService() {
        return new ProductServiceImpl();
    }

    @Bean
    public ReceiptRepository getReceiptRepository() {
        return new ReceiptRepositoryImpl();
    }

    @Bean
    public ReceiptService getReceiptService() {
        return new ReceiptServiceImpl();
    }

    @Bean
    public ReceiptItemRepository getReceiptItemRepository() {
        return new ReceiptItemRepositoryImpl();
    }

    @Bean
    public ReceiptItemService getReceiptItemService() {
        return new ReceiptItemServiceImpl();
    }


    @Bean
    public MemberRepository getMemberRepository() {
        return new MemberRepositoryImpl();
    }

    @Bean
    public MemberService getMemberService() {
        return new MemberServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
