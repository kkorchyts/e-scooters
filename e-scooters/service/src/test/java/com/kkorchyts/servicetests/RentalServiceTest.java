package com.kkorchyts.servicetests;

import com.kkorchyts.dao.repositories.rental.RentalDao;
import com.kkorchyts.dao.repositories.rentaloffice.RentalOfficeDao;
import com.kkorchyts.dao.repositories.tariff.TariffDao;
import com.kkorchyts.dao.repositories.user.UserDao;
import com.kkorchyts.dao.repositories.vehicle.VehicleDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.domain.entities.Tariff;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.enums.RentalStatus;
import com.kkorchyts.dto.converters.RentalDtoConverter;
import com.kkorchyts.dto.dtos.RentalDto;
import com.kkorchyts.dto.dtos.RentalFinishDto;
import com.kkorchyts.dto.dtos.RentalStartDto;
import com.kkorchyts.dto.dtos.VehicleTechnicalConditionDto;
import com.kkorchyts.service.exception.IncorrectDataException;
import com.kkorchyts.service.services.discount.DiscountService;
import com.kkorchyts.service.services.rental.RentalService;
import com.kkorchyts.service.services.rental.impl.RentalServiceImpl;
import com.kkorchyts.service.services.tariff.TariffService;
import com.kkorchyts.service.validators.RentalValidator;
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

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RentalServiceTest {

    @Mock
    private VehicleDao vehicleDao;

    @Mock
    private RentalDao rentalDao;

    @Mock
    private TariffDao tariffDao;

    @Mock
    private UserDao userDao;

    @Mock
    private RentalOfficeDao rentalOfficeDao;

    @Mock
    private RentalDtoConverter rentalDtoConverter;

    @Mock
    private TariffService tariffService;

    @Mock
    private DiscountService discountService;

    @Mock
    private RentalValidator rentalValidator;

    @InjectMocks
    private final RentalService rentalService = new RentalServiceImpl();

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
    public void getAllValidTest() {
        Pageable pageable = mock(Pageable.class);
        SearchCriteria<Rental> searchCriteria = mock(SearchCriteria.class);
        Page<Rental> rentalList = mock(Page.class);
        Page<RentalDto> rentalDtos = mock(Page.class);
        when(rentalDao.getAll(pageable, searchCriteria)).thenReturn(rentalList);
        when(rentalDtoConverter.createFromEntitiesPage(rentalList)).thenReturn(rentalDtos);
        assertEquals(rentalDtos, rentalService.getAll(pageable, searchCriteria));
    }

    @Test
    public void getByIdThrowExceptionWhenNull() {
        when(rentalDao.findById(0)).thenReturn(null);
        doThrow(EntityNotFoundException.class).when(rentalValidator).validateRentalId(null);
        assertThrows(EntityNotFoundException.class, () -> rentalService.getById(null));
    }

    @Test
    public void getByIdValidTest() {
        Integer rentalId = 1;
        Rental rental = mock(Rental.class);
        RentalDto rentalDto = mock(RentalDto.class);
        when(rentalDao.findById(rentalId)).thenReturn(rental);
        doNothing().when(rentalValidator).validateRentalId(1);
        when(rentalDtoConverter.createDtoFromEntity(rental)).thenReturn(rentalDto);
        assertEquals(rentalDto, rentalService.getById(rentalId));
    }

    @Test
    public void startExecuteValidatorAndThrowExceptionsWhenRentalValidatorThrows() {
        RentalStartDto rentalStartDto = mock(RentalStartDto.class);
        doThrow(IncorrectDataException.class).when(rentalValidator).validateRentalStartDto(rentalStartDto);
        assertThrows(IncorrectDataException.class, () -> rentalService.start(rentalStartDto));
        verify(rentalValidator, times(1)).validateRentalStartDto(rentalStartDto);
    }

    @Test
    public void startValidTest() {
        RentalStartDto rentalStartDto = mock(RentalStartDto.class);
        doNothing().when(rentalValidator).validateRentalStartDto(rentalStartDto);
        when(vehicleDao.findById(anyInt())).thenReturn(mock(Vehicle.class));
        when(userDao.findById(anyInt())).thenReturn(mock(User.class));
        when(tariffDao.findById(anyInt())).thenReturn(mock(Tariff.class));
        RentalDto rentalMockDto = mock(RentalDto.class);
        Mockito.when(rentalDtoConverter.createDtoFromEntity(any())).thenReturn(rentalMockDto);
        RentalDto resultRentalDto = rentalService.start(rentalStartDto);
        assertEquals(rentalMockDto, resultRentalDto);
        verify(rentalValidator, times(1)).validateRentalStartDto(rentalStartDto);
        verify(vehicleDao, times(1)).findById(any());
        verify(userDao, times(1)).findById(any());
        verify(tariffDao, times(1)).findById(any());
    }


    @Test
    public void finishExecuteValidatorAndThrowEntityNotFoundExceptionWhenRentalValidatorThrows() {
        RentalFinishDto rentalFinishDto = mock(RentalFinishDto.class);
        doThrow(EntityNotFoundException.class).when(rentalValidator).validateRentalFinishDto(rentalFinishDto);
        assertThrows(EntityNotFoundException.class, () -> rentalService.finish(anyInt(), rentalFinishDto));
        verify(rentalValidator, times(1)).validateRentalFinishDto(rentalFinishDto);
    }

    @Test
    public void finishExecuteValidatorAndThrowIncorrectDataExceptionWhenRentalValidatorThrows() {
        RentalFinishDto rentalFinishDto = mock(RentalFinishDto.class);
        doThrow(IncorrectDataException.class).when(rentalValidator).validateRentalFinishDto(rentalFinishDto);
        assertThrows(IncorrectDataException.class, () -> rentalService.finish(anyInt(), rentalFinishDto));
        verify(rentalValidator, times(1)).validateRentalFinishDto(rentalFinishDto);
    }

    @Test
    public void finishValidTest() {
        RentalFinishDto rentalFinishDto = mock(RentalFinishDto.class);
        when(rentalFinishDto.getVehicleTechnicalConditionDto()).thenReturn(mock(VehicleTechnicalConditionDto.class));
        when(rentalFinishDto.getDiscountId()).thenReturn(null);
        when(rentalFinishDto.getStatus()).thenReturn(RentalStatus.DONE);
        doNothing().when(rentalValidator).validateRentalFinishDto(rentalFinishDto);
        Rental rental = mock(Rental.class);
        when(rental.getVehicle()).thenReturn(mock(Vehicle.class));
        RentalOffice rentalOffice = mock(RentalOffice.class);
        when(rentalDao.findById(anyInt())).thenReturn(rental);
        when(rentalOfficeDao.findById(anyInt())).thenReturn(rentalOffice);
        BigDecimal cost = BigDecimal.valueOf(20);
        BigDecimal discount = BigDecimal.valueOf(2);
        when(tariffService.calculateCost(any())).thenReturn(cost);
        when(discountService.calculateDiscountAmount(any(), any())).thenReturn(discount);
        rentalService.finish(anyInt(), rentalFinishDto);

        verify(rental, times(1)).setFinishRentalDateTime(any());
        verify(rental, times(1)).setFinishRentalOffice(rentalOffice);
        verify(rental, times(1)).setStatus(any());
        verify(rental, times(1)).setDiscountAmount(discount);
        verify(rental, times(1)).setTotalCost(cost.subtract(discount));
        verify(rentalDao, times(1)).update(rental);
    }

}
