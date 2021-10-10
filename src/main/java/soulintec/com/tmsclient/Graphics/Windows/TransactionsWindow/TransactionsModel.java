package soulintec.com.tmsclient.Graphics.Windows.TransactionsWindow;

import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.OperationType;
import soulintec.com.tmsclient.Entities.TankDTO;
import soulintec.com.tmsclient.Entities.TransactionDTO;

import java.time.LocalDateTime;

public class TransactionsModel {
    //TODO--Check on delete for all keys
    //TODO--Check filtering tool for all
    //TODO--Make all info windows from main window
    //TODO--Make all equals and hashes for id only and modify controllers
    private final LongProperty transactionId = new SimpleLongProperty();
    private final LongProperty material = new SimpleLongProperty();
    private final LongProperty station = new SimpleLongProperty();
    private final LongProperty tank = new SimpleLongProperty();
    private final LongProperty driver = new SimpleLongProperty();
    private final LongProperty truckTrailer = new SimpleLongProperty();
    private final LongProperty truckContainer = new SimpleLongProperty();
    private final ObjectProperty<OperationType> operationType = new SimpleObjectProperty<>();
    private final DoubleProperty qty = new SimpleDoubleProperty(0.0);
    private final ObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>(LocalDateTime.now());
    private final StringProperty modifyDate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();
    private final StringProperty createdBy = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();

    public long getTransactionId() {
        return transactionId.get();
    }

