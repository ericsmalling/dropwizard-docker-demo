package xyz.smalls.goingnowhere;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import xyz.smalls.goingnowhere.health.TemplateHealthCheck;
import xyz.smalls.goingnowhere.resources.HelloWorldResource;

public class goingnowhereApplication extends Application<goingnowhereConfiguration> {

    public static void main(final String[] args) throws Exception {
        new goingnowhereApplication().run(args);
    }

    @Override
    public String getName() {
        return "goingnowhere";
    }

    @Override
    public void initialize(final Bootstrap<goingnowhereConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final goingnowhereConfiguration configuration,
                    final Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }

}
