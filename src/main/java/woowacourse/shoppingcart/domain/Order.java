package woowacourse.shoppingcart.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long id;
    private List<OrderDetail> orderDetails;

    public Order(final List<OrderDetail> orderDetails) {
        this(null, orderDetails);
    }

    public Order(final Long id, final List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = new ArrayList<>(orderDetails);
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
