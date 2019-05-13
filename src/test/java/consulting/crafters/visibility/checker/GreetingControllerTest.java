package consulting.crafters.visibility.checker;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class GreetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExternalVisibilityService visibilityService;

    @Before
    public void beforeEach() {
        when(visibilityService.hasVisibilityOn("abachar", "Name-Hakim")).thenReturn(true);
        when(visibilityService.hasVisibilityOn("ilovich", "Name-Hakim")).thenReturn(false);
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "abachar", roles = {"ADMIN"})
    public void should_allow_access_greeting_for_hakim_to_user_abachar_using_has_visibility_annotation() {
        val result = performGreetingRequest();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("I can say hello to Hakim");
    }

    @Test
    @WithMockUser(username = "ilovich", roles = {"MEMBER"})
    public void should_not_allow_access_greeting_for_hakim_to_user_ilovich_using_has_visibility_annotation() {
        val result = performGreetingRequest();
        assertThat(result.getResponse().getStatus()).isEqualTo(403);
    }

    @SneakyThrows
    private MvcResult performGreetingRequest() {
        val request = MockMvcRequestBuilders
                .get("/greetings?name=Hakim")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8);

        return mockMvc.perform(request).andReturn();
    }
}
