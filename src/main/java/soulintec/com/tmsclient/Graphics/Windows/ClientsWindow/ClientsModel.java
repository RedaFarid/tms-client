package soulintec.com.tmsclient.Graphics.Windows.ClientsWindow;

import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.ClientDTO;

import java.time.LocalDateTime;

public class ClientsModel {
    private final LongProperty clientId = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty mainOfficeAddress = new SimpleStringProperty("");
    private final StringProperty contactName = new SimpleStringProperty("");
    private final StringProperty contactTelNumber = new SimpleStringProperty("");
    private final StringProperty contactEmail = new SimpleStringProperty("");
    private final StringProperty modifyDate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();
    private final StringProperty createdBy = new SimpleStringProperty();
    private final StringProperty lastModifiedBy = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();

    public long getClientId() {
        return clientId.get();
    }

    public LongProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId.set(clientId);
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

    public String getMainOfficeAddress() {
        return mainOfficeAddress.get();
    }

    public StringProperty mainOfficeAddressProperty() {
        return mainOfficeAddress;
    }

    public void setMainOfficeAddress(String mainOfficeAddress) {
        this.mainOfficeAddress.set(mainOfficeAddress);
    }

    public String getContactName() {
        return contactName.get();
    }

    public StringProperty contactNameProperty() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    public String getContactTelNumber() {
        return contactTelNumber.get();
    }

    public StringProperty contactTelNumberProperty() {
        return contactTelNumber;
    }

    public void setContactTelNumber(String contactTelNumber) {
        this.contactTelNumber.set(contactTelNumber);
    }

    public String getContactEmail() {
        return contactEmail.get();
    }

