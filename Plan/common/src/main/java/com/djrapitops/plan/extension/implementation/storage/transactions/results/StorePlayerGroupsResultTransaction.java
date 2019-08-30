/*
 *  This file is part of Player Analytics (Plan).
 *
 *  Plan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License v3 as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Plan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Plan. If not, see <https://www.gnu.org/licenses/>.
 */
package com.djrapitops.plan.extension.implementation.storage.transactions.results;

import com.djrapitops.plan.system.storage.database.sql.tables.ExtensionGroupsTable;
import com.djrapitops.plan.system.storage.database.sql.tables.ExtensionProviderTable;
import com.djrapitops.plan.system.storage.database.transactions.ExecStatement;
import com.djrapitops.plan.system.storage.database.transactions.Executable;
import com.djrapitops.plan.system.storage.database.transactions.Transaction;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static com.djrapitops.plan.system.storage.database.sql.parsing.Sql.AND;
import static com.djrapitops.plan.system.storage.database.sql.parsing.Sql.WHERE;

/**
 * Transaction to store method result of a {@link com.djrapitops.plan.extension.implementation.providers.GroupDataProvider}.
 *
 * @author Rsl1122
 */
public class StorePlayerGroupsResultTransaction extends Transaction {

    private final String pluginName;
    private final UUID serverUUID;
    private final String providerName;
    private final UUID playerUUID;

    private final String[] value;

    public StorePlayerGroupsResultTransaction(String pluginName, UUID serverUUID, String providerName, UUID playerUUID, String[] value) {
        this.pluginName = pluginName;
        this.serverUUID = serverUUID;
        this.providerName = providerName;
        this.playerUUID = playerUUID;
        this.value = value;
    }

    @Override
    protected void performOperations() {
        execute(deleteOldValues());
        for (String group : value) {
            String groupName = StringUtils.truncate(group, 50);
            execute(insertGroup(groupName));
        }
    }

    private Executable deleteOldValues() {
        String sql = "DELETE FROM " + ExtensionGroupsTable.TABLE_NAME +
                WHERE + ExtensionGroupsTable.USER_UUID + "=?" +
                AND + ExtensionGroupsTable.PROVIDER_ID + "=" + ExtensionProviderTable.STATEMENT_SELECT_PROVIDER_ID;

        return new ExecStatement(sql) {
            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setString(1, playerUUID.toString());
                ExtensionProviderTable.set3PluginValuesToStatement(statement, 2, providerName, pluginName, serverUUID);
            }
        };
    }

    private Executable insertGroup(String group) {
        String sql = "INSERT INTO " + ExtensionGroupsTable.TABLE_NAME + "(" +
                ExtensionGroupsTable.GROUP_NAME + "," +
                ExtensionGroupsTable.USER_UUID + "," +
                ExtensionGroupsTable.PROVIDER_ID +
                ") VALUES (?,?," + ExtensionProviderTable.STATEMENT_SELECT_PROVIDER_ID + ")";
        return new ExecStatement(sql) {
            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setString(1, group);
                statement.setString(2, playerUUID.toString());
                ExtensionProviderTable.set3PluginValuesToStatement(statement, 3, providerName, pluginName, serverUUID);
            }
        };
    }
}