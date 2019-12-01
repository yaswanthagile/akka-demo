package com.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.math.BigInteger;

public class AccumilatorActor extends AbstractBehavior<AccumilatorActor.Accumilator> {

    private BigInteger finalResult = BigInteger.ONE;
    public AccumilatorActor(ActorContext<Accumilator> context) {
        super(context);
    }

    @Override
    public Receive<Accumilator> createReceive() {
        return newReceiveBuilder().onMessage(Accumilator.class, this::accumilate)
                .build();
    }


    public static Behavior<Accumilator> create() {
        return Behaviors.setup(AccumilatorActor::new);
    }

    private Behavior<Accumilator> accumilate (Accumilator accumilator) {
        finalResult =  finalResult.multiply(accumilator.result);
        System.out.println(finalResult);

        return Behaviors.same();
    }

    interface AccumilatorCommand{};
    public static class Accumilator implements  AccumilatorCommand {
        public final BigInteger result;
        public Accumilator (BigInteger result) {
            this.result = result;
        }
    }

    public static class Result implements AccumilatorCommand {
    }
}
