package io.codelirium.blueground.intergalactica.model.dto.event;

import io.codelirium.blueground.intergalactica.model.entity.UnitViewersEntity;
import org.springframework.context.ApplicationEvent;


public class UnitViewersEvent extends ApplicationEvent {

	public UnitViewersEvent(final UnitViewersEntity unitViewersEntity) {

		super(unitViewersEntity);

	}
}
