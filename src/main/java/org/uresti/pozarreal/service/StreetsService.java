package org.uresti.pozarreal.service;

import java.util.List;

import org.uresti.pozarreal.dto.StreetInfo;
import org.uresti.pozarreal.model.Street;

public interface StreetsService {
    List<Street> getStreets();

    StreetInfo getStreetInfo(String streetId);
}
