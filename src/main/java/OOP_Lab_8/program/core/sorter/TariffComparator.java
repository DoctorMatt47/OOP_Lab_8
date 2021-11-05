package OOP_Lab_8.program.core.sorter;

import OOP_Lab_8.program.domain.entity.Tariff;

import java.util.Comparator;

public class TariffComparator implements Comparator<Tariff> {
    @Override
    public int compare(Tariff o1, Tariff o2) {
        return o1.id().compareTo(o2.id());
    }
}
