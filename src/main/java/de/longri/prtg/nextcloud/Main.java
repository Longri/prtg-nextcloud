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

import de.longri.prtg.xml.Sensor;
import de.longri.prtg.xml.tags.HDD_Channel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

//        debugWriteToFile(args, "/var/www/nextcloud/apps/prtgnextcloud/debug.log");

        run(args);
        System.exit(0);
    }

    static void run(String[] args) {

        StringBuilder msgBilder = new StringBuilder();

        Sensor SENSOR = new Sensor();
        if (args.length == 0) {
            SENSOR.setError("No sensor specified");
        } else {
            String ossUsageString = args[0];
            String[] users = ossUsageString.split("\n");
            for (String user : users) {

                user = user.trim();

                if (user.isEmpty()) continue;

                if (!user.startsWith("\"")) {
                    // search begin and cat
                    int pos = user.indexOf("\"");

                    //if not found, skip line
                    if (pos < 0) continue;

                    user = user.substring(pos);
                }

                new HDD_Channel(user);

                SENSOR.addChannel(new UserUsage(user, msgBilder));
            }
            SENSOR.setMessage("Message for Sensor");
        }

        if (args.length >= 2) {
            int idx = 0;
            for (String arg : args) {
                if (idx++ == 0) continue;
                arg = arg.trim();
                if (arg.isEmpty()) continue;
                if (arg.startsWith("-d=")) {
                    arg = arg.replace("-d=", "").trim();

                    int pos = arg.indexOf("[");
                    int end = arg.lastIndexOf("]");

                    String name = arg.substring(0, pos);
                    String path = arg.substring(pos + 1, end).replace(":", "/");


                    File f = new File(path);
                    if (f.exists()) {
                        long total = f.getTotalSpace();
                        long used = total - f.getFreeSpace();

                        HDD_Channel hddChannel = new HDD_Channel(name);

                        float percent = ((float) used / (float) total) * 100.0f;
                        hddChannel.setValue(percent, 100);

                        SENSOR.addChannel(hddChannel);
                    }
                }
            }

        }

        String strXml = SENSOR.getXML();
        System.out.println(strXml);
    }


    public static void debugWriteToFile(String[] data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int idx = 0;
            for (String line : data) {
                writer.write("[index " + idx++);
                writer.newLine();

                writer.write(line);
                writer.newLine();
                writer.write("]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
