package ru.egormit.starshipservice.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.egormit.starshipservice.error.ErrorDescriptions;
import ru.itmo.library.Event;
import ru.itmo.library.Ticket;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TicketSpecification implements Specification<Ticket> {
    private List<FilterCriteria> criteries;

    public TicketSpecification(List<FilterCriteria> filterCriteria) {
        this.criteries = filterCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (FilterCriteria crit : criteries) {
            if (crit.getOperation().equalsIgnoreCase("gt")) {
                predicates.add(builder.greaterThan(
                        root.<String> get(crit.getKey()), crit.getValue().toString()));
            }
            else if (crit.getOperation().equalsIgnoreCase("lt")) {
                predicates.add(builder.lessThan(
                        root.<String> get(crit.getKey()), crit.getValue().toString()));
            }
            else if (crit.getOperation().equalsIgnoreCase("eq")) {
                if (root.get(crit.getKey()).getJavaType() == String.class) {
                    predicates.add(builder.like(
                            root.<String>get(crit.getKey()), "%" + crit.getValue() + "%"));
                } else {
                    predicates.add(builder.equal(root.get(crit.getKey()), crit.getValue()));
                }
            }
            else if (crit.getOperation().equalsIgnoreCase("ne")) {
                if (root.get(crit.getKey()).getJavaType() == String.class) {
                    predicates.add(builder.notLike(
                            root.<String>get(crit.getKey()), "%" + crit.getValue() + "%"));
                } else {
                    predicates.add(builder.notEqual(root.get(crit.getKey()), crit.getValue()));
                }
            }
            else {
                throw ErrorDescriptions.INCORRECT_FILTER.exception();
            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}

