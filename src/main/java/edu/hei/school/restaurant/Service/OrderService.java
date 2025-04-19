package edu.hei.school.restaurant.Service;


import edu.hei.school.restaurant.entity.*;
import edu.hei.school.restaurant.mapper.OrderDishMapper;
import edu.hei.school.restaurant.mapper.OrderMapper;
import edu.hei.school.restaurant.dto.*;
import edu.hei.school.restaurant.repository.dao.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderDao orderDao;
    private DishOrderDao dishOrderDao;
    private DishOrderHistoryDao dishOrderHistoryDao;
    private OrderHistoryDao orderHistoryDao;
    private OrderMapper orderMapper;
    private OrderDishMapper orderDishMapper;
    private ChangeStatus changeStatus;
    private DishDao dishDao;

    public OrderService(OrderDao orderDao, DishOrderDao dishOrderDao, DishOrderHistoryDao dishOrderHistoryDao, OrderHistoryDao orderHistoryDao, OrderMapper orderMapper, OrderDishMapper orderDishMapper, DishDao dishDao) {
        this.orderDao = orderDao;
        this.dishOrderDao = dishOrderDao;
        this.dishOrderHistoryDao = dishOrderHistoryDao;
        this.orderHistoryDao = orderHistoryDao;
        this.orderMapper = orderMapper;
        this.dishDao = dishDao;
        this.orderDishMapper = orderDishMapper;
    }

    public OrderRest getByID(String reference){
        Order order = orderDao.getById(reference);
        return orderMapper.toRest(order);
    }

    public OrderRest saveAll(String reference, OrderQuantityDish orderQuantityDish){
        List<QuantityDish> quantityDishes = orderQuantityDish.getQuantityDishes();
        List<DishOrder> dishOrders = dishOrderDao.saveAll(reference, orderDishMapper.toModel(quantityDishes));
        orderHistoryDao.saveStatusHistory(reference, new StatusHistory(LocalDateTime.now(), orderQuantityDish.getOrderStatus()));
        return orderMapper.toRest(orderDao.getById(reference));
    }

    public OrderRest saveHistoryDishOrder(String reference, Long idDish, ChangeStatus changeStatus){
        Long idDishOrder = dishOrderHistoryDao.getDishOrderId(reference, idDish);
        dishOrderHistoryDao.saveStatusHistory(idDishOrder, new StatusHistory(LocalDateTime.now(), changeStatus.getStatus()));
        return orderMapper.toRest(orderDao.getById(reference));
    }

    public OrderRest createOrder(String idOrder){
        Order order = orderDao.CreateOrder(idOrder);
        System.out.println(order);
        return orderMapper.toRest(order);
    }


    public List<DishSales> getDishesSold() {
        return orderDao.getDishesSold();
    }

    public List<Order> getAll(){
        return orderDao.getAll();
    }
}
