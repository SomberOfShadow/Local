package directory.size;

import java.io.File;

public class DirectorySize {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\EENHENI\\202002\\20200212172854");
        long usableSpace = file.getUsableSpace();
        System.out.println(usableSpace);

    }

}
