<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="logout">
  <form method="get" action="<c:url value="/app/logout"/>">
    <img src="<c:url value="/resources/images/logout.png"/>" onclick="this.parentNode.submit()"/>
  </form>
</div>