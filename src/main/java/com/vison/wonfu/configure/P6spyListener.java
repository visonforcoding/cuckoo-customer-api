package com.vison.wonfu.configure;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.vison.wonfu.App;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class P6spyListener extends JdbcEventListener {


    @Override
    public void onAfterExecuteQuery(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
//        System.out.println(statementInformation.getSqlWithValues());
        App.count.set(App.count.get() + 1);
        log.info("execute query..." + statementInformation.getSqlWithValues());
    }

    @Override
    public void onAfterExecuteUpdate(PreparedStatementInformation statementInformation, long timeElapsedNanos, int rowCount, SQLException e) {
        App.count.set(App.count.get() + 1);
        log.info("execute update.." + statementInformation.getSqlWithValues());
    }

    @Override
    public void onAfterExecute(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
        App.count.set(App.count.get() + 1);
        log.info("execute.." + statementInformation.getSqlWithValues());
    }

}
