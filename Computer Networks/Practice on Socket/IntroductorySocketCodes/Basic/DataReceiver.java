package Basic;

import java.io.DataInputStream;
import java.io.IOException;

public class DataReceiver extends Thread{
    DataInputStream dataInputStream;

    public DataReceiver(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        while (true)
        {
            byte[] b = new byte[1000];

            try {
                dataInputStream.read(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(b.length);
            for(int i=0;i<1000;i++)
                System.out.printf("%d ",b[i]);
            System.out.println("");

        }
    }
}
