package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class  MultiplyerActor extends AbstractBehavior<MultiplyerActor.Multiply> {

    public MultiplyerActor(ActorContext<Multiply> context) {
        super(context);
    }

    @Override
    public Receive<Multiply> createReceive() {
        return newReceiveBuilder().onMessage(Multiply.class, this::multiply).build();
    }

    private Behavior<Multiply> multiply (Multiply multiply) {
        multiply.replyTo.tell(new AccumilatorActor.Accumilator(multiply.i * multiply.j));
        return Behaviors.same();
    }

    public static class Multiply {
        public final Integer i;
        public final Integer j;
        public final ActorRef<AccumilatorActor.Accumilator> replyTo;
        public Multiply(Integer i, Integer j, ActorRef<AccumilatorActor.Accumilator> replyTo) {
            this.i = i;
            this.j = j;
            this.replyTo = replyTo;
        }
    }


}
