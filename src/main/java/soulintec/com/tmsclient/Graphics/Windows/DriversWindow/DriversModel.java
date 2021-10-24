package soulintec.com.tmsclient.Graphics.Windows.DriversWindow;


import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.DriverDTO;
import soulintec.com.tmsclient.Entities.Permissions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DriversModel {
    private final LongProperty driverId = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty licenseNumber = new SimpleStringProperty("");
    private final ObjectProperty<LocalDate> licenceExpirationDate = new SimpleObjectProperty<>();
    private final StringProperty mobileNumber = new SimpleStringProperty("");
    private final ObjectProperty<Permissions> permissions = new SimpleObjectProperty<>();
    private final StringProperty comment = new SimpleStringProperty("");
    private final StringProperty modifyDate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();
    private final StringProperty createdBy = new SimpleStringProperty();
    private final StringProperty modifiedBy = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();

    public long getDriverId() {
        return driverId.get();
    }

    public LongProperty driverIdProperty() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId.set(driverId);
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

    public String getLicenseNumber() {
        return licenseNumber.get();
    }

    public StringProperty licenseNumberProperty() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber.set(licenseNumber);
    }

    public LocalDate getLicenceExpirationDate() {
        return licenceExpirationDate.get();
    }

    public ObjectProperty<LocalDate> licenceExpirationDateProperty() {
        return licenceExpirationDate;
    }

    public void setLicenceExpirationDate(LocalDate licenceExpirationDate) {
        this.licenceExpirationDate.set(licenceExpirationDate);
    }

    public String getMobileNumber() {
        return mobileNumber.get();
    }

    public StringProperty mobileNumberProperty() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber.set(mobileNumber);
    }

    public Permissions getPermissions() {
        return permissions.get();
    }

    public ObjectProperty<Permissions> permissionsProperty() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions.set(permissions);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
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

    public String getModifiedBy() {
        return modifiedBy.get();
    }

    public StringProperty modifiedByProperty() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy.set(modifiedBy);
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
        private final LongProperty driverIdColumn;
        private final StringProperty nameColumn;
        private final StringProperty licenseNumberColumn;
        private final ObjectProperty<LocalDate> licenceExpirationDateColumn;
        private final StringProperty mobileNumberColumn;
        private final ObjectProperty<Permissions> permissionsColumn;
        private final StringProperty commentColumn;
        private final ObjectProperty<LocalDateTime> creationDateColumn;
        private final ObjectProperty<LocalDateTime> modifyDateColumn;
        private final StringProperty createdByColumn;
        private final StringProperty modifiedByColumn;
        private final StringProperty onTerminalColumn;

        public TableObject(LongProperty driverIdColumn, StringProperty nameColumn, StringProperty licenseNumberColumn, ObjectProperty<LocalDate> licenceExpirationDateColumn, StringProperty mobileNumberColumn, ObjectProperty<Permissions> permissionsColumn, StringProperty commentColumn, ObjectProperty<LocalDateTime> creationDateColumn, ObjectProperty<LocalDateTime> modifyDateColumn, StringProperty createdByColumn, StringProperty modifiedByColumn, StringProperty onTerminalColumn) {
            this.driverIdColumn = driverIdColumn;
            this.nameColumn = nameColumn;
            this.licenseNumberColumn = licenseNumberColumn;
            this.licenceExpirationDateColumn = licenceExpirationDateColumn;
            this.mobileNumberColumn = mobileNumberColumn;
            this.permissionsColumn = permissionsColumn;
            this.commentColumn = commentColumn;
            this.creationDateColumn = creationDateColumn;
            this.modifyDateColumn = modifyDateColumn;
            this.createdByColumn = createdByColumn;
            this.modifiedByColumn = modifiedByColumn;
            this.onTerminalColumn = onTerminalColumn;
        }

        public long getDriverIdColumn() {
            return driverIdColumn.get();
        }

        public LongProperty driverIdColumnProperty() {
            return driverIdColumn;
        }

        public void setDriverIdColumn(long driverIdColumn) {
            this.driverIdColumn.set(driverIdColumn);
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

        public String getLicenseNumberColumn() {
            return licenseNumberColumn.get();
        }

        public StringProperty licenseNumberColumnProperty() {
            return licenseNumberColumn;
        }

        public void setLicenseNumberColumn(String licenseNumberColumn) {
            this.licenseNumberColumn.set(licenseNumberColumn);
        }

        public LocalDate getLicenceExpirationDateColumn() {
            return licenceExpirationDateColumn.get();
        }

        public ObjectProperty<LocalDate> licenceExpirationDateColumnProperty() {
            return licenceExpirationDateColumn;
        }

        public void setLicenceExpirationDateColumn(LocalDate licenceExpirationDateColumn) {
            this.licenceExpirationDateColumn.set(licenceExpirationDateColumn);
        }

        public String getMobileNumberColumn() {
            return mobileNumberColumn.get();
        }

        public StringProperty mobileNumberColumnProperty() {
            return mobileNumberColumn;
        }

        public void setMobileNumberColumn(String mobileNumberColumn) {
            this.mobileNumberColumn.set(mobileNumberColumn);
        }

        public Permissions getPermissionsColumn() {
            return permissionsColumn.get();
        }

        public ObjectProperty<Permissions> permissionsColumnProperty() {
            return permissionsColumn;
        }

        public void setPermissionsColumn(Permissions permissionsColumn) {
            this.permissionsColumn.set(permissionsColumn);
        }

        public String getCommentColumn() {
            return commentColumn.get();
        }

        public StringProperty commentColumnProperty() {
            return commentColumn;
        }

        public void setCommentColumn(String commentColumn) {
            this.commentColumn.set(commentColumn);
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

        public String getModifiedByColumn() {
            return modifiedByColumn.get();
        }

        public StringProperty modifiedByColumnProperty() {
            return modifiedByColumn;
        }

        public void setModifiedByColumn(String modifiedByColumn) {
            this.modifiedByColumn.set(modifiedByColumn);
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

        public static TableObject createFromDriverDTO(DriverDTO driverDTO) {
            return new TableObject(
                    new SimpleLongProperty(driverDTO.getId()),
                    new SimpleStringProperty(driverDTO.getName()),
                    new SimpleStringProperty(driverDTO.getLicenseNumber()),
                    new SimpleObjectProperty<LocalDate>(driverDTO.getLicenceExpirationDate()),
                    new SimpleStringProperty(driverDTO.getMobileNumber()),
                    new SimpleObjectProperty<>(driverDTO.getPermissions()),
                    new SimpleStringProperty(driverDTO.getComment()),
                    new SimpleObjectProperty<>(driverDTO.getCreationDate()),
                    new SimpleObjectProperty<>(driverDTO.getModifyDate()),
                    new SimpleStringProperty(driverDTO.getCreatedBy()),
                    new SimpleStringProperty(driverDTO.getLastModifiedBy()),
                    new SimpleStringProperty(driverDTO.getOnTerminal()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(driverIdColumn.getValue(), that.driverIdColumn.getValue());
//                    && Objects.equal(nameColumn.getValue(), that.nameColumn.getValue())
//                    && Objects.equal(licenseNumberColumn.getValue(), that.licenseNumberColumn.getValue())
//                    && Objects.equal(licenceExpirationDateColumn.getValue(), that.licenceExpirationDateColumn.getValue())
//                    && Objects.equal(mobileNumberColumn.getValue(), that.mobileNumberColumn.getValue())
//                    && Objects.equal(permissionsColumn.getValue(), that.permissionsColumn.getValue())
//                    && Objects.equal(commentColumn.getValue(), that.commentColumn.getValue())
//                    && Objects.equal(creationDateColumn.getValue(), that.creationDateColumn.getValue())
//                    && Objects.equal(modifyDateColumn.getValue(), that.modifyDateColumn.getValue())
//                    && Objects.equal(createdByColumn.getValue(), that.createdByColumn.getValue())
//                    && Objects.equal(onTerminalColumn.getValue(), that.onTerminalColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(driverIdColumn.getValue());
//            , nameColumn.getValue(), licenseNumberColumn.getValue(),
//                    licenceExpirationDateColumn.getValue(), mobileNumberColumn.getValue(), permissionsColumn.getValue(),
//                    commentColumn.getValue(), creationDateColumn.getValue(), modifyDateColumn.getValue(),
//                    createdByColumn.getValue(), onTerminalColumn.getValue());
        }

        @Override
        public int compareTo(TableObject o) {
            return 0;
        }
    }
}
