package com.example.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.OrderRequest;
import com.example.bookstore.entity.Order;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {


	@Autowired 
	private  OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepo;


	
	@PostMapping("/place")
	public Order placeOrder(@RequestBody OrderRequest orderRequest) {
	    return orderService.placeOrder(orderRequest.getUserId(), orderRequest.getBookIds());
	}

	@GetMapping("/id/{id}")
	public Optional<Order> orderById(@PathVariable long id) {
		return orderRepo.findById(id);
	}
	
	@GetMapping
	public List<Order> getAllOders(){
		return orderRepo.findAll(); 
	}

}
