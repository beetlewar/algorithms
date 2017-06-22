import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        int numRandomValues = Integer.parseInt(args[0]);

        for (int i = 0; i < numRandomValues; i++) {
            String inputString = StdIn.readString();
            queue.enqueue(inputString);
        }

        for (int i = 0; i < numRandomValues; i++) {
            String randomString = queue.dequeue();
            StdOut.println(randomString);
        }
    }
}
