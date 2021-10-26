package soulintec.com.tmsclient.Graphics.Windows.AuthorizationView;

import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.Authorization.AppUserDTO;

public class UsersModel {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public static class UsersTableObject implements Comparable<UsersTableObject> {

        private LongProperty id;
        private StringProperty userName;
        private StringProperty password;

        public UsersTableObject(LongProperty id, StringProperty userName, StringProperty password) {
            this.id = id;
            this.userName = userName;
            this.password = password;
        }

        public UsersTableObject(Long id, String userName, String password) {
            this.id = new SimpleLongProperty(id);
            this.userName = new SimpleStringProperty(userName);
            this.password = new SimpleStringProperty(password);
        }

        public UsersTableObject(AppUserDTO userDTO) {
            this.id = new SimpleLongProperty(userDTO.getUserId());
            this.userName = new SimpleStringProperty(userDTO.getName());
            this.password = new SimpleStringProperty(userDTO.getPassword());
        }

        public static UsersTableObject CreateFromUserDTO(AppUserDTO userDTO) {
            return new UsersTableObject(new SimpleLongProperty(userDTO.getUserId()),
                    new SimpleStringProperty(userDTO.getName()),
                    new SimpleStringProperty(userDTO.getPassword()));
        }

        public long getId() {
            return id.get();
        }

        public LongProperty idProperty() {
            return id;
        }

        public void setId(long id) {
            this.id.set(id);
        }

        public String getUserName() {
            return userName.get();
        }

        public StringProperty userNameProperty() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName.set(userName);
        }

        public String getPassword() {
            return password.get();
        }

        public StringProperty passwordProperty() {
            return password;
        }

        public void setPassword(String password) {
            this.password.set(password);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UsersTableObject that = (UsersTableObject) o;
            return Objects.equal(id.getValue(), that.id.getValue()) && Objects.equal(userName.getValue(), that.userName.getValue()) && Objects.equal(password.getValue(), that.password.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id.getValue(), userName.getValue(), password.getValue());
        }

        @Override
        public String toString() {
            return "UsersTableObject{" +
                    "id=" + id.getValue() +
                    ", userName=" + userName.getValue() +
                    ", password=" + password.getValue() +
                    '}';
        }

        @Override
        public int compareTo(UsersTableObject o) {
            return this.getUserName().compareTo(o.getUserName());
        }
    }
}
