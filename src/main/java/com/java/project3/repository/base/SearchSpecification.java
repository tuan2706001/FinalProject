package com.java.project3.repository.base;

import com.java.project3.dto.base.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.java.project3.constant.Constants.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.String.format;
import static java.time.Instant.parse;

@NoArgsConstructor
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = -0x2733DE2E86ED0B65L;
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (criteria.getType()) {
            case NUM_PARAM: {
                if (criteria.getOperation().equals(G_T))
                    return criteriaBuilder.gt(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
                if (criteria.getOperation().equals(L_T))
                    return criteriaBuilder.lt(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
                if (criteria.getOperation().equals(EQUALS))
                    return criteriaBuilder.equal(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
                if (criteria.getOperation().equals(NOT_EQUALS))
                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
                if (criteria.getOperation().equals(G_T_EQUALS))
                    return criteriaBuilder.ge(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
                if (criteria.getOperation().equals(L_T_EQUALS))
                    return criteriaBuilder.le(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
                if (criteria.getOperation().equals(IN)) {
                    In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
                    for (String str : criteria.getValue().split(COMMA))
                        in.value(new BigDecimal(str));
                    return in;
                }
            }
            return null;
            case LOCAL_DATE_PARAM: {
                if (criteria.getOperation().equals(G_T))
                    return criteriaBuilder.greaterThan(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
                if (criteria.getOperation().equals(L_T))
                    return criteriaBuilder.lessThan(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
                if (criteria.getOperation().equals(EQUALS))
                    return criteriaBuilder.equal(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
                if (criteria.getOperation().equals(NOT_EQUALS))
                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
                if (criteria.getOperation().equals(G_T_EQUALS))
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
                if (criteria.getOperation().equals(L_T_EQUALS))
                    return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue()));
                if (criteria.getOperation().equals(IN)) {
                    In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
                    for (String str : criteria.getValue().split(COMMA))
                        in.value(parse(str));
                    return in;
                }
            }
            return null;
            case LOCAL_DATE_TIME_PARAM: {
                if (criteria.getOperation().equals(G_T))
                    return criteriaBuilder.greaterThan(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
                if (criteria.getOperation().equals(L_T))
                    return criteriaBuilder.lessThan(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
                if (criteria.getOperation().equals(EQUALS))
                    return criteriaBuilder.equal(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
                if (criteria.getOperation().equals(NOT_EQUALS))
                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
                if (criteria.getOperation().equals(G_T_EQUALS))
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
                if (criteria.getOperation().equals(L_T_EQUALS))
                    return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue()));
                if (criteria.getOperation().equals(IN)) {
                    In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
                    for (String str : criteria.getValue().split(COMMA))
                        in.value(parse(str));
                    return in;
                }
            }
            return null;
            case DATE_TIME_PARAM: {
                if (criteria.getOperation().equals(G_T))
                    return criteriaBuilder.greaterThan(root.get(criteria.getKey()), parse(criteria.getValue()));
                if (criteria.getOperation().equals(L_T))
                    return criteriaBuilder.lessThan(root.get(criteria.getKey()), parse(criteria.getValue()));
                if (criteria.getOperation().equals(EQUALS))
                    return criteriaBuilder.equal(root.get(criteria.getKey()), parse(criteria.getValue()));
                if (criteria.getOperation().equals(NOT_EQUALS))
                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), parse(criteria.getValue()));
                if (criteria.getOperation().equals(G_T_EQUALS))
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), parse(criteria.getValue()));
                if (criteria.getOperation().equals(L_T_EQUALS))
                    return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), parse(criteria.getValue()));
                if (criteria.getOperation().equals(IN)) {
                    In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
                    for (String str : criteria.getValue().split(COMMA))
                        in.value(parse(str));
                    return in;
                }
            }
            return null;
            case STRING_PARAM: {
                if (criteria.getOperation().equals(E_T_S))
                    return criteriaBuilder.equal(root.<String>get(criteria.getKey()), criteria.getValue());
                if (criteria.getOperation().equals(EQUALS))
                    return criteriaBuilder.like(root.<String>get(criteria.getKey()),
                            format(LIKE_PRE_POST, criteria.getValue()));
                if (criteria.getOperation().equals(EQUALS_LOWER))
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get(criteria.getKey())),
                            format(LIKE_PRE_POST, criteria.getValue().toLowerCase()));
                if (criteria.getOperation().equals(NOT_EQUALS))
                    return criteriaBuilder.notLike(root.<String>get(criteria.getKey()),
                            format(LIKE_PRE_POST, criteria.getValue()));
                if (!criteria.getOperation().equals(IN))
                    return null;
                In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
                for (String str : criteria.getValue().split(COMMA))
                    in.value(str);
                return in;
            }
            case BOOLEAN_PARAM:
                return criteriaBuilder.equal(root.get(criteria.getKey()), parseBoolean(criteria.getValue()));
            default:
                return null;
        }
    }
}
