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

package es.betroop.intranet.security.gui

import es.betroop.intranet.beans.ApplicationContextProvider
import es.betroop.intranet.dao.webadmin.WebAdminDao
import org.apache.shiro.authc.*
import org.apache.shiro.realm.AuthenticatingRealm
import org.apache.shiro.subject.SimplePrincipalCollection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Authenticating realm for Console GUI users, which are logged through an HTTP form

 * @author Mario Macias (http://github.com/mariomac)
 */
@Service
class GuiRealm : AuthenticatingRealm() {

    private var log = LoggerFactory.getLogger(GuiRealm::class.java)


    private fun dao() = ApplicationContextProvider.appContext.getBean("webAdminDao",WebAdminDao::class.java)


    /**
     * Returns true if the token is an instance of [org.apache.shiro.authc.UsernamePasswordToken]
     * @param token
     * *
     * @return
     */
    override fun supports(token: AuthenticationToken?): Boolean {
        log.trace("GuiRealm.supports() " + token)
        return token is UsernamePasswordToken
    }

    /**
     * Checks the validity of the received [UsernamePasswordToken]
     * @see AuthenticatingRealm.doGetAuthenticationInfo
     * @param authenticationToken
     * *
     * @return
     * *
     * @throws AuthenticationException
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(authenticationToken: AuthenticationToken): AuthenticationInfo? {
        log.trace("GuiRealm.doGetAuthenticationInfo() " + authenticationToken)
        val token = authenticationToken as UsernamePasswordToken
        val userLogin = token.username
        val userPassword = String(token.password)

        if (dao().checkPassword(userLogin,userPassword)) {
            return GuiRealm.Companion.createAuthenticationInfo(token)
        } else {
            return null
        }
    }

    companion object {
        val REALM_NAME = "consoleRealm"
        private var log = LoggerFactory.getLogger(GuiRealm::class.java)

        private fun createAuthenticationInfo(token: UsernamePasswordToken): AuthenticationInfo {
            GuiRealm.Companion.log.trace("GuiRealm.createAuthenticationInfo() " + token)

            val authInfo = SimpleAuthenticationInfo()
            authInfo.credentials = token.credentials
            val principals = SimplePrincipalCollection()
            principals.add(token.principal, GuiRealm.Companion.REALM_NAME)
            authInfo.principals = principals
            return authInfo
        }
    }

}