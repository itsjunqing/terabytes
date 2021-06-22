package api;

import stream.Message;

import java.util.List;

/**
 * A class that performs API communication on Message.
 */
public class MessageApi extends BasicApi<Message> {

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

}
