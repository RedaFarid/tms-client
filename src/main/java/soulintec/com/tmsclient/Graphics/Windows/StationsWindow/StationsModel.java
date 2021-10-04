package soulintec.com.tmsclient.Graphics.Windows.StationsWindow;


import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.DriverDTO;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Entities.StationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StationsModel {
    private final LongProperty stationId = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty location = new SimpleStringProperty("");
    private final StringProperty comment = new SimpleStringProperty("");
    private final ObjectProperty<String> computerName = new SimpleObjectProperty<>();
    private final StringProperty modifyDate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();
    private final StringProperty createdBy = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();

    public long getStationId() {
        return stationId.get();
    }

    public LongProperty stationIdProperty() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId.set(stationId);
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

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getComputerName() {
        return computerName.get();
    }

    public ObjectProperty<String> computerNameProperty() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName.set(computerName);
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
        private final LongProperty stationIdColumn;
        private final StringProperty nameColumn;
        private final StringProperty locationColumn;
        private final ObjectProperty<String> computerNameColumn;
        private final StringProperty commentColumn;
        private final ObjectProperty<LocalDateTime> creationDateColumn;
        private final ObjectProperty<LocalDateTime> modifyDateColumn;
        private final StringProperty createdByColumn;
        private final StringProperty onTerminalColumn;

        public TableObject(LongProperty stationIdColumn, StringProperty nameColumn, StringProperty locationColumn, ObjectProperty<String> computerNameColumn, StringProperty commentColumn, ObjectProperty<LocalDateTime> creationDateColumn, ObjectProperty<LocalDateTime> modifyDateColumn, StringProperty createdByColumn, StringProperty onTerminalColumn) {
            this.stationIdColumn = stationIdColumn;
            this.nameColumn = nameColumn;
            this.locationColumn = locationColumn;
            this.computerNameColumn = computerNameColumn;
            this.commentColumn = commentColumn;
            this.creationDateColumn = creationDateColumn;
            this.modifyDateColumn = modifyDateColumn;
            this.createdByColumn = createdByColumn;
            this.onTerminalColumn = onTerminalColumn;
        }

        public long getStationIdColumn() {
            return stationIdColumn.get();
        }

        public LongProperty stationIdColumnProperty() {
            return stationIdColumn;
        }

        public void setStationIdColumn(long stationIdColumn) {
            this.stationIdColumn.set(stationIdColumn);
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

        public String getLocationColumn() {
            return locationColumn.get();
        }

        public StringProperty locationColumnProperty() {
            return locationColumn;
        }

        public void setLocationColumn(String locationColumn) {
            this.locationColumn.set(locationColumn);
        }

        public String getComputerNameColumn() {
            return computerNameColumn.get();
        }

        public ObjectProperty<String> computerNameColumnProperty() {
            return computerNameColumn;
        }

        public void setComputerNameColumn(String computerNameColumn) {
            this.computerNameColumn.set(computerNameColumn);
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

        public String getOnTerminalColumn() {
            return onTerminalColumn.get();
        }

        public StringProperty onTerminalColumnProperty() {
            return onTerminalColumn;
        }

        public void setOnTerminalColumn(String onTerminalColumn) {
            this.onTerminalColumn.set(onTerminalColumn);
        }

        public static TableObject createFromStationDTO(StationDTO stationDTO) {
            return new TableObject(
                    new SimpleLongProperty(stationDTO.getId()),
                    new SimpleStringProperty(stationDTO.getStationName()),
                    new SimpleStringProperty(stationDTO.getLocation()),
                    new SimpleObjectProperty<>(stationDTO.getComputerName()),
                    new SimpleStringProperty(stationDTO.getComment()),
                    new SimpleObjectProperty<>(stationDTO.getCreationDate()),
                    new SimpleObjectProperty<>(stationDTO.getModifyDate()),
                    new SimpleStringProperty(stationDTO.getCreatedBy()),
                    new SimpleStringProperty(stationDTO.getOnTerminal()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(stationIdColumn.getValue(), that.stationIdColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(stationIdColumn.getValue());
        }

        @Override
        public int compareTo(TableObject o) {
            return 0;
        }
    }
}
