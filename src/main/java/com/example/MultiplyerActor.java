package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.math.BigInteger;

public class  MultiplyerActor extends AbstractBehavior<MultiplyerActor.Multiply> {

    public MultiplyerActor(ActorContext<Multiply> context) {
        super(context);
    }

    @Override
    public Receive<Multiply> createReceive() {
        return newReceiveBuilder().onMessage(Multiply.class, this::multiply).build();
    }

    private Behavior<Multiply> multiply (Multiply multiply) {
        multiply.replyTo.tell(new AccumilatorActor.Accumilator(BigInteger.valueOf(multiply.i).multiply(BigInteger.valueOf(multiply.j) )));
        return Behaviors.same();
    }

    public static class Multiply {
        public final long i;
        public final long j;
        public final ActorRef<AccumilatorActor.Accumilator> replyTo;
        public Multiply(int i, int j, ActorRef<AccumilatorActor.Accumilator> replyTo) {
            this.i = Long.valueOf(i);
            this.j = Long.valueOf(j);
            this.replyTo = replyTo;
        }
    }


}
