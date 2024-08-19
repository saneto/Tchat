import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Configuration
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states
            .withStates()
            .initial(OrderStates.ORDERED)
            .state(OrderStates.ORDERED)
            .state(OrderStates.PAID)
            .state(OrderStates.SHIPPED)
            .state(OrderStates.DELIVERED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
            .withExternal().source(OrderStates.ORDERED).target(OrderStates.PAID).event(OrderEvents.PAY)
            .and()
            .withExternal().source(OrderStates.PAID).target(OrderStates.SHIPPED).event(OrderEvents.SHIP)
            .and()
            .withExternal().source(OrderStates.SHIPPED).target(OrderStates.DELIVERED).event(OrderEvents.DELIVER);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        config
            .withConfiguration()
            .autoStartup(true)
            .listener(listener());
    }

    @Bean
    public StateMachineListenerAdapter<OrderStates, OrderEvents> listener() {
        return new StateMachineListenerAdapter<OrderStates, OrderEvents>() {
            @Override
            public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
                System.out.println("State changed from " + (from != null ? from.getId() : "none") + " to " + to.getId());
            }

            @Override
            public void transition(Transition<OrderStates, OrderEvents> transition) {
                System.out.println("Transition from " + transition.getSource().getId() + " to " + transition.getTarget().getId() + " on event " + transition.getTrigger().getEvent());
            }
        };
    }
}