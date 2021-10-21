package soulintec.com.tmsclient.UserObeserver;

public interface Subject {
    void add(Observer observer);
    void remove(Observer observer);
    void notifyAllObservers();
}
