package miniproject.infra;

import miniproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class PointHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Point>> {

    @Override
    public EntityModel<Point> process(EntityModel<Point> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/deductpoint")
                .withRel("deductpoint")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/chargepoint")
                .withRel("chargepoint")
        );

        return model;
    }
}
