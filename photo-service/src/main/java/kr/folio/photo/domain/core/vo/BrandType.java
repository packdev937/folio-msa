package kr.folio.photo.domain.core.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BrandType {
    LIFE_FOUR_CUTS("https://api.life4cut.net/"),
    PHOTOISM("https://qr.seobuk.kr/"),
    HARU_FILM("http://haru6.mx2.co.kr/"),
    DONT_LOOK_UP("https://x.dontlxxkup.kr/"),
    ;

    private final String urlFormat;

    public boolean matches(String qrUrl) {
        return qrUrl.startsWith(this.urlFormat);
    }

    public static BrandType matchBrandType(String qrUrl) {
        for (BrandType brandType : BrandType.values()) {
            if (brandType.matches(qrUrl)) {
	return brandType;
            }
        }
        return null;
    }
}
