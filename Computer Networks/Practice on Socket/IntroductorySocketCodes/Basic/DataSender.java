package Basic;

import java.io.DataOutputStream;
import java.io.IOException;

public class DataSender extends Thread{
    DataOutputStream dataOutputStream;

    public DataSender(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        while (true)
        {
            byte [] b = new byte[1000];
            for(int i=0;i<1000;i++)
                b[i] = (byte) (i+1);
            try {
                dataOutputStream.write(b);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
