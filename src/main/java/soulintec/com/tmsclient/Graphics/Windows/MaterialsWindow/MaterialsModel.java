package soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow;

import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.MaterialDTO;

import java.time.LocalDateTime;

public class MaterialsModel {
    private final LongProperty materialId = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty modifyDate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();
    private final StringProperty createdBy = new SimpleStringProperty();
    private final StringProperty modifiedBy = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();

    public long getMaterialId() {
        return materialId.get();
    }

    public LongProperty materialIdProperty() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId.set(materialId);
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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
        private final LongProperty materialIdColumn;
        private final StringProperty nameColumn;
        private final StringProperty descriptionColumn;
        private final ObjectProperty<LocalDateTime> creationDateColumn;
        private final ObjectProperty<LocalDateTime> modifyDateColumn;
        private final StringProperty createdByColumn;
        private final StringProperty modifiedByColumn;
        private final StringProperty onTerminalColumn;

        public TableObject(LongProperty materialIdColumn, StringProperty nameColumn, StringProperty descriptionColumn, ObjectProperty<LocalDateTime> creationDateColumn, ObjectProperty<LocalDateTime> modifyDateColumn, StringProperty createdByColumn, StringProperty modifiedByColumn, StringProperty onTerminalColumn) {
            this.materialIdColumn = materialIdColumn;
            this.nameColumn = nameColumn;
            this.descriptionColumn = descriptionColumn;
            this.creationDateColumn = creationDateColumn;
            this.modifyDateColumn = modifyDateColumn;
            this.createdByColumn = createdByColumn;
            this.modifiedByColumn = modifiedByColumn;
            this.onTerminalColumn = onTerminalColumn;
        }

        public long getMaterialIdColumn() {
            return materialIdColumn.get();
        }

        public LongProperty materialIdColumnProperty() {
            return materialIdColumn;
        }

        public void setMaterialIdColumn(long materialIdColumn) {
            this.materialIdColumn.set(materialIdColumn);
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

        public String getDescriptionColumn() {
            return descriptionColumn.get();
        }

        public StringProperty descriptionColumnProperty() {
            return descriptionColumn;
        }

        public void setDescriptionColumn(String descriptionColumn) {
            this.descriptionColumn.set(descriptionColumn);
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

        public static MaterialsModel.TableObject createFromMaterialDTO(MaterialDTO materialDTO) {
            return new MaterialsModel.TableObject(
                    new SimpleLongProperty(materialDTO.getId()),
                    new SimpleStringProperty(materialDTO.getName()),
                    new SimpleStringProperty(materialDTO.getDescription()),
                    new SimpleObjectProperty<>(materialDTO.getCreationDate()),
                    new SimpleObjectProperty<>(materialDTO.getModifyDate()),
                    new SimpleStringProperty(materialDTO.getCreatedBy()),
                    new SimpleStringProperty(materialDTO.getLastModifiedBy()),
                    new SimpleStringProperty(materialDTO.getOnTerminal()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(materialIdColumn.getValue(), that.materialIdColumn.getValue());
//&&
//                    Objects.equal(nameColumn.getValue(), that.nameColumn.getValue())
//                    && Objects.equal(descriptionColumn.getValue(), that.descriptionColumn.getValue()) &&
//                    Objects.equal(creationDateColumn.getValue(), that.creationDateColumn.getValue()) &&
//                    Objects.equal(modifyDateColumn.getValue(), that.modifyDateColumn.getValue()) &&
//                    Objects.equal(createdByColumn.getValue(), that.createdByColumn.getValue()) &&
//                    Objects.equal(onTerminalColumn.getValue(), that.onTerminalColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(materialIdColumn.getValue());
//                    , nameColumn.getValue(), descriptionColumn.getValue(),
//                    creationDateColumn.getValue(), modifyDateColumn.getValue(), createdByColumn.getValue(), onTerminalColumn.getValue());
        }

        @Override
        public String toString() {
            return "TableObject{" +
                    "materialIdColumn=" + materialIdColumn.getValue() +
                    ", nameColumn=" + nameColumn.getValue() +
                    ", descriptionColumn=" + descriptionColumn.getValue() +
                    ", creationDateColumn=" + creationDateColumn.getValue() +
                    ", modifyDateColumn=" + modifyDateColumn.getValue() +
                    ", createdByColumn=" + createdByColumn.getValue() +
                    ", onTerminalColumn=" + onTerminalColumn.getValue() +
                    '}';
        }

        @Override
        public int compareTo(TableObject o) {
            return this.getMaterialIdColumn() > o.getMaterialIdColumn() ? 1 : -1;
        }
    }
}
