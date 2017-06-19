<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="POST"  action="/user/dashboard-search">
    <div class="form-group">
        <label class="control-label pull-left search-input" for="SearchShareProject">Search project:</label>
        <div class="col-sm-3">
            <input class="form-control input-sm" id="SearchShareProject" name="SearchShareProject"
                   type="text" placeholder="Project name..."
            <c:if test = "${not empty searchShareName}"> value = "${searchShareName}" </c:if>
                   autofocus>
            <input type="text" name="tab" value="2" hidden>
            <input type="text" name="SearchProject" <c:if test = "${not empty searchName}"> value = "${searchName}" </c:if> hidden>
        </div>
        <div class="form-group col-sm-3">
            <button type="submit" class="btn btn-sm btn-success" value="search" name="search">Search</button>
            <button type="submit" class="btn btn-sm btn-danger" value="cancel" name="cancel">Cancel</button>
        </div>
    </div>
</form>