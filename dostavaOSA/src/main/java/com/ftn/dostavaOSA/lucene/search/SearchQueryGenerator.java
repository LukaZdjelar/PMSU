package com.ftn.dostavaOSA.lucene.search;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.ftn.dostavaOSA.dto.SimpleQueryDTO;
import com.ftn.dostavaOSA.model.TipPretrage;

public class SearchQueryGenerator {

	public static QueryBuilder createMatchQueryBuilder(SimpleQueryDTO simpleQuery) {
        if(simpleQuery.getValueString().startsWith("\"") && simpleQuery.getValueString().endsWith("\"")) {
            return QueryBuilderCustom.buildQuery(TipPretrage.PHRASE, simpleQuery.getField(), simpleQuery.getValueString());
        } else {
            return QueryBuilderCustom.buildQuery(TipPretrage.MATCH, simpleQuery.getField(), simpleQuery.getValueString());
        }
    }

    public static QueryBuilder createTermLevelQueryBuilder(SimpleQueryDTO simpleQuery) {
        return QueryBuilderCustom.buildQuery(TipPretrage.TERM, simpleQuery.getField(), simpleQuery.getValueString());
    }

    public static QueryBuilder createRangeQueryBuilder(SimpleQueryDTO simpleQuery) {
        return QueryBuilderCustom.buildQuery(TipPretrage.RANGE, simpleQuery.getField(), simpleQuery.getValueString());
    }
    
    public static QueryBuilder createFuzzyQueryBuilder(SimpleQueryDTO simpleQuery) {
        return QueryBuilderCustom.buildQuery(TipPretrage.FUZZY, simpleQuery.getField(), simpleQuery.getValueString());
    }

    public static QueryBuilder createNestedQueryBuilder(String field, BoolQueryBuilder boolQueryBuilder, ScoreMode scoreMode) {
        return QueryBuilders.nestedQuery(field, boolQueryBuilder, scoreMode);
    }
}