    public StringProperty contactEmailProperty() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail.set(contactEmail);
    }

    public String getModifyDate() {
        return modifyDate.get();
    }

    public StringProperty modifyDateProperty() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate.set(modifyDate);
    }

    public String getCreationDate() {
        return creationDate.get();
    }

    public StringProperty creationDateProperty() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate.set(creationDate);
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public StringProperty createdByProperty() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public String getLastModifiedBy() {
        return lastModifiedBy.get();
    }

    public StringProperty lastModifiedByProperty() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy.set(lastModifiedBy);
    }

    public String getOnTerminal() {
        return onTerminal.get();
    }

    public StringProperty onTerminalProperty() {
        return onTerminal;
    }

    public void setOnTerminal(String onTerminal) {
        this.onTerminal.set(onTerminal);
    }

    public static class TableObject implements Comparable<TableObject>{
        private final LongProperty clientIdColumn;
        private final StringProperty nameColumn;
        private final StringProperty mainOfficeAddressColumn;
        private final StringProperty contactNameColumn;
        private final StringProperty contactTelNumberColumn;
        private final StringProperty contactEmailColumn;
        private final ObjectProperty<LocalDateTime> creationDateColumn;
        private final ObjectProperty<LocalDateTime> modifyDateColumn;
        private final StringProperty createdByColumn;
        private final StringProperty lastModifiedByColumn;
        private final StringProperty onTerminalColumn;

        public TableObject(LongProperty clientIdColumn, StringProperty nameColumn, StringProperty mainOfficeAddressColumn, StringProperty contactNameColumn, StringProperty contactTelNumberColumn, StringProperty contactEmailColumn, ObjectProperty<LocalDateTime> creationDateColumn, ObjectProperty<LocalDateTime> modifyDateColumn, StringProperty createdByColumn, StringProperty lastModifiedByColumn, StringProperty onTerminalColumn) {
            this.clientIdColumn = clientIdColumn;
            this.nameColumn = nameColumn;
            this.mainOfficeAddressColumn = mainOfficeAddressColumn;
            this.contactNameColumn = contactNameColumn;
            this.contactTelNumberColumn = contactTelNumberColumn;
            this.contactEmailColumn = contactEmailColumn;
            this.creationDateColumn = creationDateColumn;
            this.modifyDateColumn = modifyDateColumn;
            this.createdByColumn = createdByColumn;
            this.lastModifiedByColumn = lastModifiedByColumn;
            this.onTerminalColumn = onTerminalColumn;
        }

        public long getClientIdColumn() {
            return clientIdColumn.get();
        }

        public LongProperty clientIdColumnProperty() {
            return clientIdColumn;
        }

        public void setClientIdColumn(long clientIdColumn) {
            this.clientIdColumn.set(clientIdColumn);
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

        public String getMainOfficeAddressColumn() {
            return mainOfficeAddressColumn.get();
        }

        public StringProperty mainOfficeAddressColumnProperty() {
            return mainOfficeAddressColumn;
        }

        public void setMainOfficeAddressColumn(String mainOfficeAddressColumn) {
            this.mainOfficeAddressColumn.set(mainOfficeAddressColumn);
        }

        public String getContactNameColumn() {
            return contactNameColumn.get();
        }

        public StringProperty contactNameColumnProperty() {
            return contactNameColumn;
        }

        public void setContactNameColumn(String contactNameColumn) {
            this.contactNameColumn.set(contactNameColumn);
        }

        public String getContactTelNumberColumn() {
            return contactTelNumberColumn.get();
        }

        public StringProperty contactTelNumberColumnProperty() {
            return contactTelNumberColumn;
        }

        public void setContactTelNumberColumn(String contactTelNumberColumn) {
            this.contactTelNumberColumn.set(contactTelNumberColumn);
        }

        public String getContactEmailColumn() {
            return contactEmailColumn.get();
        }

        public StringProperty contactEmailColumnProperty() {
            return contactEmailColumn;
        }

        public void setContactEmailColumn(String contactEmailColumn) {
            this.contactEmailColumn.set(contactEmailColumn);
        }

        public LocalDateTime getCreationDateColumn() {
            return creationDateColumn.get();
        }

        public ObjectProperty<LocalDateTime> creationDateColumnProperty() {
            return creationDateColumn;
        }

        public void setCreationDateColumn(LocalDateTime creationDateColumn) {
            this.creationDateColumn.set(creationDateColumn);
        }

        public LocalDateTime getModifyDateColumn() {
            return modifyDateColumn.get();
        }

        public ObjectProperty<LocalDateTime> modifyDateColumnProperty() {
            return modifyDateColumn;
        }

        public void setModifyDateColumn(LocalDateTime modifyDateColumn) {
            this.modifyDateColumn.set(modifyDateColumn);
        }

        public String getCreatedByColumn() {
            return createdByColumn.get();
        }

        public StringProperty createdByColumnProperty() {
            return createdByColumn;
        }

        public void setCreatedByColumn(String createdByColumn) {
            this.createdByColumn.set(createdByColumn);
        }

        public String getLastModifiedByColumn() {
            return lastModifiedByColumn.get();
        }

        public StringProperty lastModifiedByColumnProperty() {
            return lastModifiedByColumn;
        }

        public void setLastModifiedByColumn(String lastModifiedByColumn) {
            this.lastModifiedByColumn.set(lastModifiedByColumn);
        }

        public String getOnTerminalColumn() {
            return onTerminalColumn.get();
        }

        public StringProperty onTerminalColumnProperty() {
            return onTerminalColumn;
        }

        public void setOnTerminalColumn(String onTerminalColumn) {
            this.onTerminalColumn.set(onTerminalColumn);
        }

        public static TableObject createFromClientDTO(ClientDTO clientDTO) {
            return new TableObject(
                    new SimpleLongProperty(clientDTO.getId()),
                    new SimpleStringProperty(clientDTO.getName()),
                    new SimpleStringProperty(clientDTO.getMainOfficeAddress()),
                    new SimpleStringProperty(clientDTO.getContactName()),
                    new SimpleStringProperty(clientDTO.getContactTelNumber()),
                    new SimpleStringProperty(clientDTO.getContactEmail()),
                    new SimpleObjectProperty<>(clientDTO.getCreationDate()),
                    new SimpleObjectProperty<>(clientDTO.getModifyDate()),
                    new SimpleStringProperty(clientDTO.getCreatedBy()),
                    new SimpleStringProperty(clientDTO.getLastModifiedBy()),
                    new SimpleStringProperty(clientDTO.getOnTerminal()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(clientIdColumn.getValue(), that.clientIdColumn.getValue());
//                    && Objects.equal(nameColumn.getValue(), that.nameColumn.getValue())
//                    && Objects.equal(mainOfficeAddressColumn.getValue(), that.mainOfficeAddressColumn.getValue())
//                    && Objects.equal(contactNameColumn.getValue(), that.contactNameColumn.getValue())
//                    && Objects.equal(contactTelNumberColumn.getValue(), that.contactTelNumberColumn.getValue())
//                    && Objects.equal(contactEmailColumn.getValue(), that.contactEmailColumn.getValue())
//                    && Objects.equal(creationDateColumn.getValue(), that.creationDateColumn.getValue())
//                    && Objects.equal(modifyDateColumn.getValue(), that.modifyDateColumn.getValue())
//                    && Objects.equal(createdByColumn.getValue(), that.createdByColumn.getValue())
//                    && Objects.equal(onTerminalColumn.getValue(), that.onTerminalColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(clientIdColumn.getValue());
//            , nameColumn.getValue(), mainOfficeAddressColumn.getValue(),
//                    contactNameColumn.getValue(), contactTelNumberColumn.getValue(), contactEmailColumn.getValue(),
//                    creationDateColumn.getValue(), modifyDateColumn.getValue(), createdByColumn.getValue(), onTerminalColumn.getValue());
        }

        @Override
        public int compareTo(TableObject o) {
            return 0;
        }
    }
}
