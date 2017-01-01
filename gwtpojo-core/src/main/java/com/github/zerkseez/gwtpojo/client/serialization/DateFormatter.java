package com.github.zerkseez.gwtpojo.client.serialization;

import java.util.Date;

public interface DateFormatter {
    Date parse(String dateString);
    String format(Date date);
}
