package ruisun;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Project1Task2", urlPatterns = {"/project1Task2", "/getStates"})
public class Project1Task2 extends HttpServlet {
    Model covid;
    Map<String, String> abbr_state = new HashMap<>();
    Map<String, String> abbr_category = new HashMap<>() {{
        put("positive", "Positive Cases");
        put("negative", "Negative Cases");
        put("hospitalizedCurrently", "Currently Hospitalized");
        put("hospitalizedCumulative", "Cumulative Hospitalized");
    }};
    String[][] abbrStates;

    @Override
    public void init() {
        covid = new Model();
        String rawStates = "ak,Alaska\n" + "al,Alabama\n" + "ar,Arkansas\n" + "az,Arizona\n" + "ca,California\n" +
                "co,Colorado\n" + "ct,Connecticut\n" + "de,Delaware\n" + "fl,Florida\n" + "ga,Georgia\n" +
                "hi,Hawaii\n" + "ia,Iowa\n" + "id,Idaho\n" + "il,Illinois\n" + "in,Indiana\n" + "ks,Kansas\n" +
                "ky,Kentucky\n" + "la,Louisiana\n" + "ma,Massachusetts\n" + "md,Maryland\n" + "me,Maine\n" +
                "mi,Michigan\n" + "mn,Minnesota\n" + "mo,Missouri\n" + "ms,Mississippi\n" + "mt,Montana\n" +
                "ne,Nebraska\n" + "nc,North Carolina\n" + "nd,North Dakota\n" + "nh,New Hampshire\n" +
                "nj,New Jersey\n" + "nm,New Mexico\n" + "ny,New York\n" + "nv,Nevada\n" + "oh,Ohio\n" +
                "ok,Oklahoma\n" + "or,Oregon\n" + "pa,Pennsylvania\n" + "ri,Rhode Island\n" + "sc,South Carolina\n" +
                "sd,South Dakota\n" + "tn,Tennessee\n" + "tx,Texas\n" + "ut,Utah\n" + "va,Virginia\n" +
                "vt,Vermont\n" + "wa,Washington\n" + "wi,Wisconsin\n" + "wv,West Virginia\n" + "wy,Wyoming\n";
        String[] rawStatesList = rawStates.split("\n");
        abbrStates = new String[rawStatesList.length][2];
        for (int i = 0; i < rawStatesList.length; i++) {
            String[] temp = rawStatesList[i].split(",");
            abbr_state.put(temp[0], temp[1]);
            System.arraycopy(temp, 0, abbrStates[i], 0, 2);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String state = request.getParameter("state");
        String category = request.getParameter("category");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

//      This part of code (check device) is form Lab2
        String ua = request.getHeader("User-Agent");
        boolean mobile;
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        String picSize = (mobile) ? "mobile" : "desktop";

        String flag = covid.getPicture(abbr_state.get(state), picSize);
        String from = covid.getStats(state, category, String.join("", startDate.split("-")));
        String to = covid.getStats(state, category, String.join("", endDate.split("-")));
        String change;
        if (from != null && to != null) {
            change = Integer.toString(Integer.parseInt(to) - Integer.parseInt(from));
        } else {
            if (from == null) {
                from = "Not Available";
            }
            if (to == null) {
                to = "Not Available";
            }
            change = "Not Available";
        }

        request.setAttribute("state", abbr_state.get(state));
        request.setAttribute("flag", flag);
        request.setAttribute("category", abbr_category.get(category));
        request.setAttribute("from", from);
        request.setAttribute("to", to);
        request.setAttribute("change", change);

        RequestDispatcher view = request.getRequestDispatcher("result.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("abbrStates", abbrStates);

        String ua = request.getHeader("User-Agent");
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
    }
}
