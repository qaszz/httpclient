package no.kristiania.http_server;

public class QueryString {
    private final String value;
    private final String parameterName;

    public QueryString(String queryString){
        // int equalsPos is '=' in the queryString, set value as equalsPos+1 which is what comes after '='
        int equalsPos = queryString.indexOf('=');
        value = queryString.substring(equalsPos+1);
        parameterName = queryString.substring(0, equalsPos);
    }

    public String getParameter(String name) {
        if (name.equals(parameterName)) {
            return value;
        }
        return null;
    }
}
