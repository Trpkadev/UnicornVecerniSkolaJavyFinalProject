package cz.simekdaniel.nakupniseznam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api")
public class Controller
{
    @GetMapping(value = "/restObjectRequest", produces = APPLICATION_JSON_VALUE)
    public void restObjectRequest()
    {
    }
}
