import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PermutationWrapper {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(args[1]);
        System.setIn(fileInputStream);
        Permutation.main(args);
    }
}