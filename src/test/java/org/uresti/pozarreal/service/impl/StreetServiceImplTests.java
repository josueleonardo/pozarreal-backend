package org.uresti.pozarreal.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.uresti.pozarreal.dto.StreetInfo;
import org.uresti.pozarreal.model.House;
import org.uresti.pozarreal.model.Representative;
import org.uresti.pozarreal.model.Street;
import org.uresti.pozarreal.repository.HousesRepository;
import org.uresti.pozarreal.repository.RepresentativeRepository;
import org.uresti.pozarreal.repository.StreetRepository;

import java.util.*;

public class StreetServiceImplTests {

    @Test
    public void givenAnEmptyStreetList_whenGetStreets_thenEmptyListIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Mockito.when(streetRepository.findAll()).thenReturn(lista);

        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertTrue(streets.isEmpty());
    }

    @Test
    public void givenAnStreetListWithTwoElements_whenGetStreets_thenListWithTwoElementsIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Street street1 = new Street();
        street1.setId("id1");
        street1.setName("Street 1");
        lista.add(street1);

        Street street2 = new Street();
        street2.setId("id2");
        street2.setName("Street 2");
        lista.add(street2);

        Mockito.when(streetRepository.findAll()).thenReturn(lista);


        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertEquals(2, streets.size());
        Assertions.assertEquals("id1", streets.get(0).getId());
        Assertions.assertEquals("Street 1", streets.get(0).getName());
        Assertions.assertEquals("id2", streets.get(1).getId());
        Assertions.assertEquals("Street 2", streets.get(1).getName());
    }

    @Test
    public void givenValidStreet_whenGetStreetInfo_streetInfoIsReturned(){
        // Given:
        String streetId = "1235123";
        Street street = buildTestStreet(streetId);
        Representative representative = buildTestRepresentative();
        List<House> housesList = buildTestHouses("UnaCalle");
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository= Mockito.mock(RepresentativeRepository.class);
        HousesRepository housesRepository= Mockito.mock(HousesRepository.class);
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        Mockito.when(streetRepository.findById(streetId)).thenReturn(Optional.of(street));

        Mockito.when(representativeRepository.findRepresentativeByStreet(streetId)).thenReturn(representative);

        Mockito.when(housesRepository.findAllByStreet(streetId)).thenReturn(housesList);


        // When:
        StreetInfo streetInfo = streetService.getStreetInfo(streetId);

        // Then:
        Assertions.assertEquals(streetId, streetInfo.getId());
        Assertions.assertEquals("UnaCalle", streetInfo.getName());
        Assertions.assertEquals("2342523", streetInfo.getRepresentative().getId());
        Assertions.assertEquals("UnaCalle", streetInfo.getRepresentative().getStreet());
        Assertions.assertEquals("Un Nombre", streetInfo.getRepresentative().getName());
        Assertions.assertEquals("Un address", streetInfo.getRepresentative().getAddress());
        Assertions.assertEquals("1231231231231", streetInfo.getRepresentative().getPhone());
        Assertions.assertEquals(2, streetInfo.getHouses().size());

    }

    @Test
    public void givenNoValidStreet_whenGetStreetInfo_ExceptionIsThrown(){
        // Given:
        String streetId = "1235123";
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository= null;
        HousesRepository housesRepository= null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        Mockito.when(streetRepository.findById(streetId)).thenReturn(Optional.empty());

        // When:
        Assertions.assertThrows(NoSuchElementException.class, () -> streetService.getStreetInfo(streetId));
    }

    private List<House> buildTestHouses(String streetName) {
        List<House> housesList = new LinkedList<>();
        House house1 = new House();

        house1.setId(UUID.randomUUID().toString());
        house1.setStreet(streetName);
        house1.setChipsEnabled(true);
        house1.setNumber("100");

        housesList.add(house1);

        House house2 = new House();

        house2.setId(UUID.randomUUID().toString());
        house2.setStreet(streetName);
        house2.setChipsEnabled(false);
        house2.setNumber("120");

        housesList.add(house2);

        return housesList;
    }

    private Representative buildTestRepresentative() {
        Representative representative = new Representative();

        representative.setId("2342523");
        representative.setName("Un Nombre");
        representative.setStreet("UnaCalle");
        representative.setAddress("Un address");
        representative.setPhone("1231231231231");
        return representative;
    }

    private Street buildTestStreet(String streetId) {
        Street street = new Street();

        street.setName("UnaCalle");
        street.setId(streetId);

        return street;
    }

}
