package kr.folio.photo.domain.core.vo;

import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgeGroup {
    CHILD(13),
    TEEN(17),
    YOUNG_ADULT(21),
    ADULT(27);

    public static AgeGroup calculate(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age <= 13) {
            return CHILD;
        } else if (age <= 18) {
            return TEEN;
        } else if (age <= 24) {
            return YOUNG_ADULT;
        } else {
            return ADULT;
        }
    }

    private final int age;
}
