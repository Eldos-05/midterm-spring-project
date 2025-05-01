https://github.com/Eldos-05/midterm-spring-project/blob/main/README.md# midterm-spring-project
# Sneaker E-commerce Platform

## Introduction

This document outlines the requirements for an e-commerce platform specifically designed for the sale of sneakers. My goal is to create a robust and user-friendly system that facilitates seamless online transactions for both customers and administrators.

## Scope

The scope of this project encompasses the development of a web-based application that allows users to browse a comprehensive catalog of sneakers, manage their shopping carts, process orders, and maintain their user profiles. Additionally, the system will provide administrative tools for managing the product catalog and order fulfillment.

## Definitions, Acronyms, and Abbreviations

* **DTO:** Data Transfer Object
* **API:** Application Programming Interface
* **SRS:** Software Requirements Specification

## Product Functions

The system will offer the following core functionalities:

* **Sneaker Catalog Browsing:** Users will be able to view a list of sneakers.
* **Search and Filtering:** Users will be able to find sneakers by size, brand, or color.
* **Shopping Cart:** Users will be able to add sneakers to a shopping cart.
* **Order Placement:** Users will be able to place orders for the sneakers in their cart.
* **User Profile:** Users will be able to create and manage their basic user profile information.
* **Administrative Management:** Administrators will be able to manage the sneaker catalog and orders.

## Specific Requirements

### 3.1 Functional Requirements

#### 3.1.1 Product Catalog Management

* Administrators must be able to add, edit, and delete sneaker entries, including detailed product information such as images, descriptions, and pricing.
* The system should display a visually appealing and organized catalog of sneakers, allowing users to easily browse and explore available products.
* Users should be able to apply filters to the catalog based on size, brand, and color, enabling them to quickly find specific items.

#### 3.1.2 Shopping Cart and Order Processing

* Users must be able to add sneakers to their shopping carts.
* The system should provide a clear and concise view of the shopping cart contents, including item details and total cost.
* Users should be able to proceed to checkout and submit their orders.
* The system should generate and send order confirmation emails to users upon successful order submission.

#### 3.1.3 User Management

* Users must be able to register new accounts and log in securely.
* Users should be able to manage their personal profiles, including address and payment information.
* Administrators should have the ability to manage user accounts, including creating, editing, and deleting accounts.

#### 3.1.4 Administrative Dashboard

* Administrators should have access to a comprehensive dashboard for managing orders, including viewing order details, updating order status, and processing refunds.
* Administrators should be able to generate and view sales reports and analytics.

### 3.2 Non-Functional Requirements

#### 3.2.1 Performance

* The system must respond to user requests within 2 seconds, ensuring a smooth and responsive user experience.

#### 3.2.2 Security

* User passwords must be stored securely using industry-standard encryption techniques.
* The system must be protected against common web vulnerabilities, such as SQL injection and cross-site scripting (XSS) attacks.

#### 3.2.3 Usability

* The user interface must be intuitive and easy to navigate, minimizing the learning curve for users.
* The system should be designed to be responsive and accessible on a variety of devices, including desktops, tablets, and smartphones.

#### 3.2.4 Availability

* The system must be available 24/7, ensuring uninterrupted service for users.

#### 3.2.5 Maintainability

* The code must be well documented and follow common design patterns to make future updates and modifications easier.

#### 3.2.6 Scalability

* The system must be designed to handle a growing number of users and transactions.

## Interfaces

### 4.1 User Interfaces

* A user-friendly web interface for customers to browse, shop, and manage their accounts.
* A dedicated administrative panel for administrators to manage the platform.

### 4.2 API Interfaces

* A RESTful API will be developed to facilitate integration with potential mobile applications or third-party services.

### 4.3 Hardware Interfaces

* Not applicable.

## Security Setup

This section outlines the security measures implemented in the Sneaker E-commerce Platform to protect user data and prevent unauthorized access. The application utilizes Spring Security for managing authentication and authorization.

* **Password Encryption:** User passwords are encrypted using BCryptPasswordEncoder, a strong hashing algorithm, before being stored in the database. This ensures that even if the database is compromised, the actual passwords remain protected. This is configured within the `SecurityConfig` class.
* **Protection Against SQL Injection:** Spring Data JPA, which is likely used for database interactions, helps prevent SQL injection by using parameterized queries under the hood. Additionally, careful coding practices and input validation are employed to mitigate this risk.
* **Protection Against Cross-Site Scripting (XSS):** Input sanitization and output encoding techniques are implemented to prevent XSS attacks. Spring Security's built-in mechanisms for content security policy (CSP) and template engine integration (depending on the chosen engine) can further help in mitigating XSS vulnerabilities.
* **Secure Authentication and Authorization:** Spring Security handles authentication through a `DaoAuthenticationProvider`, which retrieves user details from the `UserService`. Authorization is managed through role-based access control, as indicated by the `@EnableGlobalMethodSecurity(securedEnabled = true)` annotation and the use of `.hasRole("ADMIN")` in the `SecurityConfig`. JWT (JSON Web Tokens) are used for stateless session management, as configured with the `JwtRequestFilter`. OAuth 2.0 login is also enabled for social login, handled by the `OAuth2LoginSuccessHandler`.
* **Session Management:** The application employs a stateless session policy (`SessionCreationPolicy.STATELESS`) using JWT. This means that the server does not store any session information, and each request is authenticated based on the JWT provided in the request headers.
* **Unauthorized Access Handling:** An `HttpStatusEntryPoint` is configured to return an HTTP 401 (Unauthorized) status code for unauthenticated requests to protected resources.
* **CORS Protection:** While explicitly disabled in the provided `SecurityConfig`, in a production environment, Cross-Origin Resource Sharing (CORS) should be carefully configured to allow only trusted origins to access the application's resources.
* **CSRF Protection:** CSRF (Cross-Site Request Forgery) protection is disabled (`csrf().disable()`). In a production environment, especially for state-changing requests, CSRF protection should be enabled and properly handled, particularly for traditional web form submissions.

**Relevant Code Snippets (from `SecurityConfig.java`):**

```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    // ... (other configurations)

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF is disabled, consider enabling in production
                .cors().disable() // CORS is disabled, configure in production
                .authorizeRequests()
                .requestMatchers("/**").permitAll() // Adjust permissions as needed
                .requestMatchers("/login", "/oauth2/**").permitAll()
                .requestMatchers("/secured").authenticated()
                .requestMatchers("/info").authenticated()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/", "/profile").permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session using JWT
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Handle unauthorized requests
                .and()
                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler) // Handle successful OAuth2 login
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // JWT filter

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder()); // BCrypt for password hashing
        provider.setUserDetailsService(userService); // User details service
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ... (AuthenticationManager bean)
}
