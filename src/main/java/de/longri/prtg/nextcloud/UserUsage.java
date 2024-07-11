/*
 * Copyright (C) 2024 Longri
 *
 * This file is part of prtg-nextcloud.
 *
 * prtg-nextcloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * prtg-nextcloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with prtg-nextcloud. If not, see <https://www.gnu.org/licenses/>.
 */
package de.longri.prtg.nextcloud;

/*

see https://github.com/nextcloud/user_usage_report

Leaving out the user argument will generate a report for all users on the system:

$ sudo -u www-data ./occ usage-report:generate --display-name
"admin","Nextcloud Admin","2017-09-18T09:00:01+00:00",5368709120,786432000,12,1,1,2
"test1","Test User 1","2017-09-18T09:00:01+00:00",-2,954368,6,0,2,10
"test2","Second Test user","2017-09-18T09:00:01+00:00",-2,164,4,0,0,0
"test3","Test User Three","2017-09-18T09:00:01+00:00",-2,164,4,0,0,0
"test5","Fifth Tester","2017-09-18T09:00:01+00:00",-2,164,4,0,0,0
The CSV data is the following:

[0] User identifier
[1] User display name (when --display-name is given)
[2] Current date and time (default in ISO 8601 format, but any format can be specified)
[3] Assigned home storage size in bytes (-3 is unlimited, -2 is unknown/not set)
[4] Disk space consumed by home storage in bytes (-2 is unknown)
[5] Number of files in home storage
[6] Number of shares created
[7] Number of files created (new files only)
[8] Number of files read (download/view)



 */
import de.longri.prtg.xml.tags.HDD_Channel;


public class UserUsage extends HDD_Channel  {

    private static String[] getValues(String occValues) {
        String[] values = occValues.split(",");
        return values;
    }

    public UserUsage(String occValue, StringBuilder stringBuilder) {
        super(getValues(occValue)[1].replace("\"", "").trim());

        String[] values = getValues(occValue);

        long bytesAssigned;

        if (values[3].contains("none")) {
            bytesAssigned = 0;
        } else {
            bytesAssigned = Long.parseLong(values[3].replace("\"", "").trim());
        }


        if (bytesAssigned > 0) {
            long usageBytes = Long.parseLong(values[4].replace("\"", "").trim());
            setValue(((double) usageBytes / (double) bytesAssigned) * 100.0f);
        } else {
            setValue((float) 0);
        }

    }

}
