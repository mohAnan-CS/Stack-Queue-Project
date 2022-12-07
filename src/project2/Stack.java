package project2;

public class Stack<T> {

    public static void main(String[] args) {

        Stack stack = new Stack();
        Stack tempStack = new Stack();

        stack.push(1);
        stack.push(2);


    }

    public Node top;
    public int count = 0;

    public Stack() {

    }

    public T push(T data) {

        Node node = new Node(data);

        if (top == null) {
            top = node;
            count++;
            return (T) node.getData();
        } else {
            node.setNext(top);
            top = node;
            count++;
            return (T) node.getData();
        }

    }

    public T pop() {

        Node node = top;

        if (top == null) {
            throw new IllegalArgumentException("The stack is empty");
        } else {
            top.setNext(top.getNext());
            top = top.getNext();
            count--;
            return (T) node.getData();
        }

    }

    public boolean isEmpty() {

        if (count == 0) {
            return true;
        } else {
            return false;
        }

    }

    public void show() {

        Node node = top;

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

    public T top() {
        return (T) top.getData();
    }

    public int length() {
        return count;
    }

}
