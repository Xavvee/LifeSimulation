package agh.ics.oop.model.Map;

import agh.ics.oop.model.Genotype.GenotypeFactory;
import agh.ics.oop.presenter.StartConfigurations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MapFactory {
    WorldMap makeMap(StartConfigurations startConfigurations, GenotypeFactory genotypeFactory);
    String getType();
    static List<MapFactory> listFactoryMap =
            List.of(new GlobeFactory(), new InflowsAndOutflowsFactory());
    static Map<String,MapFactory> factoryByNameMap =
            listFactoryMap.stream().collect(Collectors.toMap(
                    MapFactory::getType,
                    mapFactory -> mapFactory
            ));

}
