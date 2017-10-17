/*------------------------------------------------------------------------------
 * This code is distributed under a BEER-WARE license.
 * -----------------------------------------------------------------------------
 * Mario Macias Lloret wrote this file. Considering this, you can do what you
 * want with it: modify it, redistribute it, sell it, etc... But you will always
 * have to credit me as an author in the code.
 *
 * In addition, if we meet someday and you think this code has been useful for
 * you, you MUST pay me a beer (a good one, if possible) as a reward for my
 * contribution.
 * -----------------------------------------------------------------------------
 */

package es.betroop.intranet.cfg

import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.web.env.EnvironmentLoaderListener
import org.apache.shiro.web.servlet.ShiroFilter
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.DispatcherType

/**
 * Created by mmacias on 15/8/16.
 */
@Configuration
open class ShiroCfg {
    @Bean
    open fun lifecycleBeanPostProcessor() = LifecycleBeanPostProcessor()


    val log = LoggerFactory.getLogger(ShiroCfg::class.java)


//    	<listener>
//		<listener-class>org.apache.shiro.web.env.EnvironmentLoaderListener</listener-class>
//	</listener>
//
//	<filter>
//		<filter-name>ShiroFilter</filter-name>
//		<filter-class>org.apache.shiro.web.servlet.ShiroFilter</filter-class>
//		<async-supported>true</async-supported>
//	</filter>
//	<filter-mapping>
//		<filter-name>ShiroFilter</filter-name>
//		<url-pattern>/*</url-pattern>
//		<dispatcher>REQUEST</dispatcher>
//		<dispatcher>FORWARD</dispatcher>
//		<dispatcher>INCLUDE</dispatcher>
//		<dispatcher>ERROR</dispatcher>
//	</filter-mapping>
//

    @Bean
    open fun shiroListenerRegistration(): ServletListenerRegistrationBean<EnvironmentLoaderListener> {
        log.debug("ShiroListenerRegistration()")
        var listener = ServletListenerRegistrationBean<EnvironmentLoaderListener>()
        listener.listener = EnvironmentLoaderListener()
        return listener
    }
    @Bean
    open fun shiroFilterRegistration(): FilterRegistrationBean {
        log.debug("ShiroFilterRegistration()")
        val registration = FilterRegistrationBean()
        registration.setFilter(ShiroFilter())
        registration.isAsyncSupported = true
        registration.urlPatterns = arrayListOf("/*")
        registration.setName("ShiroFilter")
        registration.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR)
        return registration
    }

//    @Bean
//    open fun tralareloFilterRegistration() : FilterRegistrationBean {
//        log.debug("tralareloRegistration")
//        val registration = FilterRegistrationBean()
//        registration.filter = object: Filter() {
//            val log = LoggerFactory.getLogger(this.javaClass)
//            override fun destroy() {
//                log.error("Destroy")
//            }
//
//            override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
//                log.error("Dofilter")
//                chain!!.doFilter(request,response)
//            }
//
//            override fun init(filterConfig: FilterConfig?) {
//                log.error("Init")
//            }
//        }
//        registration.
//
//    }

//    @Bean
//    open fun shiroFilter(): ShiroFilterFactoryBean {
//        log.debug("Calling shiroFilter")
//        val factoryBean = ShiroFilterFactoryBean()
//        factoryBean.setSecurityManager(securityManager())
//        return factoryBean;
//    }
//
//
//    @Bean(name=arrayOf("securityManager"))
//    open fun securityManager(): DefaultWebSecurityManager {
//        log.debug("Calling securityManager")
//        val factory = WebIniSecurityManagerFactory(Ini.fromResourcePath("classpath:shiro.ini"))
//        val securityManager = factory.getInstance() as DefaultWebSecurityManager
//        return securityManager
//
////        http@ //stackoverflow.com/questions/20626520/how-to-configure-shiro-with-shiro-ini-when-using-springs-java-configuration
////        https://github.com/pires/spring-boot-shiro-orientdb/blob/master/src/main/java/com/github/pires/example/ShiroConfiguration.java
//    }



}