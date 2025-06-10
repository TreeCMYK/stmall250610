package untitled.domain;

import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class DeliveryComplated extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String customerId;
    private Integer qty;
    private String address;
    private String productId;
}
