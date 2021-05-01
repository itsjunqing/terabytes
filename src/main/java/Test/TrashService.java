package Test;

import api.*;
import lombok.Getter;

@Getter
@Deprecated
public class TrashService {

    private UserApi userApi;
    private SubjectApi subjectApi;
    private ContractApi contractApi;
    private BidApi bidApi;
    private MessageApi messageApi;

    public TrashService() {
        this.userApi = new UserApi();
        this.subjectApi = new SubjectApi();
        this.contractApi = new ContractApi();
        this.bidApi = new BidApi();
        this.messageApi = new MessageApi();
    }

}

/*
    public <T> List<T> executeGetAll(Class<T[]> clazz) {
        if (clazz.isInstance(User.class)) {
            return Arrays.asList(clazz.cast(userApi.getAll()));

        } else if (clazz.isInstance(Subject.class)) {
            return Arrays.asList(clazz.cast(subjectApi.getAll()));

        } else if (clazz.isInstance(Contract.class)) {
            return Arrays.asList(clazz.cast(contractApi.getAll()));

        } else if (clazz.isInstance(Bid.class)) {
            return Arrays.asList(clazz.cast(bidApi.getAll()));

        } else if (clazz.isInstance(Message.class)) {
            return Arrays.asList(clazz.cast(messageApi.getAll()));
        }
        return null;
    }


    public <T> T executeGet(Class<T> clazz, String id) {
        if (clazz.isInstance(User.class)) {
            return clazz.cast(userApi.get(id));

        } else if (clazz.isInstance(Subject.class)) {
            return clazz.cast(subjectApi.get(id));

        } else if (clazz.isInstance(Contract.class)) {
            return clazz.cast(contractApi.get(id));

        } else if (clazz.isInstance(Bid.class)) {
            return clazz.cast(bidApi.get(id));

        } else if (clazz.isInstance(Message.class)) {
            return clazz.cast(messageApi.get(id));
        }
        return null;
    }

    public <T> T executeGet(ServiceType type, String id, Class<T> clazz) {
        switch (type) {
            case USER:
                return clazz.cast(userApi.get(id));
            case SUBJECT:
                return subjectApi.get(id);
            case CONTRACT:
                return contractApi.get(id);
            case BID:
                return bidApi.get(id);
            case MESSAGE:
                return messageApi.get(id);
        }
        return null;
    }


    public <T> T executeAdd(Class<T> clazz, T object) {
        if (clazz.isInstance(User.class) && object instanceof User) {
            return clazz.cast(userApi.add(((User) object)));

        } else if (clazz.isInstance(Subject.class) && object) {
            return clazz.cast(subjectApi.get(id));

        } else if (clazz.isInstance(Contract.class)) {
            return clazz.cast(contractApi.get(id));

        } else if (clazz.isInstance(Bid.class)) {
            return clazz.cast(bidApi.get(id));

        } else if (clazz.isInstance(Message.class)) {
            return clazz.cast(messageApi.get(id));
        }
        return null;
    }

    public Stream executeAdd(ServiceType type, Stream object) {
        switch (type) {
            case USER:
                return userApi.add((User) object);
            case SUBJECT:
                return subjectApi.add((Subject) object);
            case CONTRACT:
                return contractApi.add((Contract) object);
            case BID:
                return bidApi.add((Bid) object);
            case MESSAGE:
                return messageApi.add((Message) object);
        }
        return null;
    }

    public boolean executePatch(ServiceType type, String id, Stream object) {
        switch (type) {
            case USER:
                return userApi.patch(id, (User) object);
            case SUBJECT:
                return subjectApi.patch(id, (Subject) object);
            case CONTRACT:
                return contractApi.patch(id, (Contract) object);
            case BID:
                return bidApi.patch(id, (Bid) object);
            case MESSAGE:
                return messageApi.patch(id, (Message) object);
        }
        return false;
    }

    public boolean executeRemove(ServiceType type, String id) {
        switch (type) {
            case USER:
                return userApi.remove(id);
            case SUBJECT:
                return subjectApi.remove(id);
            case CONTRACT:
                return contractApi.remove(id);
            case BID:
                return bidApi.remove(id);
            case MESSAGE:
                return messageApi.remove(id);
        }
        return false;
    }

    public boolean executeVerifyUser(User user) {
        return userApi.verify(user);
    }

    public boolean executeCloseBid(String id, Bid bid) {
        return bidApi.close(id, bid);
    }

    public boolean executeSignContract(String id, Contract contract) {
        return contractApi.sign(id, contract);
    }
*/