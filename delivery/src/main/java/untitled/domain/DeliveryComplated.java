package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class DeliveryComplated extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String customerId;
    private Integer qty;
    private String address;
    private String productId;

    public DeliveryComplated(Delivery aggregate) {
        super(aggregate);
    }

    public DeliveryComplated() {
        super();
    }
}
//>>> DDD / Domain Event
