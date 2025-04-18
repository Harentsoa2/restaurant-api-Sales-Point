package edu.hei.school.restaurant.controller;

import edu.hei.school.restaurant.Service.OrderService;
import edu.hei.school.restaurant.dto.ChangeStatus;
import edu.hei.school.restaurant.dto.OrderQuantityDish;
import edu.hei.school.restaurant.dto.OrderRest;
import edu.hei.school.restaurant.entity.DishOrder;
import edu.hei.school.restaurant.entity.Order;
import edu.hei.school.restaurant.entity.StatusHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderRestController {
    private OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{reference}")
    public ResponseEntity<Object> getOrder(@PathVariable Long reference) {
        OrderRest orderRest = orderService.getByID(reference);
        if(orderRest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderRest, HttpStatus.OK);
    }

    @PostMapping("/order/{reference}")
    public ResponseEntity<Object> createOrder(@PathVariable Long reference) {
            OrderRest orderRest = orderService.createOrder(reference);
            return new ResponseEntity<>(orderRest, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{reference}/dishes")
    public ResponseEntity<Object> updateOrder(@PathVariable Long reference, @RequestBody OrderQuantityDish orderQuantityDish) {
        OrderRest order = orderService.saveAll(reference, orderQuantityDish);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/orders/{reference}/dishes/{dishId}")
    public ResponseEntity<Object> saveHistoryDishOrder(@PathVariable Long reference, @PathVariable Long dishId, @RequestBody ChangeStatus changeStatus) {
        OrderRest statusHistory = orderService.saveHistoryDishOrder(reference, dishId, changeStatus);
        return new ResponseEntity<>(statusHistory, HttpStatus.OK);
    }

    @GetMapping("/sales")
    public ResponseEntity<Object> getDishesSold() {
        return new ResponseEntity<>(orderService.getDishesSold(), HttpStatus.OK);
    }


}
