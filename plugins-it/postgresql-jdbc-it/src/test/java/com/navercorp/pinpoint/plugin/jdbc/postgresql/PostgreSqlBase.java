/*
 * Copyright 2020 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.jdbc.postgresql;

import com.navercorp.pinpoint.pluginit.jdbc.DriverManagerUtils;
import com.navercorp.pinpoint.pluginit.jdbc.DriverProperties;
import com.navercorp.pinpoint.pluginit.jdbc.JDBCDriverClass;
import com.navercorp.pinpoint.test.plugin.shared.SharedTestBeforeAllResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author Woonduk Kang(emeroad)
 */
public abstract class PostgreSqlBase {
    protected abstract JDBCDriverClass getJDBCDriverClass();

    private final Logger logger = LogManager.getLogger(getClass());

    private static String JDBC_URL;
    private static String USERNAME;
    private static String PASSWORD;

    public static String getJdbcUrl() {
        return JDBC_URL;
    }

    public static String getUserName() {
        return USERNAME;
    }

    public static String getPassWord() {
        return PASSWORD;
    }

    @SharedTestBeforeAllResult
    public static void setBeforeAllResult(Properties beforeAllResult) {
        JDBC_URL = beforeAllResult.getProperty("JDBC_URL");
        USERNAME = beforeAllResult.getProperty("USERNAME");
        PASSWORD = beforeAllResult.getProperty("PASSWORD");
    }

    public static DriverProperties getDriverProperties() {
        DriverProperties driverProperties = new DriverProperties(getJdbcUrl(), getUserName(), getPassWord(), new Properties());
        return driverProperties;
    }


    @Before
    public void registerDriver() throws Exception {
        Driver driver = getJDBCDriverClass().getDriver().newInstance();
        DriverManager.registerDriver(driver);
    }

    @After
    public void tearDown() throws Exception {
        DriverManagerUtils.deregisterDriver();
    }
}
