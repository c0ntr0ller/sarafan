package ru.progmatik.sarafan;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {SarafanApplication.class})
public class MessageControllerTest {
    @Autowired
    private MockMvc mock;

    private static final String BaseURL = "http://localhost:8080/message";

    @Autowired
    private ObjectMapper mapper;
//    @Before
//    инициализировать пока не нужно - массив заполняется
//    public void setUp() throws Exception {
//        String detailsString =
//                objectMapper.writeValueAsString(new Details("John Smith", "john"));
//
//        this.server.expect(requestTo("/john/details"))
//                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
//    }

    @Test
    public void contextLoads() {
        assertThat(this.mock,  notNullValue());
    }

//    @Test
//    public void messagesExists() throws Exception {
//        MvcResult result = getMessageCount(3);
////        final ArrayList<Map<String, String>> resultString = mapper.readValues(result.getResponse().getContentAsString(), Map<String, String>);
////        fromJsonResult(result, Hero[].class);
//    }

    private MvcResult getMessageCount(int count) throws Exception {
        return mock.perform(get(BaseURL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(count)))
//                .andExpect(jsonPath("$[0].name", is("Superman")))
                .andReturn();
    }

    @Test
    public void messageCreate() throws Exception {

        Map<String, String> newMessage = new HashMap<String, String>() {{put("text", "four message");}};

        mock.perform(post(BaseURL).content(mapper.writeValueAsString(newMessage).getBytes()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.text", is(newMessage)))
                .andReturn();

        getMessageCount(4);


    }
}