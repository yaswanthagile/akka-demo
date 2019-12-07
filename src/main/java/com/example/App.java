package com.example;

import akka.actor.typed.ActorSystem;
import akka.cluster.typed.Cluster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String[] ports = new String[]{"2552", "2551"};
        for(String port : ports) {
            Config config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + port).withFallback(ConfigFactory.load(App.class.getClassLoader()));

            ActorSystem<StartActor.Start> system = ActorSystem.apply(StartActor.create(), "multiply-system", config);
            Cluster cluster = Cluster.get(system);
            for (int i = 3; i<20; i++) {
                system.tell(new StartActor.InitCommand(10) {
                });
            }
        }
    }
}
