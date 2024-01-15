package agh.ics.oop.model.Map;

import agh.ics.oop.model.DirectedPosition;

public interface MoveCalculator {

    DirectedPosition computeMove(DirectedPosition directedPosition);

}
