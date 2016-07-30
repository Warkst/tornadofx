package tornadofx.osgi.impl

import org.osgi.framework.BundleContext
import org.osgi.framework.ServiceEvent
import org.osgi.framework.ServiceListener
import tornadofx.importStylesheet
import tornadofx.osgi.StylesheetProvider
import tornadofx.removeStylesheet

internal class StylesheetListener(val context: BundleContext) : ServiceListener {
    override fun serviceChanged(event: ServiceEvent) {
        if (event.isStylesheetProviderEvent()) {
            val provider = context.getService(event.serviceReference) as StylesheetProvider

            if (event.type == ServiceEvent.REGISTERED) {
                importStylesheet(provider.stylesheet)
            } else if (event.type == ServiceEvent.UNREGISTERING) {
                removeStylesheet(provider.stylesheet)
            }
        }
    }

    private fun ServiceEvent.isStylesheetProviderEvent() = objectClass == StylesheetProvider::class.qualifiedName

}