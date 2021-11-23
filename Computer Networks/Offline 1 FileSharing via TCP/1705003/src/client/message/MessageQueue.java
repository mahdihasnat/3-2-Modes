package client.message;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private final Queue<Message> queue;

    private MessageQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    public void addMessage(Message message) {
        queue.add(message);
        System.out.println("You have " + queue.size() + " unread messages");
    }

    public String readMessages() {
        StringBuilder messages = new StringBuilder("Total new Message:" + queue.size() + "\n");
        while (!queue.isEmpty()) {
            messages.append(queue.poll().toString()).append("\n");
        }
        return messages.toString();
    }


    private static MessageQueue instance;

    public static MessageQueue getInstance() {
        if (instance == null) {
            instance = new MessageQueue();
        }
        return instance;
    }

}
