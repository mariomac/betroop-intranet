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

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.view.tiles3.TilesConfigurer
import org.springframework.web.servlet.view.tiles3.TilesView
import org.springframework.web.servlet.view.tiles3.TilesViewResolver

@Configuration
open class Tiles3Cfg {

    @Bean
    open fun tilesConfigurer(): TilesConfigurer {
        val configurer = TilesConfigurer()
        configurer.setDefinitions("WEB-INF/tiles/tiles.xml")
        configurer.setCheckRefresh(true)
        return configurer
    }

    @Bean
    open fun tilesViewResolver(): TilesViewResolver {
        val resolver = TilesViewResolver()
        resolver.setViewClass(TilesView::class.java)
        return resolver
    }
}