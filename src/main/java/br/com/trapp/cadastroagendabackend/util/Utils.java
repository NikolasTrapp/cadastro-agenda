package br.com.trapp.cadastroagendabackend.util;

import lombok.experimental.UtilityClass;
import org.slf4j.helpers.MessageFormatter;

import static java.util.Objects.isNull;

@UtilityClass
public class Utils {

    public static String format(String msg, Object... args) {
        return MessageFormatter.arrayFormat(msg, args).getMessage();
    }

    public static String toStrSafe(Object object) {
        if (isNull(object)) {
            return null;
        }

        return object.toString();
    }
}
