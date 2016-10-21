package pl.dors.radek.followme.commons;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by rdors on 2016-06-29.
 */
@ControllerAdvice
public class TestCommons {

    public static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    public String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, CONTENT_TYPE, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
