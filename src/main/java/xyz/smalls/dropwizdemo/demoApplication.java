package xyz.smalls.dropwizdemo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import xyz.smalls.dropwizdemo.health.TemplateHealthCheck;
import xyz.smalls.dropwizdemo.resources.HelloWorldResource;

public class demoApplication extends Application<demoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new demoApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizdemo";
    }

    @Override
    public void initialize(final Bootstrap<demoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final demoConfiguration configuration,
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
