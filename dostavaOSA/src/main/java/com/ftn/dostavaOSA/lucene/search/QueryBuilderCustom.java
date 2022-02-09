package com.ftn.dostavaOSA.lucene.search;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.ftn.dostavaOSA.model.TipPretrage;

public class QueryBuilderCustom {

	public static QueryBuilder buildQuery(TipPretrage queryType, String field, String value) {
		
		if(queryType.equals(TipPretrage.TERM)){
			return QueryBuilders.termQuery(field, value);
		} else if(queryType.equals(TipPretrage.MATCH)){
			return QueryBuilders.matchQuery(field, value);
		} else if(queryType.equals(TipPretrage.MATCH_LONG)){
			return QueryBuilders.matchQuery(field, Long.parseLong(value));
		} else if(queryType.equals(TipPretrage.PHRASE)){
			return QueryBuilders.matchPhraseQuery(field, value);
		} else if(queryType.equals(TipPretrage.FUZZY)){
			return QueryBuilders.fuzzyQuery(field, value).fuzziness(Fuzziness.fromEdits(1));
		} else if(queryType.equals(TipPretrage.PREFIX)){
			return QueryBuilders.prefixQuery(field, value);
		} else if(queryType.equals(TipPretrage.RANGE)){
			String[] values = value.split("-");
			return QueryBuilders.rangeQuery(field).from(values[0]).to(values[1]);
		} else{
			return QueryBuilders.matchPhraseQuery(field, value);
		}
	}
}
