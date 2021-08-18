package soulintec.com.tmsclient.Graphics;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Services.TanksService;

@Controller
@RequiredArgsConstructor
public class TanksController {

    private final TanksService tanksService;

    @Scheduled(fixedDelay = 5000)
    private void cyclic(){
        tanksService.getTanks();
    }
}
