import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

@Service
public class BucketService {

    @Autowired
    private StateMachine<BucketState, BucketEvent> stateMachine;

    public void transitionBucketToState2(String bucketId) {
        stateMachine.start();
        Message<BucketEvent> message = MessageBuilder
                .withPayload(BucketEvent.VALIDATE_PRODUCT)
                .setHeader("bucketId", bucketId)
                .build();
        stateMachine.sendEvent(message);
    }

    public void transitionBucketToState3(String bucketId) {
        stateMachine.start();
        Message<BucketEvent> message = MessageBuilder
                .withPayload(BucketEvent.MOVE_TO_STATE_3)
                .setHeader("bucketId", bucketId)
                .build();
        stateMachine.sendEvent(message);
    }
}