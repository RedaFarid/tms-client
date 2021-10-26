package soulintec.com.tmsclient.Graphics.Windows.AuthorizationView.AuthoraizationWindow;

import com.google.common.base.Objects;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;

@Component
public class AuthorizationModel {

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty addAuthority = new SimpleStringProperty();
    private final LongProperty userId = new SimpleLongProperty();


    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddAuthority() {
        return addAuthority.get();
    }

    public StringProperty addAuthorityProperty() {
        return addAuthority;
    }

    public void setAddAuthority(String addAuthority) {
        this.addAuthority.set(addAuthority);
    }

    public long getUserId() {
        return userId.get();
    }

    public LongProperty userIdProperty() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId.set(userId);
    }

    public static class TableObject {

        private final LongProperty idColumn;
        private final StringProperty nameColumn;
        private final LongProperty userIdColumn;

        public TableObject(LongProperty idColumn, StringProperty nameColumn, LongProperty userIdColumn) {
            this.idColumn = idColumn;
            this.nameColumn = nameColumn;
            this.userIdColumn = userIdColumn;
        }


        public TableObject(Long idColumn, String nameColumn, Long userIdColumn) {
            this.idColumn = new SimpleLongProperty(idColumn);
            this.nameColumn = new SimpleStringProperty(nameColumn);
            this.userIdColumn = new SimpleLongProperty(userIdColumn);
        }

        public static TableObject createFromAuthorization(long userIdColumn, RoleDTO authorityDTO) {
            return new TableObject(new SimpleLongProperty(authorityDTO.getId()), new SimpleStringProperty(authorityDTO.getName()), new SimpleLongProperty(userIdColumn));
        }

        public long getIdColumn() {
            return idColumn.get();
        }

        public LongProperty idColumnProperty() {
            return idColumn;
        }

        public void setIdColumn(long idColumn) {
            this.idColumn.set(idColumn);
        }

        public String getNameColumn() {
            return nameColumn.get();
        }

        public StringProperty nameColumnProperty() {
            return nameColumn;
        }

        public void setNameColumn(String nameColumn) {
            this.nameColumn.set(nameColumn);
        }

        public long getUserIdColumn() {
            return userIdColumn.get();
        }

        public LongProperty userIdColumnProperty() {
            return userIdColumn;
        }

        public void setUserIdColumn(long userIdColumn) {
            this.userIdColumn.set(userIdColumn);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(idColumn.getValue(), that.idColumn.getValue())
                    && Objects.equal(nameColumn.getValue(), that.nameColumn.getValue())
                    && Objects.equal(userIdColumn.getValue(), that.userIdColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(idColumn.getValue(), nameColumn.getValue(), userIdColumn.getValue());
        }
    }
        public static class SecondTableObject {
            private final StringProperty addAuthorityColumn;

            public SecondTableObject(StringProperty addAuthorityColumn) {
                this.addAuthorityColumn = addAuthorityColumn;
            }
            public SecondTableObject(String addAuthorityColumn) {
                this.addAuthorityColumn = new SimpleStringProperty(addAuthorityColumn);
            }

            public String getAddAuthorityColumn() {
                return addAuthorityColumn.get();
            }

            public StringProperty addAuthorityColumnProperty() {
                return addAuthorityColumn;
            }

            public void setAddAuthorityColumn(String addAuthorityColumn) {
                this.addAuthorityColumn.set(addAuthorityColumn);
            }

            public static SecondTableObject createFromAddAuthorization(String addAuthorityColumn) {
                return new SecondTableObject(new SimpleStringProperty(addAuthorityColumn));
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                SecondTableObject that = (SecondTableObject) o;
                return Objects.equal(addAuthorityColumn.getValue(), that.addAuthorityColumn.getValue());
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(addAuthorityColumn.getValue());
            }
        }

    @Override
    public String toString() {
        return "AuthorizationModel{" +
                "id=" + id.getValue() +
                ", name=" + name.getValue() +
                ", addAuthority=" + addAuthority.getValue() +
                ", userId=" + userId.getValue() +
                '}';
    }
}

