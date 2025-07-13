package org.muhabdulloh.cache.usecase.indonesian_district.client;

import org.muhabdulloh.cache.usecase.indonesian_district.dto.IndonesianRegency;

public interface RegencyClient {
    IndonesianRegency getIndonesianRegency(String code);
}
