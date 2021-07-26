package exception;

public class ExceptionTest {
    public static void main(String[] args) {
        try  {
            System.out.println("this is a test for catch exception.");
            testExecutionException();
            throw new MyRuntimeException("My exception.");
        } catch (RuntimeException e) {
            System.out.println("Exception is " + e);
        } catch (MyExecutionException e) {
            System.out.println("Exception is " + e);
        }
    }

    public static void testExecutionException () throws MyExecutionException {
        throw new MyExecutionException("My execution exception.");
    }
}
