package org.muhabdulloh.cache.usecase.indonesian_district.service;

import lombok.extern.slf4j.Slf4j;
import org.muhabdulloh.cache.data_type.LRUCache;
import org.muhabdulloh.cache.exception.ValidationException;
import org.muhabdulloh.cache.usecase.indonesian_district.dto.IndonesianRegency;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
public class IndonesianAdministrationService implements IndonesianRegencyService {

    private final static String REGENCY_CODE_PATTERN = "^\\d{2}\\.\\d{2}\\.\\d{2}\\.\\d{4}$";
    private final static Pattern REGENCY_PATTERN = Pattern.compile(REGENCY_CODE_PATTERN);
    LRUCache<String, IndonesianRegency> cache = new LRUCache<>(20);

    @Override
    public String getNameFromCode(String code) {
        if (isValidCode(code)) {
            var fromCache = cache.get(code);
            if (fromCache.isPresent()) {
                return fromCache.get().name();
            } else {
                var regency = getIndonesianRegency(code);
                cache.put(code, regency);
                return regency.name();
            }
        } else {
            throw new ValidationException("Invalid code");
        }
    }

    @Override
    public String getCodeFromName(String name) {
        return "";
    }

    private boolean isValidCode(String code) {
        return REGENCY_PATTERN.matcher(code).matches();
    }

    private IndonesianRegency getIndonesianRegency(String code) {
        return new IndonesianRegency(code, "name");
    }
}
