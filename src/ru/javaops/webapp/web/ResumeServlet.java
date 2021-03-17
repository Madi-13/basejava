package ru.javaops.webapp.web;

import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.Storage;
import ru.javaops.webapp.util.Config;
import ru.javaops.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            case "add":
                resume = new Resume("","");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " doesn't support");
        }

        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                action.equals("view")? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").
                forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        boolean actionAdd = false;
        if (uuid == null || uuid.trim().length() == 0) {
            resume = new Resume(fullName);
            actionAdd = true;
        } else {
            resume = new Resume(uuid, fullName);
        }
        for (Contact type : Contact.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            }
        }
        for (Section type : Section.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (value != null && value.trim().length() != 0) {
                readSection(request, resume, values, type);
            }
        }
        if (actionAdd) {
            if (fullName.trim().length() != 0) {
                storage.save(resume);
            }
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    private void readSection(HttpServletRequest request, Resume resume, String[] values, Section type) {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                resume.addSection(type, new Text(values[0]));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                resume.addSection(type, new ListOfTexts(values));
                break;
            case EXPERIENCE:
            case EDUCATION:
                Map<String, Organization> map = new HashMap<>();
                String[] urls = request.getParameterValues(type.name() + "orgLink");
                String[] pos = request.getParameterValues(type.name() + "posTitle");
                String[] posInfos = request.getParameterValues(type.name() + "posInfo");
                String[] startMonths = request.getParameterValues(type.name() + "posStartDateMonth");
                String[] startYears = request.getParameterValues(type.name() + "posStartDateYear");
                String[] endMonths = request.getParameterValues(type.name() + "posEndDateMonth");
                String[] endYears = request.getParameterValues(type.name() + "posEndDateYear");
                for (int i = 0; i < values.length; i++) {
                    if (values[i].trim().length() == 0 ||
                        pos[i].trim().length() == 0 ||
                        startMonths[i].trim().length() == 0 ||
                        startYears[i].trim().length() == 0) {
                        continue;
                    }
                    Organization org = map.get(values[i]);
                    boolean put = false;
                    if (org == null) {
                        org = new Organization(values[i], urls[i]);
                        put = true;
                    }
                    LocalDate startDate = DateUtil.of(Integer.parseInt(startYears[i]), Month.of(Integer.parseInt(startMonths[i])));
                    LocalDate endDate;
                    if (endYears[i].trim().length() != 0 && endMonths[i].trim().length() != 0) {
                        endDate = DateUtil.of(Integer.parseInt(endYears[i]), Month.of(Integer.parseInt(endMonths[i])));
                    } else {
                        endDate = DateUtil.NOW;
                    }
                    Organization.Position position =
                            new Organization.Position(
                                    startDate, endDate,
                                    pos[i].trim().length() == 0? "Unknown" : pos[i],
                                    posInfos[i].trim().length() == 0? "None" : posInfos[i]);
                    org.addPosition(position);
                    if (put) {
                        map.put(values[i], org);
                    }
                }
                resume.addSection(type, new ListOfOrganizations(new ArrayList<>(map.values())));
                break;
            default:
                throw new IllegalArgumentException("Illegal section" + type.name());
        }
    }
}
