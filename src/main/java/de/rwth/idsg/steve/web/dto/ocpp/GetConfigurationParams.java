/*
 * SteVe - SteckdosenVerwaltung - https://github.com/RWTH-i5-IDSG/steve
 * Copyright (C) 2013-2020 RWTH Aachen University - Information Systems - Intelligent Distributed Systems Group (IDSG).
 * All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.rwth.idsg.steve.web.dto.ocpp;

import com.google.common.base.Strings;
import de.rwth.idsg.steve.SteveException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 02.01.2015
 */
@Setter
@Getter
public class GetConfigurationParams extends MultipleChargePointSelect {

    private List<String> confKeyList;

    private String customConfKey;

    @NotNull(message = "Key type is required")
    private ConfigurationKeyType keyType = ConfigurationKeyType.PREDEFINED;

    @AssertTrue(message = "Custom Configuration Key cannot be left blank")
    public boolean isValidCustom() {
        if (keyType == ConfigurationKeyType.CUSTOM) {
            return !Strings.isNullOrEmpty(customConfKey);
        } else {
            return true;
        }
    }

    public List<String> getConfKeyList() {
        if (keyType == ConfigurationKeyType.PREDEFINED) {
            return confKeyList;
        } else if (keyType == ConfigurationKeyType.CUSTOM) {
            return Arrays.asList(customConfKey);
        }

        // This should not happen
        throw new SteveException("Cannot determine key (KeyType in illegal state)");
    }

    public boolean isSetConfKeyList() {
	if (keyType == ConfigurationKeyType.CUSTOM) {
            return true;
	}

        return confKeyList != null && !confKeyList.isEmpty();
    }

    // -------------------------------------------------------------------------
    // Enum
    // -------------------------------------------------------------------------

    @RequiredArgsConstructor
    private enum ConfigurationKeyType {
        PREDEFINED("Predefined"),
        CUSTOM("Custom");

        @Getter private final String value;

        public static ConfigurationKeyType fromValue(String v) {
            for (ConfigurationKeyType c : ConfigurationKeyType.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }
}
