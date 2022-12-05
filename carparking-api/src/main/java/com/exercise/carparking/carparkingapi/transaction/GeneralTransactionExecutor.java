package com.exercise.carparking.carparkingapi.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GeneralTransactionExecutor implements TransactionExecutor {

    @Override
    public void execute(Executor executor) {
        executor.execute();
    }

    //Rollback and retry should be here
}
