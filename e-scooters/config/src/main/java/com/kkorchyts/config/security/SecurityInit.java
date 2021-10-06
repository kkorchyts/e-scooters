package com.kkorchyts.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
/*
If we were using Spring elsewhere in our application we probably already had a WebApplicationInitializer that is loading our Spring Configuration.
If we use the previous configuration we would get an error.
Instead, we should register Spring Security with the existing ApplicationContext.
For example, if we were using Spring MVC our SecurityWebApplicationInitializer would look something like this class:
https://docs.spring.io/spring-security/site/docs/current/reference/html5/#abstractsecuritywebapplicationinitializer-with-spring-mvc

Аналог
<filter>
 <filter-name>springSecurityFilterChain</filter-name>
 <filter-class>
 org.springframework.web.filter.DelegatingFilterProxy
 </filter-class>
</filter>

<filter-mapping>
 <filter-name>springSecurityFilterChain</filter-name>
 <url-pattern>/*</url-pattern>
 <dispatcher>ERROR</dispatcher>
 <dispatcher>REQUEST</dispatcher>
</filter-mapping>
*/

public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
