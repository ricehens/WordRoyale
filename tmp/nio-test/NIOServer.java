import java.nio.*;
import java.nio.channels.*;
import java.io.*;
import java.util.*;
import java.net.InetSocketAddress;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel socket = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("localhost", 1111);
        socket.bind(address);
        socket.configureBlocking(false);
        SelectionKey key = socket.register(selector, socket.validOps(), null);

        while (true) {
            System.out.println("Hi");
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (var k : keys) {
                if (k.isAcceptable()) {
                    SocketChannel client = socket.accept();
                    if (client == null) continue;
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.printf("Accepted connection: %s%n",
                            client.getLocalAddress());
                } else if (k.isReadable()) {
                    SocketChannel client = (SocketChannel) k.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    client.read(buffer);
                    String str = new String(buffer.array()).trim();

                    System.out.printf("Received message: %s%n", str);

                    if (str.equals("quit"))
                        client.close();
                }
            }
        }
    }
}
