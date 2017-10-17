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

package es.betroop.intranet

import es.betroop.intranet.storage.StorageProperties
import es.betroop.intranet.storage.StorageService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean

/**
 * Entry point for the application.
 *
 * @author Mario Macias (http://github.com/mariomac)
 */
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties::class)
open class Application : SpringBootServletInitializer() {
    override protected fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(Application::class.java)
    }
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    @Bean
    open fun init(storageService: StorageService): CommandLineRunner {
        return CLR(storageService)
    }
}

internal class CLR(val storageService: StorageService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        storageService.init()
    }
}


