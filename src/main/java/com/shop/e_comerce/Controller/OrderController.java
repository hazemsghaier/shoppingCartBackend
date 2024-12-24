package com.shop.e_comerce.Controller;

import com.shop.e_comerce.DTO.OrderDto;
import com.shop.e_comerce.exeptions.ResourceNotFoundExeption;
import com.shop.e_comerce.model.Order;
import com.shop.e_comerce.response.APIResponse;
import com.shop.e_comerce.service.order.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<APIResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order =  orderService.placeOrder(userId);
            return ResponseEntity.ok(new APIResponse("Item Order Success!", order));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error Occured!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<APIResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new APIResponse("Item Order Success!", order));
        } catch (ResourceNotFoundExeption e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Oops!", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new APIResponse("Item Order Success!", order));
        } catch (ResourceNotFoundExeption e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Oops!", e.getMessage()));
        }
    }
}