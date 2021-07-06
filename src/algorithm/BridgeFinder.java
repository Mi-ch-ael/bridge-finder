//package algorithm;
//import Graph;
//import Node;
//import Edge;

// Этот файл содержит void main,
// необходимый для демонстрации работы
// кода и тестирования классов 
// Node, Edge, Graph.

// Программа предлагает пользователю
// ввести данные, удалить ненужные,
// узнать промежуточные данные и
// результат работы алгоритма Тарьяна.

import java.util.Scanner;

public class BridgeFinder{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int nodeCount, edgeCount;
        Graph graph = new Graph();
        String firstNodeName, secondNodeName;
        System.out.printf("Write node count:\t");
        nodeCount = in.nextInt();
        for(int i = 0; i < nodeCount; i++){
            firstNodeName = in.next();
            if(!graph.addNode(firstNodeName))
                System.out.printf("false!\n");
        }
        graph.print();
        System.out.printf("=========\n\n");

        System.out.printf("Write edge count:\t");
        edgeCount = in.nextInt();
        for(int i = 0; i < edgeCount; i++){
            firstNodeName = in.next();
            secondNodeName = in.next();
            if(!graph.addEdge(firstNodeName, secondNodeName))
                System.out.printf("false!\n");
        }
        graph.print();
        System.out.printf("=========\n\n");

        System.out.printf("Write remove node count:\t");
        nodeCount = in.nextInt();
        for(int i = 0; i < nodeCount; i++){
            firstNodeName = in.next();
            if(!graph.removeNode(firstNodeName))
                System.out.printf("false!\n");
        }
        graph.print();
        System.out.printf("=========\n\n");

        System.out.printf("Write remove edge count:\t");
        edgeCount = in.nextInt();
        for(int i = 0; i < edgeCount; i++){
            firstNodeName = in.next();
            secondNodeName = in.next();
            if(!graph.removeEdge(firstNodeName, secondNodeName))
                System.out.printf("false!\n");
        }
        graph.print();
        System.out.printf("=========\n\n");
        System.out.printf("Graph bridges finder:\n");
        graph.runAlgorithm();
        graph.print();

        in.close();
        return;
    }
}
