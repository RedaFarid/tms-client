package soulintec.com.tmsclient.Graphics.Windows.TruckWindow;

import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Entities.TruckContainerDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TruckContainerModel {

    private final LongProperty truckContainerId = new SimpleLongProperty();
    private final StringProperty containerNumber = new SimpleStringProperty();
    private final StringProperty licenseNumber = new SimpleStringProperty("");
    private final ObjectProperty<LocalDate> licenceExpirationDate = new SimpleObjectProperty<>();
    private final DoubleProperty maximumWeightConstrain = new SimpleDoubleProperty();
    private final ObjectProperty<Permissions> permissions = new SimpleObjectProperty<>();
    private final StringProperty comment = new SimpleStringProperty("");
    private final StringProperty modifyDate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();
    private final StringProperty createdBy = new SimpleStringProperty();
    private final StringProperty modifiedBy = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();

    public long getTruckContainerId() {
        return truckContainerId.get();
    }

    public LongProperty truckContainerIdProperty() {
        return truckContainerId;
    }

    public void setTruckContainerId(long truckContainerId) {
        this.truckContainerId.set(truckContainerId);
    }

    public String getContainerNumber() {
        return containerNumber.get();
    }

    public StringProperty containerNumberProperty() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber.set(containerNumber);
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

    public double getMaximumWeightConstrain() {
        return maximumWeightConstrain.get();
    }

    public DoubleProperty maximumWeightConstrainProperty() {
        return maximumWeightConstrain;
    }

    public void setMaximumWeightConstrain(double maximumWeightConstrain) {
        this.maximumWeightConstrain.set(maximumWeightConstrain);
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

    public static class TableObject implements Comparable<TruckContainerModel.TableObject> {

        private final LongProperty truckContainerIdColumn;
        private final StringProperty containerNumberColumn;
        private final StringProperty licenseNumberColumn;
        private final ObjectProperty<LocalDate> licenceExpirationDateColumn;
        private final DoubleProperty maximumWeightConstrainColumn;
        private final ObjectProperty<Permissions> permissionsColumn;
        private final StringProperty commentColumn;
        private final ObjectProperty<LocalDateTime> creationDateColumn;
        private final ObjectProperty<LocalDateTime> modifyDateColumn;
        private final StringProperty createdByColumn;
        private final StringProperty modifiedByColumn;
        private final StringProperty onTerminalColumn;

        public TableObject(LongProperty truckContainerIdColumn, StringProperty containerNumberColumn, StringProperty licenseNumberColumn, ObjectProperty<LocalDate> licenceExpirationDateColumn, DoubleProperty maximumWeightConstrainColumn, ObjectProperty<Permissions> permissionsColumn, StringProperty commentColumn, ObjectProperty<LocalDateTime> creationDateColumn, ObjectProperty<LocalDateTime> modifyDateColumn, StringProperty createdByColumn, StringProperty modifiedByColumn, StringProperty onTerminalColumn) {
            this.truckContainerIdColumn = truckContainerIdColumn;
            this.containerNumberColumn = containerNumberColumn;
            this.licenseNumberColumn = licenseNumberColumn;
            this.licenceExpirationDateColumn = licenceExpirationDateColumn;
            this.maximumWeightConstrainColumn = maximumWeightConstrainColumn;
            this.permissionsColumn = permissionsColumn;
            this.commentColumn = commentColumn;
            this.creationDateColumn = creationDateColumn;
            this.modifyDateColumn = modifyDateColumn;
            this.createdByColumn = createdByColumn;
            this.modifiedByColumn = modifiedByColumn;
            this.onTerminalColumn = onTerminalColumn;
        }

        public long getTruckContainerIdColumn() {
            return truckContainerIdColumn.get();
        }

        public LongProperty truckContainerIdColumnProperty() {
            return truckContainerIdColumn;
        }

        public void setTruckContainerIdColumn(long truckContainerIdColumn) {
            this.truckContainerIdColumn.set(truckContainerIdColumn);
        }

        public String getContainerNumberColumn() {
            return containerNumberColumn.get();
        }

        public StringProperty containerNumberColumnProperty() {
            return containerNumberColumn;
        }

        public void setContainerNumberColumn(String containerNumberColumn) {
            this.containerNumberColumn.set(containerNumberColumn);
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

        public double getMaximumWeightConstrainColumn() {
            return maximumWeightConstrainColumn.get();
        }

        public DoubleProperty maximumWeightConstrainColumnProperty() {
            return maximumWeightConstrainColumn;
        }

        public void setMaximumWeightConstrainColumn(double maximumWeightConstrainColumn) {
            this.maximumWeightConstrainColumn.set(maximumWeightConstrainColumn);
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

        public static TruckContainerModel.TableObject createFromTruckContainerDTO(TruckContainerDTO truckContainerDTO) {
            return new TruckContainerModel.TableObject(
                    new SimpleLongProperty(truckContainerDTO.getId()),
                    new SimpleStringProperty(truckContainerDTO.getContainerNumber()),
                    new SimpleStringProperty(truckContainerDTO.getLicenceNumber()),
                    new SimpleObjectProperty<>(truckContainerDTO.getLicenceExpirationDate()),
                    new SimpleDoubleProperty(truckContainerDTO.getMaximumWeightConstrain()),
                    new SimpleObjectProperty<>(truckContainerDTO.getPermissions()),
                    new SimpleStringProperty(truckContainerDTO.getComment()),
                    new SimpleObjectProperty<>(truckContainerDTO.getCreationDate()),
                    new SimpleObjectProperty<>(truckContainerDTO.getModifyDate()),
                    new SimpleStringProperty(truckContainerDTO.getCreatedBy()),
                    new SimpleStringProperty(truckContainerDTO.getLastModifiedBy()),
                    new SimpleStringProperty(truckContainerDTO.getOnTerminal()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(truckContainerIdColumn.getValue(), that.truckContainerIdColumn.getValue());
//            &&
//                    Objects.equal(containerNumberColumn.getValue(), that.containerNumberColumn.getValue()) &&
//                    Objects.equal(licenseNumberColumn.getValue(), that.licenseNumberColumn.getValue()) &&
//                    Objects.equal(licenceExpirationDateColumn.getValue(), that.licenceExpirationDateColumn.getValue()) &&
//                    Objects.equal(maximumWeightConstrainColumn.getValue(), that.maximumWeightConstrainColumn.getValue()) &&
//                    Objects.equal(permissionsColumn.getValue(), that.permissionsColumn.getValue()) &&
//                    Objects.equal(commentColumn.getValue(), that.commentColumn.getValue()) &&
//                    Objects.equal(creationDateColumn.getValue(), that.creationDateColumn.getValue()) &&
//                    Objects.equal(modifyDateColumn.getValue(), that.modifyDateColumn.getValue()) &&
//                    Objects.equal(createdByColumn.getValue(), that.createdByColumn.getValue()) &&
//                    Objects.equal(onTerminalColumn.getValue(), that.onTerminalColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(truckContainerIdColumn.getValue());
//            , containerNumberColumn.getValue(), licenseNumberColumn.getValue(),
//                    licenceExpirationDateColumn.getValue(), maximumWeightConstrainColumn.getValue(), permissionsColumn.getValue(),
//                    commentColumn.getValue(), creationDateColumn.getValue(), modifyDateColumn.getValue(), createdByColumn.getValue(), onTerminalColumn.getValue());
        }

        @Override
        public int compareTo(TableObject o) {
            return 0;
        }
    }
}
