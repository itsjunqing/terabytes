package api;

import stream.Message;

import java.util.List;

public class MessageApi extends BasicApi<Message> {

    private final String MESSAGE_ENDPOINT = "/message";

    public List<Message> getAllMessages() {
        return getAllObjects(MESSAGE_ENDPOINT, Message[].class);
    }

    public Message getMessage(String id) {
        String endpoint = MESSAGE_ENDPOINT + "/" + id;
        return getObject(endpoint, Message.class);
    }

    public boolean addMessage(Message message) {
        return postObject(MESSAGE_ENDPOINT, message);
    }

    public boolean patchMessage(Message message) {
        return patchObject(MESSAGE_ENDPOINT, message);
    }

    public boolean removeMessage(String id) {
        return deleteObject(MESSAGE_ENDPOINT + "/" + id);
    }
}
