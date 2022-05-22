import java.nio.*;
import java.nio.channels.*;
import java.io.*;
import java.util.*;
import java.net.InetSocketAddress;

public class NIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress address = new InetSocketAddress("localhost", 1111);
        SocketChannel client = SocketChannel.open(address);

        System.out.println("Connecting");

        for (int i = 0; i < 10; i++) {
            byte[] message = String.valueOf(i).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);

            System.out.printf("Sending: %d%n", i);
            buffer.clear();

            Thread.sleep(2000);
        }

        client.write(ByteBuffer.wrap("quit".getBytes()));
        client.close();
    }
}
