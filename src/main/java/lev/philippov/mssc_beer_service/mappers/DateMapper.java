package lev.philippov.mssc_beer_service.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {

    Timestamp OffsetDateTimeToTimestamp(OffsetDateTime dt) {
        LocalDateTime ldt = dt.toLocalDateTime();
        return new Timestamp(ldt.getYear(),ldt.getMonth().getValue(),ldt.getDayOfMonth(),ldt.getHour(),ldt.getMinute(),ldt.getSecond(),ldt.getNano());
    }

    OffsetDateTime timestampToOffsetDateTime(Timestamp ts) {
        return OffsetDateTime.of(ts.getYear(),ts.getMonth(),ts.getDay(),ts.getHours(),ts.getMinutes(),ts.getSeconds(),ts.getNanos(), ZoneOffset.UTC);
    }

}
