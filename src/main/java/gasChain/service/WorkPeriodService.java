package gasChain.service;

import gasChain.entity.WorkPeriod;
import gasChain.repository.WorkPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkPeriodService extends GenericService<WorkPeriod, Long, WorkPeriodRepository> {

    @Autowired
    public WorkPeriodService(WorkPeriodRepository r) {
        super(r);
    }
}
