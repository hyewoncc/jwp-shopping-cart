package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrdersResponse;

@Validated
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@Valid @RequestBody final List<OrderRequest> orderDetails,
                                         @AuthenticationPrincipal Customer customer) {
        final Long orderId = orderService.save(orderDetails, customer.getUsername());
        return ResponseEntity.created(
                URI.create("/api/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@PathVariable final Long orderId,
                                                   @AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(orderService.findOrderById(customer.getUsername(), orderId));
    }

    @GetMapping
    public ResponseEntity<OrdersResponse> findOrders(@AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(orderService.findOrdersByUsername(customer.getUsername()));
    }
}