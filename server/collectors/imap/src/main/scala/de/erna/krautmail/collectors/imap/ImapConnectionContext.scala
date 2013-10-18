package de.erna.krautmail.collectors.imap

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 18.10.2013
 * Time: 09:24
 *
 * Every IMAP adapter must subclass this trait to have a class/objects that store context information that the adapter
 * might need when it's called. This data could be connection handles, lists of supported features from the server, etc.
 *
 * The trait itself is empty as it will be used as opaque type by the collector.
 */
trait ImapConnectionContext
