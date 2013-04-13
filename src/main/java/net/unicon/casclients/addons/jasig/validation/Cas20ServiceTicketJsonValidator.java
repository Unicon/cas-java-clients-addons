package net.unicon.casclients.addons.jasig.validation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import net.unicon.cas.addons.response.ServiceValidateSuccessJsonView;
import net.unicon.cas.addons.response.TicketValidationJsonResponse;

import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.jasig.cas.client.validation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * An implementation of the {@link org.jasig.cas.client.validation.Cas20ServiceTicketValidator} that expects the ticket validation response to be
 * a JSON string, represented by {@link net.unicon.cas.addons.response.TicketValidationJsonResponse} that is rendered by {@link net.unicon.cas.addons.response.ServiceValidateSuccessJsonView}.
 * On success, it returns an instance of {@link org.jasig.cas.client.validation.Assertion} object that is populated with ticket data and attributes, if any.
 *
 * @author <a href="mailto:mmoayyed@unicon.net">Misagh Moayyed</a>
 * @author Unicon, inc.
 * @since 0.5
 */
public class Cas20ServiceTicketJsonValidator extends AbstractCasProtocolUrlBasedTicketValidator {

    private ObjectMapper jacksonObjectMapper = null;

    public Cas20ServiceTicketJsonValidator(final String casServerUrlPrefix) {
        super(casServerUrlPrefix);
        this.jacksonObjectMapper = new ObjectMapper();
    }

    @Override
    protected String getUrlSuffix() {
        return "serviceValidate";
    }

    @Override
    protected Assertion parseResponseFromServer(String response) throws TicketValidationException {
        Assertion assertion = null;
        try {
            final TicketValidationJsonResponse jsonResponse = this.jacksonObjectMapper.readValue(response,
                    TicketValidationJsonResponse.class);

            if (CommonUtils.isEmpty(jsonResponse.getUser())) {
                throw new TicketValidationException(
                        "No principal was found in the response from the CAS server.");
            }

            final Map<String, Object> attributes = jsonResponse.getAttributes();
            assertion = new AssertionImpl(new AttributePrincipalImpl(jsonResponse.getUser(), attributes),
                    attributes);
        }
        catch (final IOException e) {
            logger.error(e.getMessage(), e);
            throw new TicketValidationException("An error occurred while parsing the json response: "
                    + response, e);
        }

        return assertion;
    }
}
