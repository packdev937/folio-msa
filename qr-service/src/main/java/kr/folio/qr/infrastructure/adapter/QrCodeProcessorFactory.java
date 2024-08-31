package kr.folio.qr.infrastructure.adapter;

import java.util.EnumMap;
import java.util.Map;
import kr.folio.qr.domain.core.entity.BrandType;
import kr.folio.qr.domain.core.strategy.QrCodeProcessor;
import org.springframework.stereotype.Component;

@Component
public class QrCodeProcessorFactory {

    private final Map<BrandType, QrCodeProcessor> qrCodeProcessorMap;

    public QrCodeProcessorFactory(LifeFourCutsQrCodeProcessor lifeFourCutsQrCodeProcessor,
        PhotoismQrCodeProcessor photoismQrCodeProcessor,
        HaruFilmQrCodeProcessor haruFilmQrCodeProcessor,
        DontLookUpQrCodeProcessor dontLookUpQrCodeProcessor,
        MyFourCutQrCodeProcessor myFourCutQrCodeProcessor,
        PhotoGrayQrCodeProcessor photoGrayQrCodeProcessor,
        MonoMansionQrCodeProcessor monoMansionQrCodeProcessor,
        PhotoSignatureQrCodeProcessor photoSignatureQrCodeProcessor,
        PicDotQrCodeProcessor picDotQrCodeProcessor) {
        qrCodeProcessorMap = new EnumMap<>(BrandType.class);
        qrCodeProcessorMap.put(BrandType.LIFE_FOUR_CUTS, lifeFourCutsQrCodeProcessor);
        qrCodeProcessorMap.put(BrandType.PHOTOISM, photoismQrCodeProcessor);
        qrCodeProcessorMap.put(BrandType.HARU_FILM, haruFilmQrCodeProcessor);
        qrCodeProcessorMap.put(BrandType.DONT_LOOK_UP, dontLookUpQrCodeProcessor);
        qrCodeProcessorMap.put(BrandType.MY_FOUR_CUT, myFourCutQrCodeProcessor);
        qrCodeProcessorMap.put(BrandType.PHOTOGRAY, photoGrayQrCodeProcessor);
        qrCodeProcessorMap.put(BrandType.MONOMANSION, monoMansionQrCodeProcessor);
        qrCodeProcessorMap.put(BrandType.PHOTO_SIGNATURE, photoSignatureQrCodeProcessor);
        qrCodeProcessorMap.put(BrandType.PICDOT, picDotQrCodeProcessor);
    }

    public QrCodeProcessor getQrCodeProcessor(BrandType brandType) {
        return qrCodeProcessorMap.get(brandType);
    }
}
