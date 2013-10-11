package com.lolRiver.river.util.sql;

import com.google.common.base.CaseFormat;
import com.lolRiver.river.models.Game;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * @author mxia (mxia@lolRiver.com)
 *         10/1/13
 */

public class SqlQueryUtil {
    /**
     * @param paramsToMatch param name for key, conditional operator string for value (<, >, =)
     * @return sql conditional string you can insert into any sql clause ending with "WHERE". The caller must use namedJdbcTemplate.
     */
    public static String conditionalQuery(final Map<String, String> paramsToMatch) {
        final StringBuilder sb = new StringBuilder();
        if (paramsToMatch != null) {
            boolean isFirstParam = true;
            for (final String paramName : paramsToMatch.keySet()) {
                if (isFirstParam) {
                    isFirstParam = false;
                } else {
                    sb.append(" AND ");
                }
                String sqlParamName = paramName;
                // hack because my games don't have end time yet, so i can't
                // use game end time < video end time to create clips
                if (paramName.equals(Game.END_TIME_STRING)) {
                    sqlParamName = Game.START_TIME_STRING;
                }
                sb.append(String.format("%s %s :%s", sqlParamName, paramsToMatch.get(paramName),
                                       CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, paramName)));
            }
        }
        return sb.toString();
    }

    public static String inClause(List<String> list) {
        String[] arr = list.toArray(new String[list.size()]);
        String s = Arrays.toString(arr);
        s = s.replace('[', '(');
        s = s.replace(']', ')');
        return s;
    }
}
