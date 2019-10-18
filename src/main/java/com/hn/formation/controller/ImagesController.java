package com.hn.formation.controller;

import com.hn.formation.model.DogImageInformations;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.xml.transform.ResourceSource;
import reactor.core.publisher.Mono;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Controller
public class ImagesController {

    @Autowired
    ResourceLoader resourceLoader;


    @RequestMapping(value = "/random-dog-image", method = RequestMethod.GET)
    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
        Resource resource = this.generateImageUrl()
                .map(imageUrl -> WebClient.create(imageUrl) )
                .map(WebClient::get)
                .map(WebClient.RequestHeadersSpec::retrieve)
                .flatMap(responseSpec -> responseSpec.bodyToMono(Resource.class))
                .block();

        InputStream in = resource.getInputStream();

        IOUtils.copy(in, response.getOutputStream());
    }

    private Mono<String> generateImageUrl(){
        return WebClient.create("https://dog.ceo/api/breeds/image/random")
                .get()
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(DogImageInformations.class)
                .map(DogImageInformations::getMessage)
                .map(message -> message.replaceAll("'\'",""));
    }
}
