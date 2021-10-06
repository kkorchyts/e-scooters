package com.kkorchyts.servicetests;

import com.kkorchyts.dao.repositories.vehicle.VehicleDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.entities.VehicleModelCount;
import com.kkorchyts.dto.converters.VehicleDtoConverter;
import com.kkorchyts.dto.dtos.VehicleDto;
import com.kkorchyts.dto.exceptions.DtoException;
import com.kkorchyts.service.services.vehicle.VehicleService;
import com.kkorchyts.service.services.vehicle.impl.VehicleServiceImpl;
import com.kkorchyts.service.validators.VehicleValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VehicleServiceTest {

    @Mock
    private VehicleDao vehicleDao;

    @Mock
    private VehicleDtoConverter vehicleDtoConverter;

    @Mock
    private VehicleValidator vehicleValidator;

    @InjectMocks
    private VehicleService vehicleService = new VehicleServiceImpl();

    private MockitoSession session;

    @Before
    public void beforeMethod() {
        session = Mockito.mockitoSession()
                .initMocks(this)
                .strictness(Strictness.LENIENT)
                .startMocking();
    }

    @After
    public void afterMethod() {
        session.finishMocking();
    }

    @Test
    public void getAllVehiclesValidTest() {
        Pageable pageable = mock(Pageable.class);
        SearchCriteria<Vehicle> searchCriteria = mock(SearchCriteria.class);
        Page<Vehicle> vehicleList = mock(Page.class);
        Page<VehicleDto> vehicleDtos = mock(Page.class);
        when(vehicleDao.getAll(pageable, searchCriteria)).thenReturn(vehicleList);
        when(vehicleDtoConverter.createFromEntitiesPage(vehicleList)).thenReturn(vehicleDtos);
        assertEquals(vehicleDtos, vehicleService.getAllVehicles(pageable, searchCriteria));
    }

    @Test
    public void getAvailableVehiclesValidTest() {
        Pageable pageable = mock(Pageable.class);
        SearchCriteria<Vehicle> searchCriteria = mock(SearchCriteria.class);
        Page<Vehicle> vehicleList = mock(Page.class);
        Page<VehicleDto> vehicleDtos = mock(Page.class);
        when(vehicleDao.getAvailableVehicles(pageable, searchCriteria)).thenReturn(vehicleList);
        when(vehicleDtoConverter.createFromEntitiesPage(vehicleList)).thenReturn(vehicleDtos);
        assertEquals(vehicleDtos, vehicleService.getAvailableVehicles(pageable, searchCriteria));
    }

    @Test
    public void getVehiclesCountGroupedByModelValidTest() {
        Boolean onlyAvailableVehicles = true;
        SearchCriteria<Vehicle> searchCriteria = mock(SearchCriteria.class);
        List<VehicleModelCount> vehicleModelCounts = mock(List.class);
        when(vehicleDao.getVehiclesCountGroupedByModel(searchCriteria, onlyAvailableVehicles)).thenReturn(vehicleModelCounts);
        assertEquals(vehicleModelCounts, vehicleService.getVehiclesCountGroupedByModel(searchCriteria, onlyAvailableVehicles));
        verify(vehicleDao, times(1)).getVehiclesCountGroupedByModel(searchCriteria, onlyAvailableVehicles);
    }

    @Test
    public void moveVehiclesFromOfficeToOfficeValidTest() {
        Integer fromOffice = 1;
        Integer toOffice = 2;
        Integer countMoved = 4;
        when(vehicleDao.moveVehiclesFromOfficeToOffice(fromOffice, toOffice)).thenReturn(countMoved);
        vehicleService.moveVehiclesFromOfficeToOffice(fromOffice, toOffice);
        verify(vehicleDao, times(1)).moveVehiclesFromOfficeToOffice(fromOffice, toOffice);
    }

    @Test
    public void createVehicleNotValidDtoThrowExceptionCheck() {
        VehicleDto vehicleDto = mock(VehicleDto.class);
        doThrow(DtoException.class).when(vehicleValidator).validateNewVehicleData(null);
        //doNothing().when(vehicleValidator).validateNewVehicleData(any());
        assertThrows(DtoException.class, () -> vehicleService.create(null));
    }

/*    @Test
    public void createVehicleFromNullInValidTest(){
        VehicleDto vehicleDto = mock(VehicleDto.class);
        doNothing().when(vehicleValidator).validateVehicleAvailability(any());

        assertThrows(IncorrectDataException.class, () -> vehicleService.create(vehicleDto));
    }*/

}
