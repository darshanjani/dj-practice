package ch02

import java.awt.BorderLayout as BL
import javax.swing.WindowConstants as WC
import groovy.swing.SwingBuilder
import javax.swing.ImageIcon

/**
 * Created by Darshan on 7/10/16.
 */
println "Hello, DJ"

//https://chart.googleapis.com/chart?cht=p3&chs=250x100&chd=t:60,40&chl=Hello|World
String base = "https://chart.googleapis.com/chart?"
def params = [cht:'p3', chs:'250x100', chd:'t:60,40', chl:'Hello|World']
String qs = params.collect { k, v -> "$k=$v" }.join('&')
println "$base$qs".toURL()

SwingBuilder.edtBuilder {
    frame(title: 'Hello, World!', visible: true, pack: true, defaultCloseOperation: WC.EXIT_ON_CLOSE) {
        label(icon: new ImageIcon("$base$qs".toURL()), constraints: BL.CENTER)
    }
}