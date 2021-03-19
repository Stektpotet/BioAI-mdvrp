package mdvrp.ga;

import ga.data.Chromosome;
import mdvrp.Customer;
import mdvrp.Depot;
import mdvrp.structures.CustomerSequence;
import mdvrp.structures.Schedule;

import java.util.*;

public class Util {
    public static Random random = new Random(69);

    public static double duration(Customer a, Customer b) {
        float x = a.getX() - b.getX();
        float y = a.getY() - b.getY();
        x *= x;
        y *= y;
        return Math.sqrt(x + y);
    }


    static double duration(Customer a, Customer b, Customer c) {
        return Util.duration(a, b) + Util.duration(b, c);
    }

    static <T> T randomChoice(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
//    static <K, V> V randomChoice(Map<K, V> map) {
//        return randomChoice(new ArrayList<>(map.values())); // TODO: Not nice
//    }


    public static <C extends Chromosome> C ArgMin(List<C> arr) {
        float minFitness = arr.get(0).fitness();
        C fittest = arr.get(0);
        for (C c : arr){
            float currentFitness = c.fitness();
            if (currentFitness < minFitness) {
                minFitness = currentFitness;
                fittest = c;
            }
        }
        return fittest;
    }

    public static Map<Integer, Schedule> deepCopySchedule(Map<Integer, Schedule> original) {
        Map<Integer, Schedule> copy = new HashMap<>();

        for (Map.Entry<Integer, Schedule> depotSchedule : original.entrySet()) {

            // get depot Id
            Integer depotId = depotSchedule.getKey();

            // copy routes
            Schedule routesOriginal = depotSchedule.getValue();
            Schedule routesCopy = new Schedule(routesOriginal.size());
            for (CustomerSequence singleRouteOriginal : routesOriginal) {
                routesCopy.add(new CustomerSequence(singleRouteOriginal));
            }

            // insert into copied schedule
            copy.put(depotId, routesCopy);
        }

        return copy;
    }

    private static boolean isAssignmentCapacityValid(Map<Depot, List<Customer>> assignment, int numVehicles) {
        int i = 0;
        for (var gene : assignment.entrySet()) {
            int depotRoutesSum = gene.getValue().stream().mapToInt(Customer::getDemand).sum();
            int depotCapacity = gene.getKey().getMaxVehicleLoad() * numVehicles;
            if (depotRoutesSum > depotCapacity)
                return false;
        }
        return true;
    }

}
