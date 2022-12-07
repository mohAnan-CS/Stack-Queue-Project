package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Queue<T> {

    public static void main(String[] args) {
        try {
            String company = new String();
            Scanner sc = new Scanner(System.in);
            System.out.println("enter company name : ");
            company = sc.nextLine();
            boolean checker = false;
            double dailyPrice = 0;
            File file = new File("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Files\\dailyPrice.txt");
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {

                String line = scan.nextLine();
                int q1 = line.indexOf(',');
                String companyNameD = line.substring(0, q1).trim();
                int q2 = line.lastIndexOf(',');
                double dailyPriceD = Double.parseDouble(line.substring(q2 + 1, line.length()).trim());

                if (company.equalsIgnoreCase(companyNameD)) {

                    dailyPrice = dailyPriceD;
                    checker = true;
                    break;

                }
            }

            System.out.println("Check " + checker);


        } catch (FileNotFoundException e) {

            System.out.println(e.getMessage());

        }

    }

    //EnQeue -> addLast
    //DeQeue -> removeFirst
    public Node first, last;
    int count = 0;

    public Queue() {

    }

    public T EnQeue(T data) {

        Node node = new Node(data);

        if (first == null) {
            first = node;
            last = first;
            count++;
            return (T) node.getData();
        } else {

            Node newNode = first;

            while (newNode.getNext() != null) {
                newNode = newNode.getNext();
            }

            newNode.setNext(node);
            last = node;
            count++;
            return (T) node.getData();

        }

    }

    public T Deqeue() {

        Node node = first;

        if (first == null) {
            throw new IllegalArgumentException("The stack is empty");
        } else {

            first.setNext(first.getNext());
            first = first.getNext();
            count--;
            return (T) node.getData();

        }

    }

    public void show() {

        Node node = first;

        if (node == null) {
            System.out.println("[]");
        } else {

            System.out.print("[ ");
            while (node.getNext() != null) {

                System.out.print((T) node.getData() + " , ");
                node = node.getNext();

            }
            System.out.print((T) node.getData() + " ]");
            System.out.println();

        }

    }

    public boolean isEmpty() {

        if (count <= 0) {
            return true;
        } else
            return false;
    }

    public int length() {
        return count;
    }

}
