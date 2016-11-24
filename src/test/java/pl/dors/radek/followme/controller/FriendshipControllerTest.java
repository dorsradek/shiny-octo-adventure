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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.JwtUserFactory;
import pl.dors.radek.followme.service.IFriendshipService;

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
@WebMvcTest(FriendshipController.class)
@ActiveProfiles("development")
public class FriendshipControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private IFriendshipService friendshipService;

    User user1;

    User friend1;
    User friend2;

    List<User> friends;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        friends = new ArrayList<>();

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("User1");

        friend1 = new User();
        friend1.setId(2L);
        friend1.setUsername("Friend1");
        friends.add(friend1);

        friend2 = new User();
        friend2.setId(3L);
        friend2.setUsername("Friend2");
        friends.add(friend2);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(JwtUserFactory.create(user1));
        SecurityContextHolder.setContext(securityContext);

        given(this.friendshipService.findAllFriends(eq(user1.getUsername()))).willReturn(friends);
    }

    @Test
    public void findAllTest() throws Exception {
        this.mockMvc.perform(get("/friends"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(friends.get(0).getUsername())));
    }
}
