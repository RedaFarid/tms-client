package soulintec.com.tmsclient.Graphics.Windows.TruckWindow;

import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Entities.TruckTrailerDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TruckTrailerModel {

    private final LongProperty truckTrailerId = new SimpleLongProperty();
    private final StringProperty trailerNumber = new SimpleStringProperty();
    private final StringProperty licenseNumber = new SimpleStringProperty("");
    private final ObjectProperty<LocalDate> licenceExpirationDate = new SimpleObjectProperty<>();
    private final ObjectProperty<Permissions> permissions = new SimpleObjectProperty<>();
    private final StringProperty comment = new SimpleStringProperty("");
    private final StringProperty modifyDate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();
    private final StringProperty createdBy = new SimpleStringProperty();
    private final StringProperty modifiedBy = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();

    public long getTruckTrailerId() {
        return truckTrailerId.get();
    }

    public LongProperty truckTrailerIdProperty() {
        return truckTrailerId;
    }

    public void setTruckTrailerId(long truckTrailerId) {
        this.truckTrailerId.set(truckTrailerId);
    }

    public String getTrailerNumber() {
        return trailerNumber.get();
    }

    public StringProperty trailerNumberProperty() {
        return trailerNumber;
    }

    public void setTrailerNumber(String trailerNumber) {
        this.trailerNumber.set(trailerNumber);
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

    public static class TableObject implements Comparable<TruckTrailerModel.TableObject>{

        private final LongProperty truckTrailerIdColumn;
        private final StringProperty trailerNumberColumn;
        private final StringProperty licenseNumberColumn;
        private final ObjectProperty<LocalDate> licenceExpirationDateColumn;
        private final ObjectProperty<Permissions> permissionsColumn;
        private final StringProperty commentColumn;
        private final ObjectProperty<LocalDateTime> creationDateColumn;
        private final ObjectProperty<LocalDateTime> modifyDateColumn;
        private final StringProperty createdByColumn;
        private final StringProperty modifiedByColumn;
        private final StringProperty onTerminalColumn;

        public TableObject(LongProperty truckTrailerIdColumn, StringProperty trailerNumberColumn, StringProperty licenseNumberColumn, ObjectProperty<LocalDate> licenceExpirationDateColumn, ObjectProperty<Permissions> permissionsColumn, StringProperty commentColumn, ObjectProperty<LocalDateTime> creationDateColumn, ObjectProperty<LocalDateTime> modifyDateColumn, StringProperty createdByColumn, StringProperty modifiedByColumn, StringProperty onTerminalColumn) {
            this.truckTrailerIdColumn = truckTrailerIdColumn;
            this.trailerNumberColumn = trailerNumberColumn;
            this.licenseNumberColumn = licenseNumberColumn;
            this.licenceExpirationDateColumn = licenceExpirationDateColumn;
            this.permissionsColumn = permissionsColumn;
            this.commentColumn = commentColumn;
            this.creationDateColumn = creationDateColumn;
            this.modifyDateColumn = modifyDateColumn;
            this.createdByColumn = createdByColumn;
            this.modifiedByColumn = modifiedByColumn;
            this.onTerminalColumn = onTerminalColumn;
        }

        public long getTruckTrailerIdColumn() {
            return truckTrailerIdColumn.get();
        }

        public LongProperty truckTrailerIdColumnProperty() {
            return truckTrailerIdColumn;
        }

        public void setTruckTrailerIdColumn(long truckTrailerIdColumn) {
            this.truckTrailerIdColumn.set(truckTrailerIdColumn);
        }

        public String getTrailerNumberColumn() {
            return trailerNumberColumn.get();
        }

        public StringProperty trailerNumberColumnProperty() {
            return trailerNumberColumn;
        }

        public void setTrailerNumberColumn(String trailerNumberColumn) {
            this.trailerNumberColumn.set(trailerNumberColumn);
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

        public static TruckTrailerModel.TableObject createFromTruckTrailerDTO(TruckTrailerDTO truckTrailerDTO) {
            return new TruckTrailerModel.TableObject(
                    new SimpleLongProperty(truckTrailerDTO.getId()),
                    new SimpleStringProperty(truckTrailerDTO.getTrailerNumber()),
                    new SimpleStringProperty(truckTrailerDTO.getLicenceNumber()),
                    new SimpleObjectProperty<>(truckTrailerDTO.getLicenceExpirationDate()),
                    new SimpleObjectProperty<>(truckTrailerDTO.getPermissions()),
                    new SimpleStringProperty(truckTrailerDTO.getComment()),
                    new SimpleObjectProperty<>(truckTrailerDTO.getCreationDate()),
                    new SimpleObjectProperty<>(truckTrailerDTO.getModifyDate()),
                    new SimpleStringProperty(truckTrailerDTO.getCreatedBy()),
                    new SimpleStringProperty(truckTrailerDTO.getLastModifiedBy()),
                    new SimpleStringProperty(truckTrailerDTO.getOnTerminal()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(truckTrailerIdColumn.getValue(), that.truckTrailerIdColumn.getValue()) ;
//            &&
//                    Objects.equal(trailerNumberColumn.getValue(), that.trailerNumberColumn.getValue()) &&
//                    Objects.equal(licenseNumberColumn.getValue(), that.licenseNumberColumn.getValue()) &&
//                    Objects.equal(licenceExpirationDateColumn.getValue(), that.licenceExpirationDateColumn.getValue()) &&
//                    Objects.equal(permissionsColumn.getValue(), that.permissionsColumn.getValue()) &&
//                    Objects.equal(commentColumn.getValue(), that.commentColumn.getValue()) &&
//                    Objects.equal(creationDateColumn.getValue(), that.creationDateColumn.getValue()) &&
//                    Objects.equal(modifyDateColumn.getValue(), that.modifyDateColumn.getValue()) &&
//                    Objects.equal(createdByColumn.getValue(), that.createdByColumn.getValue()) &&
//                    Objects.equal(onTerminalColumn.getValue(), that.onTerminalColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(truckTrailerIdColumn.getValue());
//            , trailerNumberColumn.getValue(), licenseNumberColumn.getValue(),
//                    licenceExpirationDateColumn.getValue(), permissionsColumn.getValue(),
//                    commentColumn.getValue(), creationDateColumn.getValue(), modifyDateColumn.getValue(), createdByColumn.getValue(), onTerminalColumn.getValue());
        }

        @Override
        public int compareTo(TableObject o) {
            return 0;
        }
    }
}
