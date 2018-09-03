package my.messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServicesTest {

//    @Mock
    IOUtils ioUtils;

    UserServices userServices;

    @Before
    public void setUp() throws Exception {
        ioUtils = mock(IOUtils.class);
        userServices = new UserServices(ioUtils);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void saveData() {
    }

    @Test
    public void isUserExists() {
    }

    @Test
    public void newUser() {
    }

    @Test
    public void addUser_IfUserExistsInSystem() {

       // verify(ioUtils.writeMessage("Please, enter your personal data:"), atLeast(1));
//        doNothing().when(ioUtils.writeMessage("Please, enter your personal data:");
        when(ioUtils.fileExists("somestring.txt")).thenReturn(true);
        userServices.addUser();
    }
}