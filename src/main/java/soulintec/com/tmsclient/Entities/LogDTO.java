package soulintec.com.tmsclient.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogDTO  {

    private Long logId;
    private LogIdentifier logIdentifier ;
    private String source;
    private String event;
    private String userName;
    private LocalDateTime dateTime;

    private Boolean ack = false;
    private String onTerminal;

    public LogDTO(LogIdentifier logIdentifier, String source, String event) {
        this.logIdentifier = logIdentifier;
        this.source = source;
        this.event = event;
    }
}
