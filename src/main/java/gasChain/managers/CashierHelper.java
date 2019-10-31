package gasChain.managers;

import gasChain.coreInterfaces.managers.ICashierHelper;
import gasChain.entity.Cashier;
import gasChain.entity.WorkPeriod;
import gasChain.service.WorkPeriodService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;


public class CashierHelper implements ICashierHelper {
    public CashierHelper(Cashier cashier) {
        _cashier = cashier;
    }

    Cashier _cashier;

    @Autowired
    WorkPeriodService _workPeriodService;

    /*
    expected use case is every employee doesn't add a WorkPeriod until after their shift
    NOTE: given hours should be on a 24 hour basis, also assumed shifts will not be greater than 24 hour period
    args: -startHour -endHour
     */
    @Override
    public void addWorkPeriod(List<String> args) {
        WorkPeriod workPeriod = new WorkPeriod(
                _cashier,
                Integer.parseInt(args.get(0)),
                Integer.parseInt(args.get(1)),
                _cashier.getWagesHourly(),
                new Date(System.currentTimeMillis())
        );
    }
}
