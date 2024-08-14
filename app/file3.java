import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.builders.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<BucketState, BucketEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<BucketState, BucketEvent> states) throws Exception {
        states
            .withStates()
                .initial(BucketState.STATE_1)
                .state(BucketState.STATE_2)
                .state(BucketState.STATE_3); // Adding STATE_3
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BucketState, BucketEvent> transitions) throws Exception {
        transitions
            // Transition from STATE_1 to STATE_2
            .withExternal()
                .source(BucketState.STATE_1)
                .target(BucketState.STATE_2)
                .event(BucketEvent.VALIDATE_PRODUCT)
                .action(validateProductAction)

            // Transition from STATE_2 to STATE_3
            .and()
            .withExternal()
                .source(BucketState.STATE_2)
                .target(BucketState.STATE_3)
                .event(BucketEvent.MOVE_TO_STATE_3); // Restrict transition from STATE_2 to STATE_3 only
    }
}