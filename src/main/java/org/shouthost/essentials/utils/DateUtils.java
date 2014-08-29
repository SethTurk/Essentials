package org.shouthost.essentials.utils;

import scala.actors.threadpool.Arrays;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

    public static String toTime(long milli) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(milli);
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date.getTime());
    }

    public static Long timeToLong(String arg_value) {

        Long dateFrom = 0L;

        final Pattern p = Pattern.compile("([0-9]+)(s|h|m|d|w)");
        final Calendar cal = Calendar.getInstance();

        final List<String> matches = preg_match_all(p, arg_value);
        if (!matches.isEmpty()) {
            for (final String match : matches) {

                final Matcher m = p.matcher(match);
                if (m.matches()) {

                    if (m.groupCount() == 2) {

                        final int tfValue = Integer.parseInt(m.group(1));
                        final String tfFormat = m.group(2);

                        switch (tfFormat) {
                            case "w":
                                cal.add(Calendar.WEEK_OF_YEAR, -1 * tfValue);
                                break;
                            case "d":
                                cal.add(Calendar.DAY_OF_MONTH, -1 * tfValue);
                                break;
                            case "h":
                                cal.add(Calendar.HOUR, -1 * tfValue);
                                break;
                            case "m":
                                cal.add(Calendar.MINUTE, -1 * tfValue);
                                break;
                            case "s":
                                cal.add(Calendar.SECOND, -1 * tfValue);
                                break;
                            default:
                                return null;
                        }
                    }
                }
            }
            dateFrom = cal.getTime().getTime();
        }

        return dateFrom;

    }

    private static List preg_match_all(Pattern p, String subject) {
        Matcher m = p.matcher(subject);
        StringBuilder out = new StringBuilder();
        boolean split = false;
        while (m.find()) {
            out.append(m.group());
            out.append("~");
            split = true;
        }
        return Arrays.asList((split) ? out.toString().split("~") : new String[0]);
    }
}
