package soulintec.com.tmsclient.Graphics.Windows.TanksWindow;

import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.TankDTO;


import java.time.LocalDateTime;

public class TanksModel {
    private final LongProperty tankId = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final LongProperty station = new SimpleLongProperty(0);
    private final DoubleProperty capacity = new SimpleDoubleProperty(0.0);
    private final DoubleProperty qty = new SimpleDoubleProperty(0.0);
    private final StringProperty dateOfQtySet = new SimpleStringProperty();
    private final StringProperty userOfQtySet = new SimpleStringProperty();
    private final LongProperty materialID = new SimpleLongProperty(0);
    private final StringProperty modifyDate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();
    private final StringProperty createdBy = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();
    private final DoubleProperty calculatedQty = new SimpleDoubleProperty(0.0);

    public long getTankId() {
        return tankId.get();
    }

    public LongProperty tankIdProperty() {
        return tankId;
    }

    public void setTankId(long tankId) {
        this.tankId.set(tankId);
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

    public long getStation() {
        return station.get();
    }

    public LongProperty stationProperty() {
        return station;
    }

    public void setStation(long station) {
        this.station.set(station);
    }

    public double getCapacity() {
        return capacity.get();
    }

    public DoubleProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity.set(capacity);
    }

    public double getQty() {
        return qty.get();
    }

