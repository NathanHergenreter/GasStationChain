package gasChain.coreInterfaces.managers;

import java.util.List;

public interface ICashierHelper extends IUserHelper{

    void addWorkPeriod(List<String> args);
    void processSale();

    void processReturn(List<String> args);
}
