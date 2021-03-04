package forthcafe.external;

import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    // fallback message
    @Override
    public void pay(Pay pay) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!! Message service is BUSY !!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!     Try again later     !!!!!!!!!!!!!!!!!!!!!");
    }

}
