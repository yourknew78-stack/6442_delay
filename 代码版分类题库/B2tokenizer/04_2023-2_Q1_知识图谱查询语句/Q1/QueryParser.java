import java.util.List;
import java.util.Set;

public class QueryParser {

    /**
     *  Question 1(e). Given the List of QTokens (representing tokens for the search) and
     *  Set of triples available,
     *  return the corresponding set of objects if the search query adhere to the CFG,
     *  or return null otherwise.
     */
    public static Set<Object> parse(List<QToken> tokens, Set<Triple> triples) {
        if (tokens == null || tokens.size() < 2 || tokens.size() > 4) return null;
        QToken select = tokens.get(0);
        if (select == null || select.getqTokenType() != QTokenType.SELECT || select.getElemType() == null) return null;
        TripleComponent.TripleComponentType[] positions = {
            TripleComponent.TripleComponentType.SUBJECT,
            TripleComponent.TripleComponentType.PREDICATE,
            TripleComponent.TripleComponentType.OBJECT
        };
        // WHERE 中第 1、2、3 个位置分别只能是主语、谓语、宾语。
        for (int i = 1; i < tokens.size(); i++) {
            QToken token = tokens.get(i);
            if (token == null) return null;
            if (token.getqTokenType() == QTokenType.WHERE_ANY) {
                if (token.getElemType() != positions[i - 1]) return null;
            } else if (token.getqTokenType() != QTokenType.WHERE_MATCH) return null;
        }
        Set<Object> result = new java.util.HashSet<Object>();
        for (Triple triple : triples) {
            TripleComponent[] parts = {triple.getSubject(), triple.getPredicate(), triple.getObject()};
            boolean matches = true;
            for (int i = 1; i < tokens.size(); i++) {
                QToken token = tokens.get(i);
                if (token.getqTokenType() == QTokenType.WHERE_MATCH && !parts[i - 1].getValue().equals(token.getValue())) matches = false;
            }
            if (matches) {
                if (select.getElemType() == TripleComponent.TripleComponentType.TRIPLE) result.add(triple);
                else {
                    for (int i = 0; i < positions.length; i++) {
                        if (select.getElemType() == positions[i]) result.add(parts[i]);
                    }
                }
            }
        }
        return result;
    }


}
