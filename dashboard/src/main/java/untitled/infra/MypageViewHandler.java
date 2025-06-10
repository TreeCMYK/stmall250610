package untitled.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import untitled.config.kafka.KafkaProcessor;
import untitled.domain.*;

@Service
public class MypageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1(
        @Payload OrderPlaced orderPlaced
    ) {
        try {
            if (!orderPlaced.validate()) return;

            // view 객체 생성
            Mypage mypage = new Mypage();
            // view 객체에 이벤트의 Value 를 set 함
            mypage.setOrderId(orderPlaced.getId());
            mypage.setCustomerId(orderPlaced.getCustomerId());
            mypage.setItemId(orderPlaced.getItemId());
            mypage.setQty(orderPlaced.getQty());
            mypage.setOrderStatus(ORDERPLACED);
            // view 레파지 토리에 save
            mypageRepository.save(mypage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryComplated_then_UPDATE_1(
        @Payload DeliveryComplated deliveryComplated
    ) {
        try {
            if (!deliveryComplated.validate()) return;
            // view 객체 조회
            Optional<Mypage> mypageOptional = mypageRepository.findByOrderId(
                deliveryComplated.getOrderId()
            );

            if (mypageOptional.isPresent()) {
                Mypage mypage = mypageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCanceled_then_UPDATE_2(
        @Payload OrderCanceled orderCanceled
    ) {
        try {
            if (!orderCanceled.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findByOrderStatus(
                String.valueOf(orderCanceled.getId())
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryReturned_then_UPDATE_3(
        @Payload DeliveryReturned deliveryReturned
    ) {
        try {
            if (!deliveryReturned.validate()) return;
            // view 객체 조회
            Optional<Mypage> mypageOptional = mypageRepository.findByOrderId(
                deliveryReturned.getOrderId()
            );

            if (mypageOptional.isPresent()) {
                Mypage mypage = mypageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
