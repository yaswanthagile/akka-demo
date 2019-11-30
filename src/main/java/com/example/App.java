package com.example;

import akka.actor.typed.ActorSystem;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ActorSystem<StartActor.Start> system = ActorSystem.apply(StartActor.create(), "multiply-system");


        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext()) {
            int i = scanner.nextInt();
            system.tell(new StartActor.InitCommand(i));
        }
    }
}
