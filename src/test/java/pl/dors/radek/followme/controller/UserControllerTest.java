package pl.dors.radek.followme.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.JwtUserFactory;
import pl.dors.radek.followme.service.IMeetingService;
import pl.dors.radek.followme.service.IUserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by rdors on 2016-11-24.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private IMeetingService meetingService;
    @MockBean
    private IUserService userService;

    User user1;

    User friend1;
    User friend2;

    List<User> friends;
    List<Meeting> meetings;

    private static final String USER_CONTROLLER_URL = "/users";

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("User1");

        friends = new ArrayList<>();
        meetings = new ArrayList<>();

        friend1 = new User();
        friend1.setId(2L);
        friend1.setUsername("Friend1");
        friends.add(friend1);

        friend2 = new User();
        friend2.setId(3L);
        friend2.setUsername("Friend2");
        friends.add(friend2);

        Meeting meeting1 = new Meeting();
        meeting1.setId(1L);
        meeting1.setName("Meeting1");
        meetings.add(meeting1);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(JwtUserFactory.create(user1));
        SecurityContextHolder.setContext(securityContext);

        given(this.userService.findAllExceptUsername(eq(user1.getUsername()))).willReturn(friends);
        given(this.userService.findById(eq(user1.getId()))).willReturn(user1);
        given(this.userService.findById(eq(friend1.getId()))).willThrow(RuntimeException.class);
        given(this.meetingService.findByUserId(user1.getId())).willReturn(meetings);
        given(this.meetingService.findByUserId(friend1.getId())).willThrow(RuntimeException.class);
    }

    @Test
    public void findAllExceptLoggedInUserTest() throws Exception {
        this.mockMvc.perform(get(USER_CONTROLLER_URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(friends.get(0).getUsername())));
    }

    @Test
    public void showDetailsTest() throws Exception {
        this.mockMvc.perform(get(USER_CONTROLLER_URL + "/{userId}", user1.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.username", is(user1.getUsername())));
    }

    @Test
    public void showDetailsTest_UserNotExists() throws Exception {
        this.mockMvc.perform(get(USER_CONTROLLER_URL + "/{userId}", friend1.getId()))
                .andExpect(status().is(400));
    }

    @Test
    public void findAllByUserIdTest() throws Exception {
        this.mockMvc.perform(get(USER_CONTROLLER_URL + "/{userId}/meetings", user1.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(meetings.get(0).getName())));
    }

    @Test
    public void findAllByUserIdTest_UserNotExists() throws Exception {
        this.mockMvc.perform(get(USER_CONTROLLER_URL + "/{userId}/meetings", friend1.getId()))
                .andExpect(status().is(400));
    }
}
