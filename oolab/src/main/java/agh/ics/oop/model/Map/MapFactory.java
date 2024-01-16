package agh.ics.oop.model.Map;

import agh.ics.oop.model.Elements.WorldElement;
import agh.ics.oop.presenter.StartConfigurations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MapFactory {
    WorldMap makeMap(StartConfigurations startConfigurations);
    String getType();
    static List<MapFactory> listFactory =
            List.of(new GlobeFactory(), new InflowsAndOutflowsFactory());
    static Map<String,MapFactory> factoryByName =
            listFactory.stream().collect(Collectors.toMap(
                    MapFactory::getType,
                    mapFactory -> mapFactory
            ));

}
