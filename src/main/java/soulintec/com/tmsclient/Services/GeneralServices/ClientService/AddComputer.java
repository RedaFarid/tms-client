package soulintec.com.tmsclient.Services.GeneralServices.ClientService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.Entities.ComputerDTO;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Services.ComputersService;
import soulintec.com.tmsclient.Services.GeneralServices.LoggingService.LoginService;
import soulintec.com.tmsclient.Services.LogsService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class AddComputer {
    @Autowired
    private ComputersService computersService;
    @Autowired
    private LogsService logsService;
    @Autowired
    private LoginService loginService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            List<ComputerDTO> all = computersService.findAll();
            if (all != null) {
                if (all.stream().filter(item -> item.getName().equals(hostName)).collect(Collectors.toList()).size() == 0) {
                    computersService.save(new ComputerDTO(hostName));
                    logsService.save(new LogDTO(LogIdentifier.System, "Adding computer name", "Computer : [" + hostName + " ] is added"));
                }
            } else {
                computersService.save(new ComputerDTO(hostName));
                logsService.save(new LogDTO(LogIdentifier.System, "Adding computer name", "Computer : [" + hostName + " ] is added"));
            }

        } catch (UnknownHostException e) {
            logsService.save(new LogDTO(LogIdentifier.Error, "Adding computer name", e.getMessage()));
            log.error(e);
        }
    }

}
