/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bzh.terrevirtuelle.navisu.instruments.ais.logger.impl;

import bzh.terrevirtuelle.navisu.app.drivers.instrumentdriver.InstrumentDriver;
import bzh.terrevirtuelle.navisu.client.nmea.controller.events.ais.AIS01Event;
import bzh.terrevirtuelle.navisu.client.nmea.controller.events.ais.AIS05Event;
import bzh.terrevirtuelle.navisu.domain.devices.model.BaseStation;
import bzh.terrevirtuelle.navisu.domain.nmea.model.NMEA;
import bzh.terrevirtuelle.navisu.domain.ship.model.Ship;
import bzh.terrevirtuelle.navisu.instruments.ais.base.AisServices;
import bzh.terrevirtuelle.navisu.instruments.ais.base.impl.controller.events.AisCreateStationEvent;
import bzh.terrevirtuelle.navisu.instruments.ais.base.impl.controller.events.AisCreateTargetEvent;
import bzh.terrevirtuelle.navisu.instruments.ais.base.impl.controller.events.AisDeleteStationEvent;
import bzh.terrevirtuelle.navisu.instruments.ais.base.impl.controller.events.AisDeleteTargetEvent;
import bzh.terrevirtuelle.navisu.instruments.ais.base.impl.controller.events.AisUpdateStationEvent;
import bzh.terrevirtuelle.navisu.instruments.ais.base.impl.controller.events.AisUpdateTargetEvent;
import bzh.terrevirtuelle.navisu.instruments.ais.logger.AisLogger;
import bzh.terrevirtuelle.navisu.instruments.ais.logger.AisLoggerServices;
import org.capcaval.c3.component.ComponentEventSubscribe;
import org.capcaval.c3.component.ComponentState;
import org.capcaval.c3.component.annotation.UsedService;
import org.capcaval.c3.componentmanager.ComponentManager;

/**
 * @date 3 mars 2015
 * @author Serge Morvan
 */
public class AisLoggerImpl
        implements AisLogger, InstrumentDriver, AisLoggerServices, ComponentState {

    @UsedService
    AisServices aisServices;

    ComponentManager cm;
    ComponentEventSubscribe<AisCreateStationEvent> aisCSEvent;
    ComponentEventSubscribe<AisCreateTargetEvent> aisCTEvent;
    ComponentEventSubscribe<AisDeleteStationEvent> aisDSEvent;
    ComponentEventSubscribe<AisDeleteTargetEvent> aisDTEvent;
    ComponentEventSubscribe<AisUpdateStationEvent> aisUSEvent;
    ComponentEventSubscribe<AisUpdateTargetEvent> aisUTEvent;
    ComponentEventSubscribe<AIS01Event> ais1ES;
    ComponentEventSubscribe<AIS05Event> ais5ES;

    protected boolean on = false;
    private final String NAME = "AisLogger";

    @Override
    public void componentInitiated() {
        cm = ComponentManager.componentManager;
        aisCSEvent = cm.getComponentEventSubscribe(AisCreateStationEvent.class);
        aisCTEvent = cm.getComponentEventSubscribe(AisCreateTargetEvent.class);
        aisDSEvent = cm.getComponentEventSubscribe(AisDeleteStationEvent.class);
        aisDTEvent = cm.getComponentEventSubscribe(AisDeleteTargetEvent.class);
        aisUSEvent = cm.getComponentEventSubscribe(AisUpdateStationEvent.class);
        aisUTEvent = cm.getComponentEventSubscribe(AisUpdateTargetEvent.class);
        ais1ES = cm.getComponentEventSubscribe(AIS01Event.class);
        ais5ES = cm.getComponentEventSubscribe(AIS05Event.class);
    }

    @Override
    public void componentStarted() {
    }

    @Override
    public void componentStopped() {
    }

    @Override
    public void on(String... files) {
        if (!aisServices.isOn()) {
            aisServices.on();
        }
        if (on == false) {
            on = false;
            aisCTEvent.subscribe((AisCreateTargetEvent) (Ship updatedDate) -> {
                if (on) {
                    System.out.println(updatedDate);
                }
            });
            aisUTEvent.subscribe((AisUpdateTargetEvent) (Ship updatedDate) -> {
                if (on) {
                    System.out.println(updatedDate);
                }
            });
            aisDTEvent.subscribe((AisDeleteTargetEvent) (Ship updatedDate) -> {
                if (on) {
                    System.out.println(updatedDate);
                }
            });

            aisCSEvent.subscribe((AisCreateStationEvent) (BaseStation updatedDate) -> {
                if (on) {
                    System.out.println(updatedDate);
                }
            });
            aisUSEvent.subscribe((AisUpdateStationEvent) (BaseStation updatedDate) -> {
                if (on) {
                    System.out.println(updatedDate);
                }
            });
            aisDSEvent.subscribe((AisDeleteStationEvent) (BaseStation updatedDate) -> {
                if (on) {
                    System.out.println(updatedDate);
                }
            });
        }
    }

    @Override
    public void off() {

        // Pb dans la lib C3 ? objet non retiré de la liste 
        if (on == true) {
            on = false;
            aisCTEvent.unsubscribe((AisCreateTargetEvent) (Ship updatedDate) -> {

            });
            aisUTEvent.unsubscribe((AisUpdateTargetEvent) (Ship updatedDate) -> {

            });
            aisDTEvent.unsubscribe((AisDeleteTargetEvent) (Ship updatedDate) -> {

            });
            aisCSEvent.unsubscribe((AisCreateStationEvent) (BaseStation updatedDate) -> {

            });
            aisUSEvent.unsubscribe((AisUpdateStationEvent) (BaseStation updatedDate) -> {

            });
            aisDSEvent.unsubscribe((AisDeleteStationEvent) (BaseStation updatedDate) -> {

            });
        }
    }

    @Override
    public InstrumentDriver getDriver() {
        return this;
    }

    @Override
    public boolean canOpen(String category) {

        return category.equals(NAME);
    }

    @Override
    public boolean isOn() {
        return on;
    }
}
