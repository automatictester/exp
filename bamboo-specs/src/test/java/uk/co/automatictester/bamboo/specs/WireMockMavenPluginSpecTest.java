package uk.co.automatictester.bamboo.specs;

import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import com.atlassian.bamboo.specs.api.util.EntityPropertiesBuilders;
import org.junit.Test;

public class WireMockMavenPluginSpecTest {

    @Test
    public void checkYourPlanOffline() throws PropertiesValidationException {
        Plan plan = new WireMockMavenPluginSpec().getPlan();
        EntityPropertiesBuilders.build(plan);
    }
}
