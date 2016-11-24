package pl.dors.radek.followme.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.JwtUserFactory;
import pl.dors.radek.followme.service.IUserService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by rdors on 2016-10-21.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
@ActiveProfiles("development")
public class LocationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    User user1;

    double x;
    double y;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("User1");

        x = 10;
        y = 20;

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(JwtUserFactory.create(user1));
        SecurityContextHolder.setContext(securityContext);

        given(this.userService.updateLocation(eq(user1.getUsername()), eq(x), eq(y))).willReturn(user1);
        given(this.userService.updateLocation(eq(user1.getUsername()), isNull(Double.class), eq(y))).willThrow(RuntimeException.class);
    }

    @Test
    public void updateTest() throws Exception {
        this.mockMvc.perform(put("/location").param("x", String.valueOf(x)).param("y", String.valueOf(y)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTest_XNull() throws Exception {
        this.mockMvc.perform(put("/location").param("x", "").param("y", String.valueOf(y)))
                .andExpect(status().is(400));
    }

    @Test
    public void updateTest_YNull() throws Exception {
        this.mockMvc.perform(put("/location").param("x", String.valueOf(y)).param("y", ""))
                .andExpect(status().is(400));
    }

}
