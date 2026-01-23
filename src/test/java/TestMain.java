import java.util.Optional;

/**
 * project test-demo
 * path PACKAGE_NAME.TestMain
 * author Rorschach
 * dateTime 2026/1/13 23:03
 */
public class TestMain {
    public static void main(String[] args) {
        System.out.println(Optional.of("123").orElse("456"));
    }
}
