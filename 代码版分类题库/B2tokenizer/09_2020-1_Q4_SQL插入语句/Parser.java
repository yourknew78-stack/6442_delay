/**
 * IMPORTANT: This class is incomplete. Please look for "TODO" comments.
 * 
 * Implement a parser to extract the columns and values from tokens and execute
 * the SQL command to insert new customers. Do not insert customers if the
 * following errors are found: 
 * 1 - some brackets are missing 
 * 2 - some columnnames are wrong
 * 
 * Please see the columns names in Customer.java file. Please see test cases in
 * ParserTest.java
 */
public class Parser {

	Tokeniser tokeniser;
	XMLTable table;

	public Parser(Tokeniser tokeniser, XMLTable table) {
		this.tokeniser = tokeniser;
		this.table = table;
	}

	/**
	 * Extract the columns and values from tokens and execute the SQL command to insert new customers
	 */
	public void parseExp() {
        Token insert = tokeniser.takeNext();
        Token values = tokeniser.takeNext();
        if (insert == null || values == null || insert.type != Token.Type.INSERT_INTO || values.type != Token.Type.VALUES) return;
        int left = insert.value.indexOf('(');
        int right = insert.value.lastIndexOf(')');
        String tableName = insert.value.substring(0, left).trim();
        String[] columns = insert.value.substring(left + 1, right).split(",");
        String body = values.value.substring(1, values.value.length() - 1);
        // 引号里的逗号是字符串内容，只有引号外的逗号才分隔字段。
        java.util.List<String> data = new java.util.ArrayList<String>();
        boolean quoted = false;
        int start = 0;
        for (int i = 0; i < body.length(); i++) {
            char c = body.charAt(i);
            if (c == '\'') quoted = !quoted;
            if (c == ',' && !quoted) {
                data.add(body.substring(start, i).trim());
                start = i + 1;
            }
        }
        if (quoted) return;
        data.add(body.substring(start).trim());
        if (columns.length != data.size()) return;
        Customer customer = new Customer();
        boolean hasId = false;
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i].trim();
            String value = data.get(i);
            if (value.startsWith("'") && value.endsWith("'")) value = value.substring(1, value.length() - 1);
            if (column.equals(Customer.KEY_ID)) {
                try { customer.setId(Integer.parseInt(value)); } catch (NumberFormatException e) { return; }
                hasId = true;
            } else if (column.equals(Customer.KEY_NAME)) customer.setName(value);
            else if (column.equals(Customer.KEY_ADDRESS)) customer.setAddress(value);
            else if (column.equals(Customer.KEY_CITY)) customer.setCity(value);
            else if (column.equals(Customer.KEY_POSTCODE)) customer.setPostCode(value);
            else if (column.equals(Customer.KEY_COUNTRY)) customer.setCountry(value);
            else return; // 错误列名：整条命令不插入。
        }
        if (hasId) table.insert(tableName, customer);
    }
}
