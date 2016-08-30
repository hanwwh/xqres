<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <title>TOKEN CHECK RESULT</title>
  </head>
  
  <body>
  	result: ${result}<br>
  	token: ${token}<br>
    signature: ${signature}<br>
    echostr: ${echostr}<br>
    timestamp: ${timestamp}<br>
    nonce: ${nonce}
  </body>
</html>