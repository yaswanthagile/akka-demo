package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import akka.cluster.typed.Cluster;
import akka.cluster.typed.ClusterStateSubscription;


public class StartActor extends AbstractBehavior<StartActor.Start> {
    interface Start {};

    private final ActorRef<MultiplyerActor.Multiply> router;

    public StartActor(ActorContext<Start> context) {
        super(context);
        PoolRouter<MultiplyerActor.Multiply> poolRouter = Routers.pool(50, Behaviors.setup(MultiplyerActor::new)).withRoundRobinRouting();
        router = getContext().spawn(poolRouter, "multiplier-pool");
    }

    public static Behavior<Start> create() {
        return Behaviors.setup(StartActor::new);
    }
    @Override
    public Receive<Start> createReceive() {
        return newReceiveBuilder().onMessage(InitCommand.class, this::onStart).build();
    }

    public Behavior<Start> onStart (InitCommand initCommand) {

        ActorRef<AccumilatorActor.Accumilator> accumilatorActorRef = getContext().spawnAnonymous(AccumilatorActor.create());
        for(int i = initCommand.factorialOf ; i >= 2; i = i-2) {
            router.tell(new MultiplyerActor.Multiply(i, i-1, accumilatorActorRef));
        }

        return Behaviors.same();
    }

    public static class InitCommand implements Start {
        public final int factorialOf;
        public InitCommand(int factorialOf) {
            this.factorialOf = factorialOf;
        }
    };
}
