package com.example.bookstore.service;


import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
	
	@Autowired
	EmailService emailService;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookRepository bookRepo;

    public Order placeOrder(Long userId, List<Long> bookIds) {
        Optional<User> userOpt = userRepo.findById(userId);
        
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found.");
        }

        List<Book> books = bookRepo.findAllById(bookIds);
        if (books.isEmpty()) {
            throw new RuntimeException("Books not found.");
        }

        double totalAmount = 0.0;

     
     for (Book book : books) {
         totalAmount += book.getPrice();
     }
        Order order = new Order();
        order.setUser(userOpt.get());
        order.setBooks(books);
        order.setOrderDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());

        order.setTotalAmount(totalAmount);

        String email = userOpt.get().getEmail();
        emailService.sendSimpleEmail(email, "Regaurding Book Order.." , "Book has been Ordered");
        
        return orderRepo.save(order);
    }


    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