    public DoubleProperty qtyProperty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty.set(qty);
    }

    public String getDateOfQtySet() {
        return dateOfQtySet.get();
    }

    public StringProperty dateOfQtySetProperty() {
        return dateOfQtySet;
    }

    public void setDateOfQtySet(String dateOfQtySet) {
        this.dateOfQtySet.set(dateOfQtySet);
    }

    public String getUserOfQtySet() {
        return userOfQtySet.get();
    }

    public StringProperty userOfQtySetProperty() {
        return userOfQtySet;
    }

    public void setUserOfQtySet(String userOfQtySet) {
        this.userOfQtySet.set(userOfQtySet);
    }

    public long getMaterialID() {
        return materialID.get();
    }

    public LongProperty materialIDProperty() {
        return materialID;
    }

    public void setMaterialID(long materialID) {
        this.materialID.set(materialID);
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

    public double getCalculatedQty() {
        return calculatedQty.get();
    }

    public DoubleProperty calculatedQtyProperty() {
        return calculatedQty;
    }

    public void setCalculatedQty(double calculatedQty) {
        this.calculatedQty.set(calculatedQty);
    }

    public static class TableObject implements Comparable<TanksModel.TableObject>{
        private final LongProperty tankIdColumn;
        private final StringProperty nameColumn;
        private final LongProperty stationColumn;
        private final DoubleProperty capacityColumn;
        private final DoubleProperty qtyColumn;
        private final ObjectProperty<LocalDateTime> dateOfQtySetColumn;
        private final StringProperty userOfQtySetColumn;
        private final LongProperty materialIDColumn;
        private final ObjectProperty<LocalDateTime> creationDateColumn;
        private final ObjectProperty<LocalDateTime> modifyDateColumn;
        private final StringProperty createdByColumn;
        private final StringProperty onTerminalColumn;
        private final DoubleProperty calculatedQtyColumn;

        public TableObject(LongProperty tankIdColumn, StringProperty nameColumn, LongProperty stationColumn, DoubleProperty capacityColumn, DoubleProperty qtyColumn, ObjectProperty<LocalDateTime> dateOfQtySetColumn, StringProperty userOfQtySetColumn, LongProperty materialIDColumn, ObjectProperty<LocalDateTime> creationDateColumn, ObjectProperty<LocalDateTime> modifyDateColumn, StringProperty createdByColumn, StringProperty onTerminalColumn,  DoubleProperty calculatedQtyColumn) {
            this.tankIdColumn = tankIdColumn;
            this.nameColumn = nameColumn;
            this.stationColumn = stationColumn;
            this.capacityColumn = capacityColumn;
            this.qtyColumn = qtyColumn;
            this.dateOfQtySetColumn = dateOfQtySetColumn;
            this.userOfQtySetColumn = userOfQtySetColumn;
            this.materialIDColumn = materialIDColumn;
            this.creationDateColumn = creationDateColumn;
            this.modifyDateColumn = modifyDateColumn;
            this.createdByColumn = createdByColumn;
            this.onTerminalColumn = onTerminalColumn;
            this.calculatedQtyColumn = calculatedQtyColumn;
        }

        public long getTankIdColumn() {
            return tankIdColumn.get();
        }

        public LongProperty tankIdColumnProperty() {
            return tankIdColumn;
        }

        public void setTankIdColumn(long tankIdColumn) {
            this.tankIdColumn.set(tankIdColumn);
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

        public long getStationColumn() {
            return stationColumn.get();
        }

        public LongProperty stationColumnProperty() {
            return stationColumn;
        }

        public void setStationColumn(long stationColumn) {
            this.stationColumn.set(stationColumn);
        }

        public double getCapacityColumn() {
            return capacityColumn.get();
        }

        public DoubleProperty capacityColumnProperty() {
            return capacityColumn;
        }

        public void setCapacityColumn(double capacityColumn) {
            this.capacityColumn.set(capacityColumn);
        }

        public double getQtyColumn() {
            return qtyColumn.get();
        }

        public DoubleProperty qtyColumnProperty() {
            return qtyColumn;
        }

        public void setQtyColumn(double qtyColumn) {
            this.qtyColumn.set(qtyColumn);
        }

        public LocalDateTime getDateOfQtySetColumn() {
            return dateOfQtySetColumn.get();
        }

        public ObjectProperty<LocalDateTime> dateOfQtySetColumnProperty() {
            return dateOfQtySetColumn;
        }

        public void setDateOfQtySetColumn(LocalDateTime dateOfQtySetColumn) {
            this.dateOfQtySetColumn.set(dateOfQtySetColumn);
        }

        public String getUserOfQtySetColumn() {
            return userOfQtySetColumn.get();
        }

        public StringProperty userOfQtySetColumnProperty() {
            return userOfQtySetColumn;
        }

        public void setUserOfQtySetColumn(String userOfQtySetColumn) {
            this.userOfQtySetColumn.set(userOfQtySetColumn);
        }

        public long getMaterialIDColumn() {
            return materialIDColumn.get();
        }

        public LongProperty materialIDColumnProperty() {
            return materialIDColumn;
        }

        public void setMaterialIDColumn(long materialIDColumn) {
            this.materialIDColumn.set(materialIDColumn);
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

        public double getCalculatedQtyColumn() {
            return calculatedQtyColumn.get();
        }

        public DoubleProperty calculatedQtyColumnProperty() {
            return calculatedQtyColumn;
        }

        public void setCalculatedQtyColumn(double calculatedQtyColumn) {
            this.calculatedQtyColumn.set(calculatedQtyColumn);
        }

        public static TableObject createFromTankDTO(TankDTO tankDTO) {
            return new TanksModel.TableObject(
                    new SimpleLongProperty(tankDTO.getId()),
                    new SimpleStringProperty(tankDTO.getName()),
                    new SimpleLongProperty(tankDTO.getStation()),
                    new SimpleDoubleProperty(tankDTO.getCapacity()),
                    new SimpleDoubleProperty(tankDTO.getQty()),
                    new SimpleObjectProperty<LocalDateTime>(tankDTO.getDateOfQtySet()),
                    new SimpleStringProperty(tankDTO.getUserOfQtySet()),
                    new SimpleLongProperty(tankDTO.getMaterialID()),
                    new SimpleObjectProperty<>(tankDTO.getCreationDate()),
                    new SimpleObjectProperty<>(tankDTO.getModificationDate()),
                    new SimpleStringProperty(tankDTO.getCreatedBy()),
                    new SimpleStringProperty(tankDTO.getOnTerminal()),
                    new SimpleDoubleProperty(tankDTO.getCalculatedQty()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(tankIdColumn.getValue(), that.tankIdColumn.getValue()
//                    && Objects.equal(nameColumn.getValue(), that.nameColumn.getValue()) &&
//                    Objects.equal(capacityColumn.getValue(), that.capacityColumn.getValue()) && Objects.equal(qtyColumn.getValue(), that.qtyColumn.getValue()) &&
//                    Objects.equal(dateOfQtySetColumn.getValue(), that.dateOfQtySetColumn.getValue()) && Objects.equal(userOfQtySetColumn.getValue(), that.userOfQtySetColumn.getValue()) &&
//                    Objects.equal(materialIDColumn.getValue(), that.materialIDColumn.getValue()) && Objects.equal(creationDateColumn.getValue(), that.creationDateColumn.getValue()) &&
//                    Objects.equal(modifyDateColumn.getValue(), that.modifyDateColumn.getValue()) && Objects.equal(createdByColumn.getValue(), that.createdByColumn.getValue()) &&
//                    Objects.equal(onTerminalColumn.getValue(), that.onTerminalColumn.getValue()) && Objects.equal(materialNameColumn.getValue(), that.materialNameColumn.getValue()) &&
//                    Objects.equal(calculatedQtyColumn.getValue(), that.calculatedQtyColumn.getValue()
                    );
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(tankIdColumn.getValue(), nameColumn.getValue()
//                    , capacityColumn.getValue(), qtyColumn.getValue(), dateOfQtySetColumn.getValue(), userOfQtySetColumn.getValue(),
//                    materialIDColumn.getValue(), creationDateColumn.getValue(), modifyDateColumn.getValue(), createdByColumn.getValue(), onTerminalColumn.getValue(), materialNameColumn.getValue(),
//                    calculatedQtyColumn.getValue()
            );
        }

        @Override
        public int compareTo(TableObject o) {
            return 0;
        }
    }
}
