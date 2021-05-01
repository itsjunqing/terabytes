package api;

import stream.Message;

import java.util.List;

public class MessageApi extends BasicApi<Message> implements ApiInterface<Message> {

    private final String MESSAGE_ENDPOINT = "/message";

    @Override
    public List<Message> getAll() {
        return getAllObjects(MESSAGE_ENDPOINT, Message[].class);
    }

    @Override
    public Message get(String id) {
        return getObject(MESSAGE_ENDPOINT + "/" + id, Message.class);
    }

    @Override
    public Message add(Message object) {
        return postObject(MESSAGE_ENDPOINT, object, Message.class);
    }

    @Override
    public boolean patch(String id, Message object) {
        return patchObject(MESSAGE_ENDPOINT + "/" + id, object);
    }

    @Override
    public boolean remove(String id) {
        return deleteObject(MESSAGE_ENDPOINT + "/" + id);
    }

    /*
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

    public boolean patchMessage(String id, Message message) {
        String endpoint = MESSAGE_ENDPOINT + "/" + id;
        return patchObject(endpoint, message);
    }

    public boolean removeMessage(String id) {
        return deleteObject(MESSAGE_ENDPOINT + "/" + id);
    }
    */
}
