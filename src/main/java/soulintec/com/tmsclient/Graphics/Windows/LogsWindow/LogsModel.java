package soulintec.com.tmsclient.Graphics.Windows.LogsWindow;

import com.google.common.base.Objects;
import javafx.beans.property.*;
import soulintec.com.tmsclient.Entities.LogDTO;

import java.time.LocalDateTime;

public class LogsModel {
    private final LongProperty logId = new SimpleLongProperty();
    private final ObjectProperty<LogIdentifier> logIdentifier = new SimpleObjectProperty<>();
    private final StringProperty source = new SimpleStringProperty("");
    private final StringProperty event = new SimpleStringProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty dateTime = new SimpleStringProperty();
    private final StringProperty onTerminal = new SimpleStringProperty();
    private final BooleanProperty ack = new SimpleBooleanProperty();

    public long getLogId() {
        return logId.get();
    }

    public LongProperty logIdProperty() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId.set(logId);
    }

    public LogIdentifier getLogIdentifier() {
        return logIdentifier.get();
    }

    public ObjectProperty<LogIdentifier> logIdentifierProperty() {
        return logIdentifier;
    }

    public void setLogIdentifier(LogIdentifier logIdentifier) {
        this.logIdentifier.set(logIdentifier);
    }

    public String getSource() {
        return source.get();
    }

    public StringProperty sourceProperty() {
        return source;
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public String getEvent() {
        return event.get();
    }

    public StringProperty eventProperty() {
        return event;
    }

    public void setEvent(String event) {
        this.event.set(event);
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

    public String getDateTime() {
        return dateTime.get();
    }

    public StringProperty dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime.set(dateTime);
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

    public boolean isAck() {
        return ack.get();
    }

    public BooleanProperty ackProperty() {
        return ack;
    }

    public void setAck(boolean ack) {
        this.ack.set(ack);
    }

    public static class TableObject {
        private final LongProperty logIdColumnColumn;
        private final ObjectProperty<LogIdentifier> logIdentifierColumn;
        private final StringProperty sourceColumn;
        private final StringProperty eventColumn;
        private final StringProperty userNameColumn;
        private final ObjectProperty<LocalDateTime> dateTimeColumn;
        private final StringProperty onTerminalColumn;
        private final BooleanProperty ackColumn;

        public TableObject(LongProperty logIdColumnColumn, ObjectProperty<LogIdentifier> logIdentifierColumn, StringProperty sourceColumn, StringProperty eventColumn, StringProperty userNameColumn, ObjectProperty<LocalDateTime> dateTimeColumn, StringProperty onTerminalColumn, BooleanProperty ackColumn) {
            this.logIdColumnColumn = logIdColumnColumn;
            this.logIdentifierColumn = logIdentifierColumn;
            this.sourceColumn = sourceColumn;
            this.eventColumn = eventColumn;
            this.userNameColumn = userNameColumn;
            this.dateTimeColumn = dateTimeColumn;
            this.onTerminalColumn = onTerminalColumn;
            this.ackColumn = ackColumn;
        }

        public long getLogIdColumnColumn() {
            return logIdColumnColumn.get();
        }

        public LongProperty logIdColumnColumnProperty() {
            return logIdColumnColumn;
        }

        public void setLogIdColumnColumn(long logIdColumnColumn) {
            this.logIdColumnColumn.set(logIdColumnColumn);
        }

        public LogIdentifier getLogIdentifierColumn() {
            return logIdentifierColumn.get();
        }

        public ObjectProperty<LogIdentifier> logIdentifierColumnProperty() {
            return logIdentifierColumn;
        }

        public void setLogIdentifierColumn(LogIdentifier logIdentifierColumn) {
            this.logIdentifierColumn.set(logIdentifierColumn);
        }

        public String getSourceColumn() {
            return sourceColumn.get();
        }

        public StringProperty sourceColumnProperty() {
            return sourceColumn;
        }

        public void setSourceColumn(String sourceColumn) {
            this.sourceColumn.set(sourceColumn);
        }

        public String getEventColumn() {
            return eventColumn.get();
        }

        public StringProperty eventColumnProperty() {
            return eventColumn;
        }

        public void setEventColumn(String eventColumn) {
            this.eventColumn.set(eventColumn);
        }

        public String getUserNameColumn() {
            return userNameColumn.get();
        }

        public StringProperty userNameColumnProperty() {
            return userNameColumn;
        }

        public void setUserNameColumn(String userNameColumn) {
            this.userNameColumn.set(userNameColumn);
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

        public String getOnTerminalColumn() {
            return onTerminalColumn.get();
        }

        public StringProperty onTerminalColumnProperty() {
            return onTerminalColumn;
        }

        public void setOnTerminalColumn(String onTerminalColumn) {
            this.onTerminalColumn.set(onTerminalColumn);
        }

        public boolean isAckColumn() {
            return ackColumn.get();
        }

        public BooleanProperty ackColumnProperty() {
            return ackColumn;
        }

        public void setAckColumn(boolean ackColumn) {
            this.ackColumn.set(ackColumn);
        }

        public static LogsModel.TableObject createFromLogDTO(LogDTO logDTO) {
            return new LogsModel.TableObject(
                    new SimpleLongProperty(logDTO.getLogId()),
                    new SimpleObjectProperty<>(logDTO.getLogIdentifier()),
                    new SimpleStringProperty(logDTO.getSource()),
                    new SimpleStringProperty(logDTO.getEvent()),
                    new SimpleStringProperty(logDTO.getUserName()),
                    new SimpleObjectProperty<>(logDTO.getDateTime()),
                    new SimpleStringProperty(logDTO.getOnTerminal()),
                    new SimpleBooleanProperty(logDTO.getAck()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableObject that = (TableObject) o;
            return Objects.equal(logIdColumnColumn.getValue(), that.logIdColumnColumn.getValue()) && Objects.equal(logIdentifierColumn.getValue(), that.logIdentifierColumn.getValue())
                    && Objects.equal(sourceColumn.getValue(), that.sourceColumn.getValue()) && Objects.equal(eventColumn.getValue(), that.eventColumn.getValue())
                    && Objects.equal(userNameColumn.getValue(), that.userNameColumn.getValue()) && Objects.equal(dateTimeColumn.getValue(), that.dateTimeColumn.getValue())
                    && Objects.equal(onTerminalColumn.getValue(), that.onTerminalColumn.getValue()) && Objects.equal(ackColumn.getValue(), that.ackColumn.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(logIdColumnColumn.getValue(), logIdentifierColumn.getValue(), sourceColumn.getValue(), eventColumn.getValue(),
                    userNameColumn.getValue(), dateTimeColumn.getValue(), onTerminalColumn.getValue(), ackColumn.getValue());
        }
    }
}
