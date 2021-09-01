package soulintec.com.tmsclient.Graphics.Windows.TanksWindow;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;

@Log4j2
@Controller

public class TanksController {

    private MainTanksWindow mainTanksWindow;

    @Autowired
    private MainWindow mainView;

    public void setMainTanksWindow(MainTanksWindow mainTanksWindow) {
        this.mainTanksWindow = mainTanksWindow;
    }


    private enum InHeader {
        Tank,
        Level,
        Volume,
    }
}