    public LongProperty transactionIdProperty() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId.set(transactionId);
    }

    public long getMaterial() {
        return material.get();
    }

    public LongProperty materialProperty() {
        return material;
    }

    public void setMaterial(long material) {
        this.material.set(material);
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

    public long getTank() {
        return tank.get();
    }

    public LongProperty tankProperty() {
        return tank;
    }

    public void setTank(long tank) {
        this.tank.set(tank);
    }

    public long getDriver() {
        return driver.get();
    }

    public LongProperty driverProperty() {
        return driver;
    }

    public void setDriver(long driver) {
        this.driver.set(driver);
    }

    public long getTruckTrailer() {
        return truckTrailer.get();
    }

    public LongProperty truckTrailerProperty() {
        return truckTrailer;
    }

    public void setTruckTrailer(long truckTrailer) {
        this.truckTrailer.set(truckTrailer);
    }

    public long getTruckContainer() {
        return truckContainer.get();
    }

    public LongProperty truckContainerProperty() {
        return truckContainer;
    }

    public void setTruckContainer(long truckContainer) {
        this.truckContainer.set(truckContainer);
    }

    public OperationType getOperationType() {
        return operationType.get();
    }

    public ObjectProperty<OperationType> operationTypeProperty() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType.set(operationType);
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

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    public ObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime.set(dateTime);
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

    public static class TableObject {
        private final LongProperty transactionIdColumn;
        private final LongProperty materialColumn;
        private final LongProperty stationColumn;
        private final LongProperty tankColumn;
        private final LongProperty driverColumn;
        private final LongProperty truckTrailerColumn;
        private final LongProperty truckContainerColumn;
        private final ObjectProperty<OperationType> operationTypeColumn;
        private final DoubleProperty qtyColumn;
        private final ObjectProperty<LocalDateTime> dateTimeColumn;
        private final ObjectProperty<LocalDateTime> creationDateColumn;
        private final ObjectProperty<LocalDateTime> modifyDateColumn;
        private final StringProperty createdByColumn;
        private final StringProperty onTerminalColumn;

        public TableObject(LongProperty transactionIdColumn, LongProperty materialColumn, LongProperty stationColumn, LongProperty tankColumn, LongProperty driverColumn, LongProperty truckTrailerColumn, LongProperty truckContainerColumn, ObjectProperty<OperationType> operationTypeColumn, DoubleProperty qtyColumn, ObjectProperty<LocalDateTime> dateTimeColumn, ObjectProperty<LocalDateTime> creationDateColumn, ObjectProperty<LocalDateTime> modifyDateColumn, StringProperty createdByColumn, StringProperty onTerminalColumn) {
            this.transactionIdColumn = transactionIdColumn;
            this.materialColumn = materialColumn;
            this.stationColumn = stationColumn;
            this.tankColumn = tankColumn;
            this.driverColumn = driverColumn;
            this.truckTrailerColumn = truckTrailerColumn;
            this.truckContainerColumn = truckContainerColumn;
            this.operationTypeColumn = operationTypeColumn;
            this.qtyColumn = qtyColumn;
            this.dateTimeColumn = dateTimeColumn;
            this.creationDateColumn = creationDateColumn;
            this.modifyDateColumn = modifyDateColumn;
            this.createdByColumn = createdByColumn;
            this.onTerminalColumn = onTerminalColumn;
        }

        public long getTransactionIdColumn() {
            return transactionIdColumn.get();
        }

        public LongProperty transactionIdColumnProperty() {
            return transactionIdColumn;
        }

        public void setTransactionIdColumn(long transactionIdColumn) {
            this.transactionIdColumn.set(transactionIdColumn);
        }

        public long getMaterialColumn() {
            return materialColumn.get();
        }

        public LongProperty materialColumnProperty() {
            return materialColumn;
        }

        public void setMaterialColumn(long materialColumn) {
            this.materialColumn.set(materialColumn);
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

        public long getTankColumn() {
            return tankColumn.get();
        }

        public LongProperty tankColumnProperty() {
            return tankColumn;
        }

        public void setTankColumn(long tankColumn) {
            this.tankColumn.set(tankColumn);
        }

        public long getDriverColumn() {
            return driverColumn.get();
        }

        public LongProperty driverColumnProperty() {
            return driverColumn;
        }

        public void setDriverColumn(long driverColumn) {
            this.driverColumn.set(driverColumn);
        }

        public long getTruckTrailerColumn() {
            return truckTrailerColumn.get();
        }

        public LongProperty truckTrailerColumnProperty() {
            return truckTrailerColumn;
        }

        public void setTruckTrailerColumn(long truckTrailerColumn) {
            this.truckTrailerColumn.set(truckTrailerColumn);
        }

        public long getTruckContainerColumn() {
            return truckContainerColumn.get();
        }

        public LongProperty truckContainerColumnProperty() {
            return truckContainerColumn;
        }

        public void setTruckContainerColumn(long truckContainerColumn) {
            this.truckContainerColumn.set(truckContainerColumn);
        }

        public OperationType getOperationTypeColumn() {
            return operationTypeColumn.get();
        }

        public ObjectProperty<OperationType> operationTypeColumnProperty() {
            return operationTypeColumn;
        }

        public void setOperationTypeColumn(OperationType operationTypeColumn) {
            this.operationTypeColumn.set(operationTypeColumn);
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

        public LocalDateTime getDateTimeColumn() {
            return dateTimeColumn.get();
        }

        public ObjectProperty<LocalDateTime> dateTimeColumnProperty() {
            return dateTimeColumn;
        }

        public void setDateTimeColumn(LocalDateTime dateTimeColumn) {
            this.dateTimeColumn.set(dateTimeColumn);
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

        public static TableObject createFromTransactionDTO(TransactionDTO transactionDTO) {
            return new TransactionsModel.TableObject(
                    new SimpleLongProperty(transactionDTO.getId()),
                    new SimpleLongProperty(transactionDTO.getMaterial()),
                    new SimpleLongProperty(transactionDTO.getStation()),
                    new SimpleLongProperty(transactionDTO.getTank()),
                    new SimpleLongProperty(transactionDTO.getDriver()),
                    new SimpleLongProperty(transactionDTO.getTruckTrailer()),
                    new SimpleLongProperty(transactionDTO.getTruckContainer()),
                    new SimpleObjectProperty<>(transactionDTO.getOperationType()),
                    new SimpleDoubleProperty(transactionDTO.getQty()),
                    new SimpleObjectProperty<>(transactionDTO.getDateTime()),
                    new SimpleObjectProperty<>(transactionDTO.getCreationDate()),
                    new SimpleObjectProperty<>(transactionDTO.getModifyDate()),
                    new SimpleStringProperty(transactionDTO.getCreatedBy()),
                    new SimpleStringProperty(transactionDTO.getOnTerminal()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(transactionIdColumn.getValue(), that.transactionIdColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(transactionIdColumn.getValue());
        }
    }
}
