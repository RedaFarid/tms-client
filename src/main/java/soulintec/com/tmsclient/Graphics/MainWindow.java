package soulintec.com.tmsclient.Graphics;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;

@Component
public class MainWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private Stage stage;

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener applicationListener) {
        stage = applicationListener.getStage();

        stage.show();
    }
}
