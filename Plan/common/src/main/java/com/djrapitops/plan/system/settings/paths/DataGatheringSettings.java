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
package com.djrapitops.plan.system.settings.paths;

import com.djrapitops.plan.system.settings.paths.key.BooleanSetting;
import com.djrapitops.plan.system.settings.paths.key.Setting;

/**
 * {@link Setting} values that are in "Data_gathering" section.
 *
 * @author Rsl1122
 */
public class DataGatheringSettings {

    public static final Setting<Boolean> GEOLOCATIONS = new BooleanSetting("Data_gathering.Geolocations");
    public static final Setting<Boolean> LOG_UNKNOWN_COMMANDS = new BooleanSetting("Data_gathering.Commands.Log_unknown");
    public static final Setting<Boolean> COMBINE_COMMAND_ALIASES = new BooleanSetting("Data_gathering.Commands.Log_aliases_as_main_command");

    private DataGatheringSettings() {
        /* static variable class */
    }

}