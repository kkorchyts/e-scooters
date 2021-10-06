import com.kkorchyts.dto.dtos.RentalDto;
import com.kkorchyts.dto.dtos.RentalStartDto;
import com.kkorchyts.dto.dtos.UserDto;
import com.kkorchyts.restapi.controllers.RentalController;
import com.kkorchyts.restapi.validators.AccessValidator;
import com.kkorchyts.restapi.validators.UsersUtils;
import com.kkorchyts.service.services.rental.RentalService;
import com.kkorchyts.service.services.user.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.quality.Strictness;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccessValidator.class, UsersUtils.class})
public class RentalControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RentalService rentalService;

    @InjectMocks
    private RentalController rentalController = new RentalController();

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

    @org.junit.Test
    public void rentVehicleValidTest() throws Exception {
        String login = "login";
        Integer userId = 1;
        RentalStartDto rentalStartDto = mock(RentalStartDto.class);
        UserDto userDto = mock(UserDto.class);
        when(userDto.getId()).thenReturn(userId);
        when(userService.findByLogin(login)).thenReturn(userDto);

        RentalDto rentalDto = mock(RentalDto.class);
        when(rentalService.start(any())).thenReturn(rentalDto);

        PowerMockito.mockStatic(UsersUtils.class);
        PowerMockito.when(UsersUtils.getAuthenticatedUserLogin()).thenReturn(login);
        PowerMockito.mockStatic(AccessValidator.class);

        ResponseEntity<RentalDto> resultRentalDto = rentalController.rentVehicle(rentalStartDto);
        verify(rentalStartDto, times(1)).setUserId(userId);
        verify(rentalService, times(1)).start(rentalStartDto);
        assertEquals(rentalDto, resultRentalDto.getBody());

        PowerMockito.verifyStatic(AccessValidator.class);
        AccessValidator.validateLimitedAccess(any(), any());
    }

}
