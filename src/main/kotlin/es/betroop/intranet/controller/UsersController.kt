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

package es.betroop.intranet.controller

import es.betroop.intranet.dao.meeting.UserSearchCriteria
import es.betroop.intranet.dao.user.UsersDao
import es.betroop.intranet.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Created by mmacias on 28/9/16.
 */

@Component
class UsersController {

    @Autowired
    internal open lateinit var dao : UsersDao

    @Value("\${db.page.entries}")
    var dbPageEntries : Int = 20

    fun getAll(sc: UserSearchCriteria):List<User> {
        val allUsers = dao.find(sc)
        return allUsers
    }

    fun get(userId: Long): User? {
        val events = dao.find(searchCriteria = UserSearchCriteria(id=userId))
        return if(events.size == 0) null else events[0];
    }

    fun update(user: User) {
        dao.update(user)
    }

    fun create(user: User): Long {
        return dao.insert(user)
    }

    fun count(sc: UserSearchCriteria) : Long {
        val ev = dao.count(sc)
        val pe = dbPageEntries
        return (( ev / pe ) + (if(ev%pe == 0L) 0 else 1)).toLong()
    }
}